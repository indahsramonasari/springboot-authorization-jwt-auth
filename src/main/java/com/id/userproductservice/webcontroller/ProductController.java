package com.id.userproductservice.webcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.id.userproductservice.configuration.JwtConfiguration;
import com.id.userproductservice.domain.ProductListResponse;
import com.id.userproductservice.domain.ProductRequest;
import com.id.userproductservice.domain.ResponseStatus;
import com.id.userproductservice.domain.UserResponse;
import com.id.userproductservice.exception.GenericException;
import com.id.userproductservice.repository.UserRepository;
import com.id.userproductservice.services.ProductService;
import com.id.userproductservice.services.UserService;
import com.id.userproductservice.utils.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class ProductController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ProductService productService;

    @Autowired
    JwtConfiguration jwtUtils;

    @Autowired
    UserService userService;

    @PostMapping("/getUserProfile")
    public ResponseEntity<UserResponse> getUserProfile(@Valid HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        String tokenValue = HttpRequestUtil.getToken(request);
        UserResponse userResponse = new UserResponse();
        try {
            userResponse = userService.getUserProfile(tokenValue);
        } catch (GenericException ex){
            log.error("Error when get user profile {}", ex);
            throw ex;
        }
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseStatus> logoutUser(@Valid HttpServletRequest request, HttpServletResponse response) {
        String tokenValue = HttpRequestUtil.getToken(request);
        ResponseStatus responseStatus = new ResponseStatus();
        try {
            responseStatus = userService.removeAuth(tokenValue);
        } catch (GenericException ex){
            log.error("Error when sign out your account {}", ex);
            throw ex;
        }
        return ResponseEntity.ok(responseStatus);
    }

    @PostMapping("/saveProduct")
    public ResponseEntity saveProduct(@Valid @RequestBody ProductRequest productRequest, HttpServletRequest request, HttpServletResponse response) {
        log.info("productRequest : "+productRequest);
        ResponseStatus responseStatus = new ResponseStatus();
        try {
            responseStatus = productService.saveProducts(productRequest);
        } catch (GenericException ex){
            log.error("Error when save product");
            throw ex;
        }
        return ResponseEntity.ok(responseStatus);
    }

    @GetMapping("/getProduct")
    public ProductListResponse getProductById(@Valid HttpServletRequest request, HttpServletResponse response) {
        ProductListResponse productListResponse = new ProductListResponse();
        try {
            productListResponse = productService.getProducts();
        } catch (GenericException ex){
            log.error("Error when get product by id");
            throw ex;
        }
        return productListResponse;
    }

    @PostMapping("/deleteProductById")
    public ResponseEntity deleteProduct(@Valid @RequestBody ProductRequest productRequest, HttpServletRequest request, HttpServletResponse response) {
        log.info("productRequest : "+productRequest);
        ResponseStatus responseStatus = new ResponseStatus();
        try {
            responseStatus = productService.deleteProducts(productRequest);
        } catch (GenericException ex){
            log.error("Error when delete product");
            throw ex;
        }
        return ResponseEntity.ok(responseStatus);
    }

}
