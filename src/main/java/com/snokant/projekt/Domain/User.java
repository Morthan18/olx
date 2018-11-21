package com.snokant.projekt.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    public User(@NotNull(message = "{empty}") @Email(message = "{email.validation}") String email, @NotNull(message = "{empty}") @Size(min = 6, message = "{password}") String password) {
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @NotNull(message = "{empty}")
    @Email(message = "{email.validation}")
    private String email;

    @NotNull(message = "{empty}")
    @Size(min = 6,message = "{password}")
    private String password;

    @NotNull(message = "{empty}")
    @Size(min = 3,max = 30,message = "{first.name}")
    private String first_name;

    @NotNull(message = "{empty}")
    @Size(min = 3,max = 30,message = "{last.name}")
    private String last_name;

    @NotNull(message = "{empty}")
    @Min(value = 9,message = "{phone.number}")
    private int phone_number;

    @NotNull(message = "{empty}")
    private boolean newsletter;

    @NotNull(message = "{empty}")
    private int role_id;

}
