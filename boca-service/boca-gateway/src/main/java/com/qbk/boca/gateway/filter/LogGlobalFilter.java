package com.qbk.boca.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.qbk.boca.gateway.constant.GatewayConstant;
import com.qbk.boca.gateway.entity.LogDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 日志全局过滤器
 */
@Slf4j
public class LogGlobalFilter  implements GlobalFilter,Ordered {
    /*
    1.实现GlobalFilter＆Ordered
    2.重要提示：订单必须小于-1，否则标准NettyWriteResponseFilter将在您的过滤器有机会被调用之前发送响应
    3.在重写的过滤器方法中，通过exchange.getResponse（）创建一个ServerHttpResponseDecorator，4
    重写此装饰器的writeWith方法，然后在其中进行主体修改（我必须将主体从Publisher <？扩展为DataBuffer>转换为Flux以​​使其完成。
    易于修改），返回super.writeWith（yourModifiedBodyFlux）。更改交换并将装饰器设置为新的响应。
     */
    /**
     * 必须要小于 -1
     */
    @Override
    public int getOrder() {
        return -2;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //开始时间
        exchange.getAttributes().put(GatewayConstant.LOG_ATTRIBUTES, LogDTO.builder().startTime(System.currentTimeMillis()).build());
        //请求方法 只纪录get和post
        HttpMethod method = exchange.getRequest().getMethod();
        if( !HttpMethod.GET.matches(method.name()) && !HttpMethod.POST.equals(method)){
            return chain.filter(exchange);
        }
        //请求头 当post时候只纪录json
        if(HttpMethod.POST.equals(method)){
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            if(!headers.containsKey(HttpHeaders.CONTENT_TYPE)){
                return chain.filter(exchange);
            }
            List<String> contextTypes = headers.get(HttpHeaders.CONTENT_TYPE);
            if(!(contextTypes.contains( MediaType.APPLICATION_JSON_VALUE)
                    || contextTypes.contains( MediaType.APPLICATION_JSON_UTF8_VALUE))){
                return chain.filter(exchange);
            }
        }
        /*
           TODO 忽略下载
           临时方案从路径中忽略
           正常应该从相应头中忽略
           MediaType.APPLICATION_OCTET_STREAM  application/octet-stream
         */
        boolean isDownload = Stream.of(
                exchange.getRequest().getPath().value().toLowerCase().split("/")
        ).anyMatch("download"::equals);
        if(isDownload){
            return chain.filter(exchange);
        }
        //参数
        if( HttpMethod.GET.matches(method.name())){
            // 记录请求的参数信息 针对GET 请求
            MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
                builder.append(entry.getKey()).append("=").append(StringUtils.join(entry.getValue(), ",")).append(",");
            }
            LogDTO sysLog = exchange.getAttribute(GatewayConstant.LOG_ATTRIBUTES);
            sysLog.setParams(builder.toString());
            return returnMono(chain, exchange);
        }else {
            //重新构造request，参考ModifyRequestBodyGatewayFilterFactory
            ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
            //body
            Mono<String> modifiedBody = serverRequest.bodyToMono(String.class).flatMap(body -> {
                    LogDTO sysLog = exchange.getAttribute(GatewayConstant.LOG_ATTRIBUTES);
                    sysLog.setParams(body);
                    return Mono.just(body);
            });
            BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
            HttpHeaders headersNew = new HttpHeaders();
            headersNew.putAll(exchange.getRequest().getHeaders());
            //猜测这个就是之前报400错误的元凶，之前修改了body但是没有重新写content length
            // the new content type will be computed by bodyInserter
            // and then set in the request decorator
            headersNew.remove(HttpHeaders.CONTENT_LENGTH);
            // Greenwich版本通过cachedBodyOutputMessage进行构造body请求
            //MyCachedBodyOutputMessage 这个类完全就是CachedBodyOutputMessage，只不过CachedBodyOutputMessage不是公共的
            MyCachedBodyOutputMessage outputMessage = new MyCachedBodyOutputMessage(exchange, headersNew);
            final Mono<Void> then = bodyInserter.insert(
                    outputMessage,
                    new BodyInserterContext()
            ).then(Mono.defer(() -> {
                ServerHttpRequest decorator = this.decorate(exchange, headersNew, outputMessage);
                return returnMono(chain, exchange.mutate().request(decorator).build());
            }));
            return then;
        }
    }

    private Mono<Void> returnMono(GatewayFilterChain chain, ServerWebExchange exchange){
        return chain.filter(exchange.mutate().response(decorate(exchange)).build()).then(Mono.fromRunnable(()->{
            //日志
            ServerHttpRequest request = exchange.getRequest();
            LogDTO sysLog = exchange.getAttribute(GatewayConstant.LOG_ATTRIBUTES);
            sysLog.setUri(request.getURI());
            sysLog.setPath(request.getPath().value());
            sysLog.setMethod(request.getMethodValue());
            HttpHeaders headers = request.getHeaders();
            sysLog.setHeaders(headers);
            sysLog.setAddress(request.getRemoteAddress());
            Long startTime = sysLog.getStartTime();
            long executeTime = (System.currentTimeMillis() - startTime);
            sysLog.setExecuteTime(executeTime);
            sysLog.setCode(exchange.getResponse().getStatusCode().value());
            System.out.println("日志打印:" + JSONObject.toJSONString(sysLog));
        }));
    }

    /**
     * 参考ModifyRequestBodyGatewayFilterFactory
     */
    ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers,
                                        MyCachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                if (contentLength > 0) {
                    httpHeaders.setContentLength(contentLength);
                }
                else {
                    // TODO: this causes a 'HTTP/1.1 411 Length Required' // on
                    // httpbin.org
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }

    /**
     * 参考 ModifyResponseBodyGatewayFilterFactory
     */
    @SuppressWarnings("unchecked")
    ServerHttpResponse decorate(ServerWebExchange exchange) {
        return new ServerHttpResponseDecorator(exchange.getResponse()) {

            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

                Class inClass = String.class;
                Class outClass = String.class;

                String originalResponseContentType = exchange
                        .getAttribute(ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
                HttpHeaders httpHeaders = new HttpHeaders();
                // explicitly add it in this way instead of
                // 'httpHeaders.setContentType(originalResponseContentType)'
                // this will prevent exception in case of using non-standard media
                // types like "Content-Type: image"
                httpHeaders.add(HttpHeaders.CONTENT_TYPE,
                        originalResponseContentType);

                ClientResponse clientResponse = ClientResponse
                        .create(exchange.getResponse().getStatusCode())
                        .headers(headers -> headers.putAll(httpHeaders))
                        .body(Flux.from(body)).build();

                // TODO: flux or mono
                Mono modifiedBody = clientResponse.bodyToMono(inClass)
                        .flatMap(originalBody -> {
                            //此次可以对返回的body进行操作
                            LogDTO sysLog = exchange.getAttribute(GatewayConstant.LOG_ATTRIBUTES);
                            sysLog.setResult(originalBody);
                            return Mono.just(originalBody);
                        });

                BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody,
                        outClass);
                MyCachedBodyOutputMessage outputMessage = new MyCachedBodyOutputMessage(
                        exchange, exchange.getResponse().getHeaders());
                return bodyInserter.insert(outputMessage, new BodyInserterContext())
                        .then(Mono.defer(() -> {
                            Flux<DataBuffer> messageBody = outputMessage.getBody();
                            HttpHeaders headers = getDelegate().getHeaders();
                            if (!headers.containsKey(HttpHeaders.TRANSFER_ENCODING)) {
                                messageBody = messageBody.doOnNext(data -> headers
                                        .setContentLength(data.readableByteCount()));
                            }
                            // TODO: fail if isStreamingMediaType?
                            return getDelegate().writeWith(messageBody);
                        }));
            }

            @Override
            public Mono<Void> writeAndFlushWith(
                    Publisher<? extends Publisher<? extends DataBuffer>> body) {
                return writeWith(Flux.from(body).flatMapSequential(p -> p));
            }
        };
    }
}








