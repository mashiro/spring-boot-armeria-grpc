package com.example.demo

import com.example.grpc.hello.Hello
import com.example.grpc.hello.HelloServiceGrpcKt
import org.springframework.stereotype.Component

@Component
class MyHelloService : HelloServiceGrpcKt.HelloServiceCoroutineImplBase() {
    override suspend fun hello(request: Hello.HelloRequest): Hello.HelloReply {
        return Hello.HelloReply.newBuilder()
            .setMessage("Hello ${request.name}")
            .build()
    }
}