package com.qbk.boca.dubbo.springboot.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * dubbo多协议
 * 支持rest协议
 */
@Path("/")
public interface HelloService {

    @GET
    @Path("/say")
    String sayHello(String name);

    QRespon sayKryo(String name);
}
