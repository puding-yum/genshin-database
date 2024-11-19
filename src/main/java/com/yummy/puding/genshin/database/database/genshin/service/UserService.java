package com.yummy.puding.genshin.database.database.genshin.service;

import com.yummy.puding.genshin.database.database.genshin.client.req.RegisterReq;
import com.yummy.puding.genshin.database.database.genshin.client.res.ErrorRes;
import com.yummy.puding.genshin.database.database.genshin.client.res.RegisterRes;
import com.yummy.puding.genshin.database.database.genshin.repository.RoleRepository;
import com.yummy.puding.genshin.database.database.genshin.repository.UserRepository;
import com.yummy.puding.genshin.database.entity.RoleEntity;
import com.yummy.puding.genshin.database.entity.UserEntity;
import com.yummy.puding.genshin.database.enums.RoleEnum;
import com.yummy.puding.genshin.database.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public ResponseEntity<Object> register(RegisterReq request){
        try{
            if (Boolean.TRUE.equals(userRepository.existsByEmail(request.getEmail()))) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "User with this email exists");
            }

            if (Boolean.TRUE.equals(userRepository.existsByEmail(request.getUsername()))) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "User with this username exists");
            }

            Set<RoleEntity> roles = new HashSet<>();
            Optional<RoleEntity> userRole = roleRepository.findByName(RoleEnum.USER.name());
            if(userRole.isEmpty()){
                throw new CustomException(HttpStatus.BAD_REQUEST, "Role not found");
            }
            roles.add(userRole.get());

            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(request.getUsername());
            userEntity.setEmail(request.getEmail());
            userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
            userEntity.setRoles(roles);
            userRepository.save(userEntity);

            RegisterRes registerRes = new RegisterRes("Register success", userEntity.getUsername(), userEntity.getEmail());

            return new ResponseEntity<>(registerRes, HttpStatus.CREATED);
        }catch (CustomException ce){
            ErrorRes errorRes = new ErrorRes();
            errorRes.setMessage(ce.getMessage());
            return new ResponseEntity<>(errorRes, ce.getHttpStatus());
        }catch (Exception e){
            ErrorRes errorRes = new ErrorRes();
            errorRes.setMessage(e.getMessage());
            return new ResponseEntity<>(errorRes, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
