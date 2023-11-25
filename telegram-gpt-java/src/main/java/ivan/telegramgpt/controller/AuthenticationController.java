package ivan.telegramgpt.controller;

import ivan.telegramgpt.dto.user.UserAuthenticationDto;
import ivan.telegramgpt.model.entity.User;
import ivan.telegramgpt.security.jwt.JwtTokenProvider;
import ivan.telegramgpt.service.AuthenticationService;
import ivan.telegramgpt.service.mapper.UserRegisterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRegisterMapper userRegisterMapper;

    @PostMapping("/register")
    public User register(@RequestBody UserAuthenticationDto user) {
        return authenticationService.register(userRegisterMapper.toModel(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserAuthenticationDto userLoginDto)
            throws RuntimeException {
        User user = authenticationService.login(userLoginDto.getUsername(),
                userLoginDto.getPassword());
        Set<User.Role> roles = new HashSet<>();
        roles.add(user.getRole());
        String token = jwtTokenProvider.createToken(user.getUsername(), roles);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        return new ResponseEntity<>(tokenMap, HttpStatus.OK);
    }
}
