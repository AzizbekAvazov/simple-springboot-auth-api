package com.azizbek.fancybackservice.component;

import com.azizbek.fancybackservice.entity.Images;
import com.azizbek.fancybackservice.entity.Posts;
import com.azizbek.fancybackservice.entity.Users;
import com.azizbek.fancybackservice.model.request.object.SavePostRequest;
import com.azizbek.fancybackservice.service.ImagesService;
import com.azizbek.fancybackservice.service.PostsService;
import com.azizbek.fancybackservice.service.UsersService;
import com.azizbek.fancybackservice.util.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

/**
 * Creator: Azizbek Avazov
 * Date: 20.08.2022
 * Time: 18:27
 */
@Component
public class ObjectRestHelper {
    private final UsersService usersService;
    private final ImagesService imagesService;
    private final PostsService postsService;

    Logger logger = LoggerFactory.getLogger(ObjectRestHelper.class);

    public ObjectRestHelper(UsersService usersService, ImagesService imagesService, PostsService postsService) {
        this.usersService = usersService;
        this.imagesService = imagesService;
        this.postsService = postsService;
    }

    /**
     * Returns current user
     */
    private Optional<Users> getUserObject() {
        JSONObject userAsJson = new JSONObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        String userEmail = userAsJson.getString("username");
        Optional<Users> opt = usersService.findByEmail(userEmail);

        return opt;
    }

    /**
    * Save file to DB
    */
    public JSONObject imageUploadDBResponse(MultipartFile multipartFile, String path) {
        JSONObject response = new JSONObject();
        Optional<Users> opt = getUserObject();

        if (opt.isPresent()) {
            Users user = opt.get();
            String fileName = Utilities.getFileName(multipartFile.getOriginalFilename(), user);

            try {
                /*
                *  Save uploaded image in DB
                */
                Images image = new Images();
                image.setImage(multipartFile.getBytes());
                image.setImage_name(fileName);
                image.setUser_id(user.getUser_id());
                image.setCreated_date(Utilities.getCurrentDateAndTime());
                image.setContent_type(multipartFile.getContentType());
                imagesService.saveImage(image);

                /*
                * Create successful response
                */

                String fileBytesString = Base64.getEncoder().encodeToString(multipartFile.getBytes());
                response = Utilities.generateBaseResponse(0, "File successfully saved in DB!", path);
                response.put("image", fileBytesString);
                return response;
                // UploadImageResponse response = new UploadImageResponse();
                // response.setCode(0L);
                // response.setDate(Utilities.getCurrentDateAndTime());
                // response.setMsg("File successfully saved in DB");
                // response.setPath(path+"/IMAGE_UPLOAD");
                //response.setImage(multipartFile.getBytes());

               // return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
            } catch (IOException e) {
                response = Utilities.generateBaseResponse(1, e.getMessage(), path);
                return response;
               /* BaseResponse response = new BaseResponse();
                response.setCode(1L);
                response.setDate(Utilities.getCurrentDateAndTime());
                response.setMsg(e.getMessage());
                response.setPath(path+"/IMAGE_UPLOAD");

                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);*/
            }
        } else {
            response = Utilities.generateBaseResponse(1, "User not found!", path);
            return response;
            /*          BaseResponse response = new BaseResponse();
            response.setCode(1L);
            response.setDate(Utilities.getCurrentDateAndTime());
            response.setMsg("User not found");
            response.setPath(path+"/IMAGE_UPLOAD");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);*/
        }
    }

    /**
    * Save file to file system
    */
    public JSONObject imageUploadFSResponse(MultipartFile multipartFile, String path) {
        JSONObject response = new JSONObject();
        Optional<Users> opt = getUserObject();

        if (opt.isPresent()) {
            Users user = opt.get();

            String fileName = Utilities.getFileName(multipartFile.getOriginalFilename(), user);
            String folderPath = Utilities.IMAGES_FOLDER_PATH + user.getUser_id();
            String imagePath = folderPath + "\\" + fileName;

            // create new folder (if does not exists) and give user_id as its name
            File folder = new File(folderPath);

            if (!folder.exists()) {
                folder.mkdir();
            }

            // store image uploaded by user inside this folder
            File uploadedImage = new File(imagePath);
            try {
                multipartFile.transferTo(uploadedImage);

                response = Utilities.generateBaseResponse(0, "File successfully saved in DB!", path);
                response.put("image_url", imagePath);

                return response;
            } catch (IOException e) {
                response = Utilities.generateBaseResponse(1, e.getMessage(), path);
                return response;
            }
        } else {
            response = Utilities.generateBaseResponse(1, "User not found!", path);
            return response;
        }
    }

    /**
     * Save new post to DB
     */
    public JSONObject savePostResponse(SavePostRequest request, String microservicePath) {
        JSONObject response = new JSONObject();
        Optional<Users> opt = getUserObject();

        if (opt.isPresent()) {
            Users user = opt.get();

            Posts newPost = new Posts();
            newPost.setSource_code(request.getSource_code());
            newPost.setTitle(request.getTitle());
            newPost.setCreated_date(Utilities.getCurrentDateAndTime());
            newPost.setOwner_id(user.getUser_id());
            newPost.setOwner_fullname(user.getUser_fullname());
            newPost.setTags(request.getTags());

            postsService.savePost(newPost);

            response = Utilities.generateBaseResponse(0, "", microservicePath);
            return response;
        } else {
            response = Utilities.generateBaseResponse(1, "User not found!", microservicePath);
            return response;
        }
    }

    /**
     * Get list of user's posts
     */
    public JSONObject getUserPostsResponse(String microservicePath) {
        JSONObject response = new JSONObject();
        Optional<Users> opt = getUserObject();

        if (opt.isPresent()) {
            Users user = opt.get();

            List<Posts> postsList = postsService.getUserPosts(user.getUser_id());
            response = Utilities.generateBaseResponse(0, "", microservicePath);
            response.put("posts", new JSONArray(postsList));

            return response;
        } else {
            response = Utilities.generateBaseResponse(1, "User not found!", microservicePath);
            return response;
        }
    }


    /**
     * Get list of user's posts by ID
     */
    public JSONObject getUserPostsByIdResponse(String microservicePath, long user_id) {
        JSONObject response = new JSONObject();

        List<Posts> postsList = postsService.getUserPosts(user_id);
        response = Utilities.generateBaseResponse(0, "", microservicePath);
        response.put("posts", new JSONArray(postsList));

        return response;
    }


    /**
     * Get all Posts from DB
     */
    public JSONObject getAllPostsResponse(String microservicePath) {
        JSONObject response = new JSONObject();
        List<Posts> postsList = postsService.getAllPosts();

        response = Utilities.generateBaseResponse(0, "", microservicePath);
        response.put("posts", new JSONArray(postsList));

        return response;
    }

    /**
     * Retrieve user object from DB by username
     */
    public JSONObject getUserByUsernameResponse(String microservicePath, String username) {
        JSONObject response = new JSONObject();
        Users user = usersService.getUserByUsername(username);

        if (user != null) {
            response = Utilities.generateBaseResponse(0, "", microservicePath);

            JSONObject data = new JSONObject();
            data.put("email", user.getEmail());
            data.put("user_id", user.getUser_id());
            data.put("user_fullname", user.getUser_fullname());
            data.put("username", user.getUsername());
            response.put("data", data);
        } else {
            response = Utilities.generateBaseResponse(1, "User not found!", microservicePath);
        }

        return response;
    }

    /**
     * Delete post
     */
    public JSONObject deletePostResponse(String microservicePath, long post_id) {
        JSONObject response = new JSONObject();
        postsService.deletePostById(post_id);
        response = Utilities.generateBaseResponse(0, "", microservicePath);

        return response;
    }

    /**
     * Get post by post_id
     */
    public JSONObject getPostByIdResponse(String microservicePath, long post_id) {
        JSONObject response = new JSONObject();
        Optional<Posts> opt = postsService.getPostById(post_id);

        if (opt.isPresent()) {
            Posts post = opt.get();
            response = Utilities.generateBaseResponse(0, "", microservicePath);
            response.put("post", new JSONObject(post));
        } else {
            response = Utilities.generateBaseResponse(1, "Post not found!", microservicePath);
        }

        return response;
    }
}
