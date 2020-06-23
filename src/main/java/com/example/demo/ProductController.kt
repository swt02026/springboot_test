package com.example.demo
import  com.example.demo.Product
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class ProductController {
    @GetMapping("/products/{id}")
    fun getProduct(@PathVariable("id") id:String): Product {
        return Product(id, "aaaa", 200)
    }
}