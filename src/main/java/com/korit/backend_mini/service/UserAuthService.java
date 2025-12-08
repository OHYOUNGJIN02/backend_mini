package com.korit.backend_mini.service;

import com.korit.backend_mini.dto.ApiRespDto;
import com.korit.backend_mini.dto.SignupReqDto;
import com.korit.backend_mini.entity.User;
import com.korit.backend_mini.entity.UserRole;
import com.korit.backend_mini.repository.UserRepository;
import com.korit.backend_mini.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApiRespDto<?> signup(SignupReqDto signupReqDto) {
        Optional<User> foundUser = userRepository.getUserByEmail(signupReqDto.getEmail());
        if (foundUser.isPresent()) {
            return new ApiRespDto<>("failed", "이미 존재하는 이메일 입니다.", null);
        }
        
        Optional<User> foundUserByUsername = userRepository.getUserByUsername(signupReqDto.getUsername());
        if (foundUserByUsername.isPresent()) {
            return new ApiRespDto<>("failed", "이미 존재하는 사용자 이름 입니다.", null);
        }

        Optional<User> optionalUser = userRepository.addUser(signupReqDto.toEntity(bCryptPasswordEncoder));

        UserRole userRole = UserRole.builder()
                .userId(optionalUser.get().getUserId())
                .roleId(3)
                .build();

        userRoleRepository.addUserRole(userRole);

        return new ApiRespDto<>("success", "회원가입이 완료되었습니다.", optionalUser.get());
    }
}
