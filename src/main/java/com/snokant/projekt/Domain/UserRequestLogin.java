package com.snokant.projekt.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserRequestLogin {
    String email;
    String password;
}
