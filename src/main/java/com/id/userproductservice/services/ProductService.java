package com.id.userproductservice.services;

import com.id.userproductservice.domain.ProductListResponse;
import com.id.userproductservice.domain.ProductRequest;
import com.id.userproductservice.domain.ProductResponse;
import com.id.userproductservice.domain.ResponseStatus;
import com.id.userproductservice.exception.GenericException;
import com.id.userproductservice.models.Product;
import com.id.userproductservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    private static final String STATUS_FAILED = "Failed";
    private static final String STATUS_SUCCESS = "Success";

    private static AtomicLong numberGenerator = new AtomicLong(04000000L);

    //add and update
    public ResponseStatus saveProducts(ProductRequest productRequest){
        ResponseStatus response = new ResponseStatus();
        try {
            Product productExist = productRepository.findOneByProductId(productRequest.getProductId());
            log.info("productExist {}", productExist);

            if (StringUtils.isEmpty(productExist)) {
                // save new data
                String productId = generateUniqueProductId();
                Product product = Product.builder()
                        .productId(productId)
                        .productName(productRequest.getProductName())
                        .productPrice(productRequest.getProductPrice())
                        .build();
                productRepository.save(product);
                response.setMessageDesc("Successfully save product's data");
            } else {
                //update
                productExist.setProductName(productRequest.getProductName());
                productExist.setProductPrice(productRequest.getProductPrice());
                productRepository.save(productExist);
                response.setMessageDesc("Successfully update product's data");
            }

            response.setStatusMessage(STATUS_SUCCESS);
        } catch (GenericException ex) {
            log.error("Error when save product's data {}", ex);
            throw new GenericException("03", STATUS_FAILED, "Error when input or update product's data!");
        }

        return response;
    }

    //get
    public ProductListResponse getProducts() {
        ProductListResponse productListResponse = new ProductListResponse();
        List<ProductResponse> productResponses = new ArrayList<>();
        try {
            List<Product> products = productRepository.findAll();
            for (Product product : products) {
                ProductResponse productResponse = ProductResponse.builder()
                        .productId(product.getProductId())
                        .productName(product.getProductName())
                        .productPrice(product.getProductPrice())
                        .build();
                productResponses.add(productResponse);
            }
            productListResponse.setProductResponses(productResponses);
        } catch (GenericException ex) {
            log.error("Error when get product's data {}", ex);
            throw new GenericException("04", STATUS_FAILED, "Error when get product's data!");
        }
        return productListResponse;
    }

    //delete
    public ResponseStatus deleteProducts(ProductRequest productRequest){
        ResponseStatus response = new ResponseStatus();
        try {
            Product product = productRepository.findOneByProductId(productRequest.getProductId());
            if (product != null) {
                productRepository.deleteById(productRequest.getProductId());
                response.setMessageDesc("Successfully delete data");
                response.setStatusMessage(STATUS_SUCCESS);
            } else {
                throw new GenericException("05", STATUS_FAILED, "Product's data not found!");
            }
        } catch (GenericException ex) {
            log.error("Error when delete product's data {}", ex);
            throw new GenericException("08", STATUS_FAILED, "Error when delete product's data!");
        }

        return response;
    }

    private static String generateUniqueProductId(){
        Random random = new Random();
        int number = random.nextInt(999999);
        return "PRD".concat(String.format("%07d", number));
    }
}
