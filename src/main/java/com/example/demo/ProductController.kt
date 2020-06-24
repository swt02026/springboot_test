package com.example.demo
import  com.example.demo.Product
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class ProductController {
    var productDB = mutableListOf<Product>(Product("aaaa","bbb",123))
    @GetMapping("/products/{id}")
    fun getProduct(@PathVariable("id") id:String): ResponseEntity<Product> {
        val product = productDB.find { p -> p.id == id } ?: return ResponseEntity.notFound().build<Product>()
        return ResponseEntity.ok().body(product)
    }

    @PostMapping("/products")
    fun postProduct(@RequestBody request: Product): ResponseEntity<Product> {
        if(productDB.any { p -> p.id == request.id }){
            return ResponseEntity.status(HttpStatus.CONFLICT).build<Product>()
        }
        productDB.add(request.copy())
        var location: URI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(request.id)
                .toUri()
        return ResponseEntity.created(location).body(request)
    }
}