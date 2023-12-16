package com.azizbek.fancybackservice.model.response;

/**
 * Creator: Azizbek Avazov
 * Date: 13.08.2022
 * Time: 10:23
 */

public class UploadImageResponse extends BaseResponse {
    private String image_url;
    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
