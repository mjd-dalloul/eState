package com.example.estate.api;

import com.example.estate.configuration.security.JwtTokenUtil;
import com.example.estate.domain.dto.*;
import com.example.estate.domain.dto.auth_dto.*;
import com.example.estate.domain.entity.ApplicationUser;
import com.example.estate.service.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/public")
@RequiredArgsConstructor
@Profile("!receiver")
public class AuthApi {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final ApplicationUserService userService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AuthorizedUserView>> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            Authentication authenticate = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword()));

            ApplicationUser user = ((CustomUserSecurity) authenticate.getPrincipal()).getUser();
            final AuthorizedUserView retUser = new AuthorizedUserView(
                    user,
                    jwtTokenUtil.generateAccessToken(user)
            );
            return ResponseEntity.ok().body(
                    new BaseResponse<>(retUser, true, HttpStatus.OK.value())
            );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<AuthorizedUserView>> register(@RequestBody @Valid CreateUserRequest request) {
        final ApplicationUser newUser = userService.create(request);
        final AuthorizedUserView ret = new AuthorizedUserView();
        final UserView userView = new UserView(newUser.getId(), newUser.getEmail(), newUser.getFullName());
        ret.setToken(jwtTokenUtil.generateAccessToken(newUser));
        ret.setUser(newUser);
        return ResponseEntity.ok().body(
                new BaseResponse<>(ret, true, HttpStatus.CREATED.value())
        );
    }

}
