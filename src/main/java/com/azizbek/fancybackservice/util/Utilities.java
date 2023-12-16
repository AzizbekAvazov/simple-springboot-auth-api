package com.azizbek.fancybackservice.util;

import com.azizbek.fancybackservice.entity.Users;
import com.azizbek.fancybackservice.model.response.BaseResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Creator: Azizbek Avazov
 * Date: 01.07.2022
 * Time: 12:04
 */

public final class Utilities {
    public static final String IMAGES_FOLDER_PATH = "D:\\Files\\fancy_storage\\images\\";
    public static String getCurrentDateAndTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String getFileName(String name, Users user) {
        return user.getUser_id() + "_isuserid_" + name;
    }

    public static String byteaToBase64(byte[] bytea) throws UnsupportedEncodingException {
        return new String(Base64.encodeBase64(bytea), "UTF-8");
    }

    public static JSONObject generateBaseResponse(long code, String msg, String path) {
        BaseResponse response = new BaseResponse();
        response.setCode(code);
        response.setDate(Utilities.getCurrentDateAndTime());
        response.setMsg(msg);
        response.setPath(path);

        return new JSONObject(response);
    }
}
