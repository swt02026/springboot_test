package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MockProductService(@Autowired var productDAO: MockProductDAO) {
    fun createProduct(request: Product): Product {
        val product = request.copy()
        productDAO.insert(product)
        return product
    }

    fun getProduct(id: String) : Product? {
        return this.productDAO.find(id)?: throw NotFoundException("Can't find product.")
    }

    fun replaceProduct(id: String, product: Product): Product? {
        val checkProduct =  getProduct(id)
        if (checkProduct != null) {
            return productDAO.replace(checkProduct.id, product)
        }
        return checkProduct
    }

    fun deleteProduct(id: String){
        val checkProduct = getProduct(id)
        if (checkProduct != null) {
            productDAO.delete(checkProduct.id)
        }
    }
    fun getProducts(param: ProductQueryParameter): List<Product> {
        return productDAO.find(param)
    }
}