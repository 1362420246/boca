package com.qbk.boca.jsonrpc.api;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

@JsonRpcService("/rpc/user")
public interface UserService {
    User createUser(
            @JsonRpcParam(value = "userName") String userName,
            @JsonRpcParam(value = "password") String password);
}
/*
   jsonrpc协议请求示例：
   curl --location --request POST 'http://localhost:10111/rpc/user' \
        --header 'Content-Type: application/json' \
        --data-raw '{
            "id":"1",
            "jsonrpc":"2.0",
            "method":"createUser",
            "params":{
                "userName":"qbk",
                "password":"123"
                }
        }'
 */