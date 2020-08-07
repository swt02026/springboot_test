package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MockProductDAOTest {

    MockProductDAO mockProductDAO = new MockProductDAO();
    @org.junit.jupiter.api.Test
    void insert() {
        Product tmp = new Product("aaa", "bbb", 124);
        Product insert = mockProductDAO.insert(tmp);
        assertSame(tmp, insert);

    }

    @org.junit.jupiter.api.Test
    void replace() {
        Product tmp = new Product("aaa", "bbb", 124);
        Product insert = mockProductDAO.insert(tmp);
        assertSame(tmp, insert);
        Product new_tmp = new Product("aaa","ccc",444);
        mockProductDAO.replace("aaa", new_tmp);
        var find_product = mockProductDAO.find("aaa");
        assertNotNull(find_product);
        assertEquals(find_product, new_tmp);
    }
}