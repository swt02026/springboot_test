package com.example.demo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping(value = ["/products"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ProductCsqontroller(@Autowired var mockProductService: MockProductService) {
    var productDB = mutableListOf<Product>(Product("aaaa","bbb",123))

    @GetMapping("{id}")
    fun getProduct(@PathVariable("id") id:String): ResponseEntity<Product> {
        val product =  mockProductService.getProduct(id)
        return ResponseEntity.ok().body(product)
    }

    @PostMapping
    fun createProduct(@RequestBody request: Product): ResponseEntity<Product> {

        val product = mockProductService.createProduct(request)

        val location: URI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(request.id)
                .toUri()
        return ResponseEntity.created(location).body(product)
    }

    @PutMapping("{id}")
    fun replaceProduct(@PathVariable("id") id: String, @RequestBody request: Product): ResponseEntity<Product?> {

        // kotlin find/first can't get reference of data class
        val product: Product? = mockProductService.replaceProduct(id, request)
        return ResponseEntity.ok(product)
    }

    @DeleteMapping("{id}")
    fun deleteProduct(@PathVariable("id") id: String): ResponseEntity<Product> {
        mockProductService.deleteProduct(id)
        return ResponseEntity.noContent().build<Product>()
    }

    @GetMapping
    fun getProducts(@ModelAttribute param: ProductQueryParameter): ResponseEntity<List<Product>> {
        val productOrderSeq = mockProductService.getProducts(param)
        return ResponseEntity.ok(productOrderSeq)
    }
}