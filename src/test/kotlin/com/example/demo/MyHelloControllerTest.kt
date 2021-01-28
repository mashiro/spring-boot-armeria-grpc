package com.example.demo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(MyHelloController::class)
internal class MyHelloControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun hello() {
        val response = mockMvc.perform(get("/hello").param("name", "test"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString

        assertEquals("Hello test", response)
    }
}