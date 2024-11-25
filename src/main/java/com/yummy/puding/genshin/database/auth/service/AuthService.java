package com.yummy.puding.genshin.database.auth.service;

import com.yummy.puding.genshin.database.auth.client.req.LoginReq;
import com.yummy.puding.genshin.database.auth.client.res.LoginRes;
import com.yummy.puding.genshin.database.auth.config.security.JwtUtil;
import com.yummy.puding.genshin.database.auth.repository.UserRepository;
import com.yummy.puding.genshin.database.entity.UserEntity;
import com.yummy.puding.genshin.database.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<Object> login(LoginReq requestBody){
        try {
            Optional<UserEntity> user = userRepository.findByUsername(requestBody.getUsername());
            if (user.isEmpty()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Email or password incorrect");
            }

            Boolean isPasswordCorrect = passwordEncoder.matches(requestBody.getPassword(), user.get().getPassword());
            if (Boolean.FALSE.equals(isPasswordCorrect)) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Email or password incorrect");
            }

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestBody.getUsername(), requestBody.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtil.generateToken(requestBody.getUsername());
            LoginRes loginRes = new LoginRes();
            loginRes.setUsername(requestBody.getUsername());
            loginRes.setToken(jwt);

            return new ResponseEntity<>(loginRes, HttpStatus.OK);
        } catch (CustomException ce){
            ce.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
