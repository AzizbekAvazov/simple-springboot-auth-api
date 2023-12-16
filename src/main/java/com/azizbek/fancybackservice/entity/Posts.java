package com.azizbek.fancybackservice.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Creator: Azizbek Avazov
 * Date: 20.08.2022
 * Time: 17:35
 */
@Entity
@Table(name = "posts")
public class Posts {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;

    @Column(name = "title")
    private String title;

    //@Column(name = "source_code")
    @Column(name = "source_code", columnDefinition = "TEXT")
    private String source_code;

/*    @Column(name = "owner_id")
    private Long owner_id;*/

    @Column
    private Long owner_id;

    @Column(name = "created_date")
    private String created_date;

    @Column(name = "owner_fullname")
    private String owner_fullname;

    @Column(name = "tags")
    @Type(type = "string-array")
    private String[] tags;

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getOwner_fullname() {
        return owner_fullname;
    }

    public void setOwner_fullname(String owner_fullname) {
        this.owner_fullname = owner_fullname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public String getSource_code() {
        return source_code;
    }

    public void setSource_code(String source_code) {
        this.source_code = source_code;
    }
/*
    public Long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Long owner_id) {
        this.owner_id = owner_id;
    }*/


    public Long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Long owner_id) {
        this.owner_id = owner_id;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
}
