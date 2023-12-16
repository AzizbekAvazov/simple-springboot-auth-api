package com.azizbek.fancybackservice.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Creator: Azizbek Avazov
 * Date: 13.08.2022
 * Time: 10:09
 */

@Entity
@Table(name = "images")
public class Images {
    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long image_id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "image_name")
    private String image_name;

    @Column(name = "image")
    @Type(type="org.hibernate.type.BinaryType")
    @Lob
    private byte[] image;

    @Column(name = "created_date")
    private String created_date;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "content_type")
    private String content_type;

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Long getImage_id() {
        return image_id;
    }

    public void setImage_id(Long image_id) {
        this.image_id = image_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
}
