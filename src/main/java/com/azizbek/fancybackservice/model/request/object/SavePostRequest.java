package com.azizbek.fancybackservice.model.request.object;

/**
 * Creator: Azizbek Avazov
 * Date: 20.08.2022
 * Time: 17:51
 */
public class SavePostRequest {
    private String source_code;

    private String title;

    private String[] tags;

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource_code() {
        return source_code;
    }

    public void setSource_code(String source_code) {
        this.source_code = source_code;
    }
}
