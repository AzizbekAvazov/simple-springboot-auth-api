package com.azizbek.fancybackservice.model.response;

/**
 * Creator: Azizbek Avazov
 * Date: 01.07.2022
 * Time: 9:46
 */
public class UserSignInResponse extends BaseResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
