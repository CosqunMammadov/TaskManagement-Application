package com.example.taskmanagement.service;

import com.example.taskmanagement.dao.entity.User;
import com.example.taskmanagement.mapper.UserMapper;
import com.example.taskmanagement.security.JwtService;
import com.example.taskmanagement.security.MyUserDetail;
import com.example.taskmanagement.security.data.AuthRequest;
import com.example.taskmanagement.security.data.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthResponse login(AuthRequest request){
        UserDetails userDetails;
        String sessionId = null;
        if (request.getUsernameOrEmail() != null) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsernameOrEmail(),
                            request.getPassword()
                    )
            );
            userDetails  = userDetailsService.loadUserByUsername(request.getUsernameOrEmail());
            User user = userMapper.myUserDetailToUser((MyUserDetail) userDetails);
            if (request.isRememberMe()) {
                sessionId = generateSessionId();
                user.setSessionId(sessionId);
                userService.save(user);
            }
        }else {
            userDetails = userMapper.userToMyUserDetail(userService.getBySessionId(request.getSessionId()));
            MyUserDetail myUserDetail = (MyUserDetail) userDetails;
            sessionId = myUserDetail.getSessionId();
        }
        var jwtToken = jwtService.generateToken(userDetails);
        return new AuthResponse(jwtToken, sessionId);
    }

    public String generateSessionId(){
        String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
                "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        long randomNumber = (long) (Math.random() * 1000000000000L);
        String randomNumberStr = Long.toString(randomNumber);
        String sessionId = randomNumberStr.substring(0, 3) + alphabet[(int) (Math.random() * 25)]
                + randomNumberStr.substring(3, 6) + alphabet[(int) (Math.random() * 25)]
                + randomNumberStr.substring(6, 9) + alphabet[(int) (Math.random() * 25)]
                + randomNumberStr.substring(9) + alphabet[(int) (Math.random() * 25)];
        return sessionId;
    }
}
