package com.dealtech.IntA

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class Message (val name: String)

@RestController
@RequestMapping("/api") // All endpoints in this controller will be prefixed with /api
class ApiController {

    @GetMapping("/")
    fun getHello(): Message {
        return Message("backend")
    }
}