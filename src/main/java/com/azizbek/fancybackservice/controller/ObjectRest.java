package com.azizbek.fancybackservice.controller;

import com.azizbek.fancybackservice.component.ObjectRestHelper;
import com.azizbek.fancybackservice.entity.Users;
import com.azizbek.fancybackservice.model.request.object.DeletePostRequest;
import com.azizbek.fancybackservice.model.request.object.GetPostByIdRequest;
import com.azizbek.fancybackservice.model.request.object.SavePostRequest;
import com.azizbek.fancybackservice.service.ImagesService;
import com.azizbek.fancybackservice.service.UsersService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * Creator: Azizbek Avazov
 * Date: 07.07.2022
 * Time: 18:28
 */

@RestController
@RequestMapping("/OBJECT")
public class ObjectRest {
    private final String path = "/OBJECT";
    private final UsersService usersService;
    private final ImagesService imagesService;
    private final ObjectRestHelper objectRestHelper;

    Logger logger = LoggerFactory.getLogger(ObjectRest.class);

    public ObjectRest(UsersService usersService, ImagesService imagesService, ObjectRestHelper objectRestHelper) {
        this.usersService = usersService;
        this.imagesService = imagesService;
        this.objectRestHelper = objectRestHelper;
    }

    /*
    * Returns current user
    */
    private Optional<Users> getUserObject() {
        JSONObject userAsJson = new JSONObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        String userEmail = userAsJson.getString("username");
        Optional<Users> opt = usersService.findByEmail(userEmail);

        return opt;
    }

    /*
     * This rest endpoint is for uploading image to DATABASE
     */
    @PostMapping("/IMAGE_UPLOAD_DB")
    public ResponseEntity image_upload_db(@RequestParam("file") MultipartFile multipartFile) {
        /* Retrieve user email (aka username) from Spring Security.
         * Using user email, get user id. User id will be used to create folder for specific user
         * inside Images folder and to as a suffix for the image name that user uploaded
         */
        JSONObject response = objectRestHelper.imageUploadDBResponse(multipartFile, path + "/IMAGE_UPLOAD_DB");
        long code = response.getLong("code");

        if (code != 0) {
            return new ResponseEntity(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity(response.toString(), HttpStatus.OK);
        }

        /*JSONObject userAsJson = new JSONObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        String userEmail = userAsJson.getString("username");
        Optional<Users> opt = usersService.findByEmail(userEmail);

        if (opt.isPresent()) {
            Users user = opt.get();
            String fileName = Utilities.getFileName(multipartFile.getOriginalFilename(), user);

            Images image = new Images();
            try {
                image.setImage(multipartFile.getBytes());
                image.setImage_name(fileName);
                image.setUser_id(user.getUser_id());
                image.setCreated_date(Utilities.getCurrentDateAndTime());
                image.setContent_type(multipartFile.getContentType());
                imagesService.saveImage(image);

                UploadImageResponse response = new UploadImageResponse();
                response.setCode(0L);
                response.setDate(Utilities.getCurrentDateAndTime());
                response.setMsg("File successfully saved in DB");
                response.setPath(path+"/IMAGE_UPLOAD");
                response.setImage(multipartFile.getBytes());

                return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
            } catch (IOException e) {
                BaseResponse response = new BaseResponse();
                response.setCode(1L);
                response.setDate(Utilities.getCurrentDateAndTime());
                response.setMsg(e.getMessage());
                response.setPath(path+"/IMAGE_UPLOAD");

                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            BaseResponse response = new BaseResponse();
            response.setCode(1L);
            response.setDate(Utilities.getCurrentDateAndTime());
            response.setMsg("User not found");
            response.setPath(path+"/IMAGE_UPLOAD");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }*/
    }

    /*
    * This rest endpoint is for uploading image to file system
    */
    @PostMapping("/IMAGE_UPLOAD")
    public ResponseEntity image_upload_fs(@RequestParam("file") MultipartFile multipartFile) {
        /* Retrieve user email (aka username) from Spring Security.
        *  Using user email, get user id. User id will be used to create folder for specific user
        *  inside Images folder and to as a suffix for the image name that user uploaded
        * */
        JSONObject response = objectRestHelper.imageUploadFSResponse(multipartFile, path + "/IMAGE_UPLOAD");
        long code = response.getLong("code");

        if (code != 0) {
            return new ResponseEntity(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity(response.toString(), HttpStatus.OK);
        }
        /*JSONObject userAsJson = new JSONObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        String userEmail = userAsJson.getString("username");
        Optional<Users> opt = usersService.findByEmail(userEmail);

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

                UploadImageResponse response = new UploadImageResponse();
                response.setCode(0L);
                response.setDate(Utilities.getCurrentDateAndTime());
                response.setMsg("File successfully saved in DB");
                response.setPath(path+"/IMAGE_UPLOAD");
                response.setImage_url(imagePath);

                return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
            } catch (IOException e) {
                BaseResponse response = new BaseResponse();
                response.setCode(1L);
                response.setDate(Utilities.getCurrentDateAndTime());
                response.setMsg(e.getMessage());
                response.setPath(path+"/IMAGE_UPLOAD");

                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            BaseResponse response = new BaseResponse();
            response.setCode(1L);
            response.setDate(Utilities.getCurrentDateAndTime());
            response.setMsg("User not found");
            response.setPath(path+"/IMAGE_UPLOAD");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }*/
    }

    /*
    * Saves post to DATABASE
    */
    @PostMapping("/SAVE_POST")
    public ResponseEntity save_post(@RequestBody SavePostRequest request) {
        JSONObject response = objectRestHelper.savePostResponse(request, path + "/SAVE_POST");
        long code = response.getLong("code");

        if (code != 0) {
            return new ResponseEntity(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity(response.toString(), HttpStatus.OK);
        }
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
     * Delete post
     */
    @PostMapping("DELETE_POST")
    public ResponseEntity delete_post(@RequestBody DeletePostRequest request) {
        JSONObject response = objectRestHelper.deletePostResponse(path + "/DELETE_POST", request.getPost_id());
        long code = response.getLong("code");

        if (code != 0) {
            return new ResponseEntity(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity(response.toString(), HttpStatus.OK);
        }
    }

}
