package com.id.userproductservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @Column(name="id", length = 15, nullable = false)
    private String productId;

    @Column(name="name", length = 30)
    private String productName;

    @Column(name="price", length = 5)
    private String productPrice;

}
