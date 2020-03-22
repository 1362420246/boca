package com.qbk.boca.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.qbk.boca.bean.BaseResult;
import com.qbk.boca.bean.BaseResultUtil;
import com.qbk.boca.core.exception.BasicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.TimeUnit;
@Slf4j
@RestController
public class TestController {
    /**
     * SentinelResource 控制注解
     * value资源名，和控制台对应
     * blockHandlerClass sentinel控制异常回退类
     * blockHandler sentinel控制异常回退方法
     * fallbackClass 异常熔断回退类
     * fallback 异常熔断回退方法
     * exceptionsToIgnore 排除的异常
     */
    @GetMapping("/test")
    @SentinelResource(
            value = "test",
            blockHandlerClass = TestBlockHandler.class,
            blockHandler = "testBloc",
            fallbackClass = TestFallback.class,
            fallback = "testFall",
            exceptionsToIgnore = BasicException.class
    )
    public BaseResult<?> test(
            @RequestParam(value = "id",required = false,defaultValue = "1")Integer id){
        if(id == 2){
            //测试排除掉自定义异常，交由spring统一异常处理
            throw new BasicException("统一异常");
        }else if( id == 3){
            //测试发生异常时，错误熔断
            int i = 10/0;
        }else if (id == 4){
            //测试sentinel时长
            try {
                TimeUnit.SECONDS.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return BaseResultUtil.ok();
    }
    /**
     *  sentinel控制会触发BlockException异常，具体有5个字类
     *  注意方法参数和返回值和原方法对应
     *  注意类和方法的static 和 public
     */
    public static class TestBlockHandler{
         public static BaseResult<?> testBloc(Integer id,BlockException blockException){
             if(blockException instanceof FlowException){
                 log.error("发送了流控",blockException);
                 return BaseResultUtil.error("流控") ;
             }else if(blockException instanceof DegradeException){
                 log.error("发送了熔断降级",blockException);
                 return BaseResultUtil.error("熔断降级") ;
             }else if(blockException instanceof SystemBlockException){
                 log.error("发送了系统保护",blockException);
                 return BaseResultUtil.error("系统保护") ;
             }else if(blockException instanceof ParamFlowException){
                 log.error("发送了热点参数限流",blockException);
                 return BaseResultUtil.error("热点参数限流") ;
             }else {
                 //AuthorityException
                 log.error("发送了阻塞请求源访问(权限)控制",blockException);
                 return BaseResultUtil.error("阻塞请求源访问(权限)控制") ;
             }
         }
    }
    /**
     * 异常熔断
     */
    public static class TestFallback{
        public static BaseResult<?> testFall(Integer id,Throwable throwable){
            log.error("发送了异常熔断",throwable);
            return BaseResultUtil.error("异常熔断") ;
        }
    }
}
