package com.snokant.projekt.Model.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UserDomain {
    public UserDomain(String email, String password) {
        this.email = email;
        this.password = password;
    }

    String email;
    String password;
}
