package com.azizbek.fancybackservice.component;

import com.azizbek.fancybackservice.entity.Users;
import com.azizbek.fancybackservice.model.request.authentication.UserSignInRequest;
import com.azizbek.fancybackservice.model.response.BaseResponse;
import com.azizbek.fancybackservice.model.response.UserSignInResponse;
import com.azizbek.fancybackservice.security.JwtUserDetailsService;
import com.azizbek.fancybackservice.security.TokenManager;
import com.azizbek.fancybackservice.service.UsersService;
import com.azizbek.fancybackservice.util.Utilities;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Creator: Azizbek Avazov
 * Date: 21.08.2022
 * Time: 11:54
 */
@Component
public class AuthRestHelper {
    Logger logger = LoggerFactory.getLogger(ObjectRestHelper.class);
    private final String path = "/AUTH";
    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUserDetailsService userDetailsService;
    private final TokenManager tokenManager;
    private final AuthenticationManager authenticationManager;


    public AuthRestHelper(UsersService usersService, PasswordEncoder passwordEncoder, JwtUserDetailsService userDetailsService, TokenManager tokenManager, AuthenticationManager authenticationManager) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.tokenManager = tokenManager;
        this.authenticationManager = authenticationManager;
    }

    public JSONObject userRegResponse(Users request) {
        JSONObject response = new JSONObject();
        Optional<Users> findByEmail = usersService.findByEmail(request.getEmail());

        if (findByEmail.isPresent()) {
            response = Utilities.generateBaseResponse(
                    1,
                    "User with email " + request.getEmail() + " already exists !",
                    path + "/USER_REG");

            return response;
        } else if (usersService.getUserByUsername(request.getUsername()) != null) {
            response = Utilities.generateBaseResponse(
                    1,
                    "User with username " + request.getUsername() + " already exists !",
                    path + "/USER_REG");

            return response;
        } else {
            String encryptedPassword = passwordEncoder.encode(request.getPassword());
            request.setPassword(encryptedPassword);
            usersService.addUser(request);

            response = Utilities.generateBaseResponse(
                    0,
                    "Sucess!",
                    path + "/USER_REG"
            );

            return response;
        }
    }

    public JSONObject userSignInResponse(UserSignInRequest request) {
        JSONObject response = new JSONObject();
        Optional<Users> findByEmail = usersService.findByEmail(request.getEmail());

        if (findByEmail.isPresent()) {
            Users user = findByEmail.get();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            final String jwtToken = tokenManager.generateJwtToken(userDetails);

            response = Utilities.generateBaseResponse(0, "Success!", path+"/USER_SIGN_IN");
            response.put("token", jwtToken);

            JSONObject data = new JSONObject();
            data.put("email", user.getEmail());
            data.put("user_id", user.getUser_id());
            data.put("user_fullname", user.getUser_fullname());
            data.put("username", user.getUsername());
            response.put("data", data);

            return response;
        } else {
            response = Utilities.generateBaseResponse(1, "User not found!", path+"/USER_SIGN_IN");
            return response;
        }
    }
}
