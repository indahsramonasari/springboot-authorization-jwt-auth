package com.id.userproductservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest implements Serializable {

    private static final long serialVersionUID = -1559228278715169780L;

    private String productId;
    private String productName;
    private String productPrice;
}
