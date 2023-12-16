package com.azizbek.fancybackservice.controller;

import com.azizbek.fancybackservice.component.ObjectRestHelper;
import com.azizbek.fancybackservice.model.request.object.GetPostByIdRequest;
import com.azizbek.fancybackservice.model.request.object.GetUserByUsernameRequest;
import com.azizbek.fancybackservice.model.request.object.GetUserPostsByIdRequest;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/PUBLIC")
public class PublicPageRest {
    private final ObjectRestHelper objectRestHelper;
    private final String path = "/PUBLIC";

    public PublicPageRest(ObjectRestHelper objectRestHelper) {
        this.objectRestHelper = objectRestHelper;
    }

    /**
     * Get all Posts from DB
     */
    @GetMapping("GET_ALL_POSTS")
    public ResponseEntity get_all_posts() {
        JSONObject response = objectRestHelper.getAllPostsResponse(path + "/GET_ALL_POSTS");
        long code = response.getLong("code");

        if (code != 0) {
            return new ResponseEntity(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity(response.toString(), HttpStatus.OK);
        }
    }

    /**
     * Get list of user's posts by ID
     */
    @PostMapping("/GET_USER_POSTS_BY_ID")
    public ResponseEntity get_user_posts_by_id(@RequestBody GetUserPostsByIdRequest request) {
        JSONObject response = objectRestHelper.getUserPostsByIdResponse(path + "/GET_USER_POSTS", request.getOwner_id());
        long code = response.getLong("code");

        if (code != 0) {
            return new ResponseEntity(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity(response.toString(), HttpStatus.OK);
        }
    }

    /**
     * Retrieve user object from DB by username
     */
    @PostMapping("GET_USER_BY_USERNAME")
    public ResponseEntity get_user_by_username(@RequestBody GetUserByUsernameRequest request) {
        JSONObject response = objectRestHelper.getUserByUsernameResponse(path + "/GET_USER_BY_USERNAME", request.getUsername());
        long code = response.getLong("code");

        if (code != 0) {
            return new ResponseEntity(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity(response.toString(), HttpStatus.OK);
        }
    }

    /**
     * Get post by post_id
     */
    @PostMapping("GET_POST_BY_ID")
    public ResponseEntity get_post_by_id(@RequestBody GetPostByIdRequest request) {
        JSONObject response = objectRestHelper.getPostByIdResponse(path + "/GET_POST_BY_ID", request.getPost_id());
        long code = response.getLong("code");

        if (code != 0) {
            return new ResponseEntity(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity(response.toString(), HttpStatus.OK);
        }
    }
}
