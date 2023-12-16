package com.azizbek.fancybackservice.model.response;

/**
 * Creator: Azizbek Avazov
 * Date: 01.07.2022
 * Time: 9:58
 */
public class BaseResponse {
    private Long code;
    private String msg;
    private String path;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
