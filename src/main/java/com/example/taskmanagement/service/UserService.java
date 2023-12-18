package com.example.taskmanagement.service;

import com.example.taskmanagement.dao.entity.UnverifiedUser;
import com.example.taskmanagement.dao.entity.User;
import com.example.taskmanagement.mapper.UserMapper;
import com.example.taskmanagement.model.request.UserRequestDTO;
import com.example.taskmanagement.dao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UnverifiedUserService unverifiedUserService;
    private final EmailService emailService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User getUserByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.getUserByUsernameOrEmail(usernameOrEmail);
    }

    public User getBySessionId(String sessionId){
        return userRepository.getBySessionId(sessionId);
    }

    public UnverifiedUser register(UserRequestDTO userRequestDTO) {
        if (validatePassword(userRequestDTO.getPassword()) || validateEmail(userRequestDTO.getEmail())) {
            int otp = generateOtp();
            UnverifiedUser unverifiedUser = userMapper.userRequestDtoToUnverifiedUser(userRequestDTO);
            unverifiedUser.setOtp(otp);
            unverifiedUser.setPassword(passwordEncoder.encode(unverifiedUser.getPassword()));
            emailService.sendEmail(unverifiedUser.getEmail(), "Verify email", String.format("Your otp code: %d", otp));
            return unverifiedUserService.add(unverifiedUser);
        } else
            return null;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void verifyUser(Long id, int otp) {
        UnverifiedUser unverifiedUser = unverifiedUserService.getById(id);
        if (otp == unverifiedUser.getOtp()) {
            User user = userMapper.unverifiedUserToUser(unverifiedUser);
            userRepository.save(user);
            unverifiedUserService.delete(unverifiedUser);
        }
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public void delete(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("User not found. Id: %d", id)));
        userRepository.delete(user);
    }

    public void changePassword(Long id, String password){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("User not found. Id: %d", id)));
        if (validatePassword(password))
            user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        Matcher matcher = pattern.matcher(password);
        boolean isValid = matcher.matches();
        if (!isValid)
            throw new RuntimeException("Password is not valid");
        return isValid;
    }

    public boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher matcher = pattern.matcher(email);
        boolean isValid = matcher.matches();
        if (!isValid)
            throw new RuntimeException("Email is not valid");
        return isValid;
    }

    public int generateOtp() {
        return (int) (Math.random() * 1000000);
    }
}
