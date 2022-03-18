package com.peaksoft.accounting.service;

import com.peaksoft.accounting.api.payload.UserRequest;
import com.peaksoft.accounting.api.payload.UserResponse;
import com.peaksoft.accounting.db.entity.UserEntity;
import com.peaksoft.accounting.db.mapper.UserMapper;
import com.peaksoft.accounting.db.repository.UserRepository;
import com.peaksoft.accounting.service.validation.validator.UserRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRequestValidator userRequestValidator;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    public UserResponse create(UserEntity registeredUser, UserRequest request){
        userRequestValidator.validate(registeredUser,request);
        UserEntity user = userMapper.create(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return userMapper.viewUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(format("User with email - %s, not found", email))
                );
    }
}
