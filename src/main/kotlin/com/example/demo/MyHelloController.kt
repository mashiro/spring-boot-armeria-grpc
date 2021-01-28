package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MyHelloController {
    @GetMapping("/hello")
    fun hello(@RequestParam("name") name: String) = "Hello $name"
}