package com.auth.userAuth.services;

import com.auth.userAuth.dto.LoginDto;
import com.auth.userAuth.dto.LoginResponseDto;
import com.auth.userAuth.dto.SignUpDto;
import com.auth.userAuth.dto.UserDto;
import com.auth.userAuth.entities.UserEntity;
import com.auth.userAuth.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new BadCredentialsException("Invalid Credentials"));
    }

    public UserDto signUpUser(SignUpDto signUpDto){
        Optional<UserEntity> user = userRepository.findByUsername(signUpDto.getUsername());
        if(user.isPresent()){
            throw new BadCredentialsException("User already exists: " + signUpDto.getUsername());
        }
        UserEntity userToBeCreated = modelMapper.map(signUpDto, UserEntity.class);
        userToBeCreated.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        UserEntity savedUser = userRepository.save(userToBeCreated);
        return modelMapper.map(savedUser, UserDto.class);
    }

    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new BadCredentialsException("user not found!"));
    }
}
