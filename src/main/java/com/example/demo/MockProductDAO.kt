package com.example.demo

import org.springframework.stereotype.Repository

@Repository
class MockProductDAO(var productList: MutableList<Product> = mutableListOf<Product>()) {
    fun insert(product: Product): Product {
        productList.add(product)
        return product
    }
    fun replace(id: String, product: Product): Product? {

        val index = productList.indexOfFirst { it.id == id }
        if(index > productList.size && index < 0){
            return  null
        }
        productList[index] = product
        return product
    }
    fun find(id: String): Product? {
        return productList.find { it.id == id }
    }
    fun delete(id: String) {
        productList.removeIf { it.id == id }
    }

    fun find(param: ProductQueryParameter): List<Product>{
        val (keyword, orderBy, sortRule) = param
        val productsFiltered = productList.filter { keyword?.toUpperCase().toString() in it.name.toUpperCase() }
        val productSorted = when(orderBy){
            "name" ->productsFiltered.sortedBy { it.name }
            "price" -> productsFiltered.sortedBy { it.price }
            else -> productsFiltered
        }
        return when(sortRule) {
            "desc" -> productSorted.reversed()
            else -> productSorted
        }
    }
}