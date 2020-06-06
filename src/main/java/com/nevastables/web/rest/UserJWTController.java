package com.nevastables.web.rest;

import com.nevastables.domain.UserOwner;
import com.nevastables.repository.UserOwnerRepository;
import com.nevastables.repository.UserRepository;
import com.nevastables.security.jwt.JWTFilter;
import com.nevastables.security.jwt.TokenProvider;
import com.nevastables.web.rest.vm.LoginVM;
import com.nevastables.domain.User;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final UserRepository userService;
    private final UserOwnerRepository userOwnerService;

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder,
                             UserRepository userService, UserOwnerRepository userOwnerService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService  = userService;
        this.userOwnerService = userOwnerService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        System.out.println(loginVM.getUsername());
        Optional<User> u = userService.findOneByLogin(loginVM.getUsername());

        return new ResponseEntity<>(new JWTToken(jwt, u.get().getId()), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/authenticate-owner")
    public ResponseEntity<JWTToken> authorizeOwner(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        System.out.println(loginVM.getUsername());
        Optional<UserOwner> u = userOwnerService.findOneByLogin(loginVM.getUsername());

        return new ResponseEntity<>(new JWTToken(jwt, u.get().getId()), httpHeaders, HttpStatus.OK);
    }


//    @PostMapping("/authenticate-owner")
//    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVMOwner loginVM) {
//
//        UsernamePasswordAuthenticationToken authenticationToken =
//            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());
//
//        System.out.println("loginVM.getUsername()");
//        System.out.println(loginVM.getUsername());
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
//        String jwt = tokenProvider.createToken(authentication, rememberMe);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//
//        Optional<OwnerUser> u = userOwnerRepository.findOneByLogin(loginVM.getUsername());
//
//        return new ResponseEntity<>(new JWTToken(jwt, u.get().getId()), httpHeaders, HttpStatus.OK);
//    }
    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;
        private Long idUser;

        JWTToken(String idToken, Long idUser) {
            this.idToken = idToken;
            this.idUser = idUser;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_user")
        Long getIdUser() {
            return idUser;
        }
    }
}
