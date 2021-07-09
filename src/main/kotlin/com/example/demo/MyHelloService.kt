package com.example.demo

import com.example.grpc.hello.HelloReply
import com.example.grpc.hello.HelloRequest
import com.example.grpc.hello.HelloServiceGrpcKt
import org.springframework.stereotype.Component

@Component
class MyHelloService : HelloServiceGrpcKt.HelloServiceCoroutineImplBase() {
    override suspend fun hello(request: HelloRequest): HelloReply {
        return HelloReply.newBuilder()
            .setMessage("Hello ${request.name}")
            .build()
    }
}