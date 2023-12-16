package com.azizbek.fancybackservice.model.request.object;

public class GetUserPostsByIdRequest {
    private Long owner_id;

    public Long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Long owner_id) {
        this.owner_id = owner_id;
    }
}
