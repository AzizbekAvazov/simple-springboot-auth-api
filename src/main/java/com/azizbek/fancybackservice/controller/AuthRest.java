package com.azizbek.fancybackservice.controller;

import com.azizbek.fancybackservice.component.AuthRestHelper;
import com.azizbek.fancybackservice.entity.Users;
import com.azizbek.fancybackservice.model.request.authentication.UserSignInRequest;
import com.azizbek.fancybackservice.model.response.UserSignInResponse;
import com.azizbek.fancybackservice.security.JwtUserDetailsService;
import com.azizbek.fancybackservice.security.TokenManager;
import com.azizbek.fancybackservice.service.UsersService;
import com.azizbek.fancybackservice.util.Utilities;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by Azizbek Avazov on июнь, 2022
 */

@RestController
@RequestMapping("/AUTH")
public class AuthRest {
    private final String path = "/AUTH";
    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUserDetailsService userDetailsService;
    private final TokenManager tokenManager;
    private final AuthenticationManager authenticationManager;
    private final AuthRestHelper authRestHelper;

    public AuthRest(UsersService usersService, JwtUserDetailsService userDetailsService,
                    PasswordEncoder passwordEncoder, TokenManager tokenManager,
                    AuthenticationManager authenticationManager, AuthRestHelper authRestHelper) {
        this.usersService = usersService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.tokenManager = tokenManager;
        this.authenticationManager = authenticationManager;
        this.authRestHelper = authRestHelper;
    }

    @PostMapping("/USER_REG")
    public ResponseEntity user_reg(@RequestBody Users request) {
        JSONObject response = authRestHelper.userRegResponse(request);
        long code = response.getLong("code");

        if (code != 0) {
            return new ResponseEntity(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity(response.toString(), HttpStatus.OK);
        }

        /*Optional<Users> findByEmail = usersService.findByEmail(request.getEmail());
        // check if user exists

        if (findByEmail.isPresent()) {
            BaseResponse response = new BaseResponse();
            response.setCode(1L);
            response.setMsg("User with email " + request.getEmail() + " already exists !");
            response.setPath(path+"/USER_REG");
            response.setDate(Utilities.getCurrentDateAndTime());

            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            String encryptedPassword = passwordEncoder.encode(request.getPassword());
            request.setPassword(encryptedPassword);
            usersService.addUser(request);

            BaseResponse response = new BaseResponse();
            response.setCode(0L);
            response.setMsg("Success !");
            response.setPath(path+"/USER_REG");
            response.setDate(Utilities.getCurrentDateAndTime());

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }*/
    }

    @PostMapping("/USER_SIGN_IN")
    public ResponseEntity user_sign_in(@RequestBody UserSignInRequest request) {
        JSONObject response = authRestHelper.userSignInResponse(request);
        long code = response.getLong("code");

        if (code != 0) {
            return new ResponseEntity(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity(response.toString(), HttpStatus.OK);
        }
        /*Optional<Users> findByEmail = usersService.findByEmail(request.getEmail());

        if (findByEmail.isPresent()) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            final String jwtToken = tokenManager.generateJwtToken(userDetails);

            UserSignInResponse response = new UserSignInResponse();
            response.setCode(0L);
            response.setDate(Utilities.getCurrentDateAndTime());
            response.setMsg("Success");
            response.setPath(path+"/USER_SIGN_IN");
            response.setToken(jwtToken);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } else {
            UserSignInResponse response = new UserSignInResponse();
            response.setCode(1L);
            response.setDate(Utilities.getCurrentDateAndTime());
            response.setMsg("User not found");
            response.setPath(path+"/USER_SIGN_IN");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }*/
    }
}
