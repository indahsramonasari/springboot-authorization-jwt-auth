package com.id.userproductservice.services;

import com.id.userproductservice.domain.ProductListResponse;
import com.id.userproductservice.domain.ProductRequest;
import com.id.userproductservice.domain.ResponseStatus;
import com.id.userproductservice.models.Product;
import com.id.userproductservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles()
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Slf4j
public class ProductServiceTest {

    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @Test
    public void getProductstest(){
        Mockito.when(productRepository.findAll()).thenReturn(List.of(Product.builder()
                .productId("PRDCT7816562173")
                .productName("Kursi")
                .productPrice("1000000")
                .build()));
        ProductListResponse response = productService.getProducts();
        assertNotNull(response);
    }

    @Test
    public void saveProductsTest(){
        ProductRequest productRequest = ProductRequest.builder()
                .productId("PRDCT7816562199")
                .productName("Bantal")
                .productPrice("150000").build();
        ResponseStatus response = productService.saveProducts(productRequest);
        assertNotNull(response);
    }

    @Test
    public void updateProductsTest(){
        Mockito.when(productRepository.findOneByProductId("PRDCT7816562199")).thenReturn(Product.builder()
                .productId("PRDCT7816562199")
                .productName("Kursi")
                .productPrice("1000000")
                .build());
        ProductRequest productRequest = ProductRequest.builder()
                .productId("PRDCT7816562199")
                .productName("Kusri santai")
                .productPrice("150000").build();
        ResponseStatus response = productService.saveProducts(productRequest);
        assertNotNull(response);
    }

    @Test
    public void deleteProductsTest(){
        Mockito.when(productRepository.findOneByProductId("PRDCT7816562199")).thenReturn(Product.builder()
                .productId("PRDCT7816562199")
                .productName("Kursi")
                .productPrice("1000000")
                .build());
        ResponseStatus response = productService.deleteProducts(ProductRequest.builder().productId("PRDCT7816562199").build());
        assertNotNull(response);
    }

}
