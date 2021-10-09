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
public class UserResponse implements Serializable {

    private static final long serialVersionUID = -1359157592734714899L;

    private String statusMessage;
    private String token;
    private int id;
    private String username;
    private String gender;
    private String email;

}
