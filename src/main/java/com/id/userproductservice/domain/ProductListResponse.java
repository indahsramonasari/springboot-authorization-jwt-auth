package com.id.userproductservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponse implements Serializable {

    private static final long serialVersionUID = 5696801842114009940L;

    private List<ProductResponse> productResponses;

}
