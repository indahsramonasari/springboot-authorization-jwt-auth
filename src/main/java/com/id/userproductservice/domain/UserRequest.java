package com.id.userproductservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest implements Serializable {

    private static final long serialVersionUID = -4952514121412304215L;

    private String username;

    @Email
    private String email;

    private String gender;

    private String password;

}
