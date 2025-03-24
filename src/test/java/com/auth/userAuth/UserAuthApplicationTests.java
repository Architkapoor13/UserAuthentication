package com.auth.userAuth;

import com.auth.userAuth.dto.SignUpDto;
import com.auth.userAuth.entities.UserEntity;
import com.auth.userAuth.services.JwtService;
import com.auth.userAuth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserAuthApplicationTests {
	@Autowired
	private JwtService jwtService;
	private UserService userService;

    @Test
	void contextLoads() {
	}

	@Test
	void createUser(){
		userService.signUpUser(new SignUpDto("Archit kapoor", "architkapoor13@gmail.com", "archit"));

	}

//	@Test
//	void jwtTokenTest(){
//		UserEntity user = (UserEntity) userService.loadUserByUsername("architkapoor13@gmail.com");
//		String token = jwtService.generateToken(user);
//		System.out.println(token);
//		user = jwtService.getUserFromToken(token);
//		System.out.println(user);
//	}

}
