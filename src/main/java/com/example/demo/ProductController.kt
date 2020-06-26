package com.example.demo
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping(value = ["/products"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ProductController {
    var productDB = mutableListOf<Product>(Product("aaaa","bbb",123))
    @GetMapping("{id}")
    fun getProduct(@PathVariable("id") id:String): ResponseEntity<Product> {
        val product = productDB.find { it.id == id } ?: return ResponseEntity.notFound().build<Product>()
        return ResponseEntity.ok().body(product)
    }

    @PostMapping
    fun postProduct(@RequestBody request: Product): ResponseEntity<Product> {
        if(productDB.any { it.id == request.id }){
            return ResponseEntity.status(HttpStatus.CONFLICT).build<Product>()
        }
        productDB.add(request.copy())
        var location: URI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(request.id)
                .toUri()
        return ResponseEntity.created(location).body(request)
    }

    @PutMapping("{id}")
    fun putProduct(@PathVariable("id") id: String, @RequestBody request: Product): ResponseEntity<Product> {

        // kotlin find/first can't get reference of data class
        var product_index = productDB.indexOfFirst { it.id == id }
        if(product_index < 0) {
            return ResponseEntity.notFound().build<Product>()
        }
        productDB[product_index] = request
        return ResponseEntity.ok(request)
    }

    @DeleteMapping("{id}")
    fun deleteProduct(@PathVariable("id") id: String): ResponseEntity<Product> {
        val removed = productDB.removeIf { it.id == id}
        if(removed){
            return ResponseEntity.noContent().build<Product>()
        }
        return ResponseEntity.notFound().build<Product>()
    }

    @GetMapping
    fun getProducts(@ModelAttribute param: ProductQueryParameter): ResponseEntity<List<Product>> {
        val (keyword, orderBy, sortRule) = param
        val productsFiltered = productDB.filter { keyword == null || keyword?.toUpperCase().toString() in it.name.toUpperCase() }
        val productSorted = when(orderBy){
            "name" ->productsFiltered.sortedBy { it.name }
            "price" -> productsFiltered.sortedBy { it.price }
            else -> productsFiltered
        }
        val productOrderSeq = when(sortRule) {
            "desc" -> productSorted.reversed()
            else -> productSorted
        }
        return ResponseEntity.ok(productOrderSeq)
    }
}