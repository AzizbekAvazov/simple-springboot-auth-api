package com.azizbek.fancybackservice.model.request.authentication;

/**
 * Creator: Azizbek Avazov
 * Date: 01.07.2022
 * Time: 9:44
 */
public class UserSignInRequest {
    private String email;
    private String password;

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
}
