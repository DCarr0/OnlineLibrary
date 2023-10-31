package ru.mtuci.demo.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class UserData implements Serializable {
    @NotEmpty(message = "First name can not be empty")
    private String name;

    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Please provide a valid email id")
    private String email;

    @NotEmpty(message = "Password can not be empty")
    private String password;

//    @NotEmpty(message = "Request to redactor can not be empty")
//    private Boolean requestToRedactor;
//
//    @NotEmpty
//    private LocalDateTime date;
//
//    @NotEmpty
//    private Boolean ban;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Boolean getRequestToRedactor() {
//        return requestToRedactor;
//    }
//
//    public void setRequestToRedactor(Boolean requestToRedactor) {
//        this.requestToRedactor = requestToRedactor;
//    }
//
//    public LocalDateTime getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDateTime date) {
//        this.date = date;
//    }
//
//    public Boolean getBan() {
//        return ban;
//    }
//
//    public void setBan(Boolean ban) {
//        this.ban = ban;
//    }
}
