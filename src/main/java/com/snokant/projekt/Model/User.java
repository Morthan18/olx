package com.snokant.projekt.Model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, int id) {
        this.email = email;
        this.id = id;
        this.password = password;
    }

    public User(String email, String firstName, String lastName, String place, String zipCode, int phoneNumber) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.place = place;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
    }

    public User(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String place;
    private String zipCode;
    private int phoneNumber;
    private boolean newsletter;
    private String allowedUserListToShowThemPhoneNumber;
    private String awaitingUserListToShowThemPhoneNumber;


}
