package com.example.taskmanagement.service;

import com.example.taskmanagement.dao.entity.ForgetfulUser;
import com.example.taskmanagement.dao.entity.User;
import com.example.taskmanagement.dao.repository.ForgetfulUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ForgetfulUserService {

    private final ForgetfulUserRepository forgetfulUserRepository;
    private final UserService userService;
    private final EmailService emailService;

    public ForgetfulUser forgotPassword(String usernameOrEmail) {
        int otp = userService.generateOtp();
        User user = userService.getUserByUsernameOrEmail(usernameOrEmail);
        emailService.sendEmail(user.getEmail(), "Change password", String.format("Your otp code: %d. You have 5 minutes.", otp));
        ForgetfulUser forgetfulUser = new ForgetfulUser();
        forgetfulUser.setExpirationDateForVerify(LocalDateTime.now().plusMinutes(5));
        forgetfulUser.setUser(user);
        forgetfulUser.setOtp(otp);
        return forgetfulUserRepository.save(forgetfulUser);
    }

    public void changePassword(Long id, String newPassword) {
        ForgetfulUser forgetfulUser = forgetfulUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Forgetful user not found. Id: %d", id)));
        if (forgetfulUser.isChecked())
            userService.changePassword(forgetfulUser.getUser().getId(), newPassword);
    }

    public boolean checkOtp(Long id, int otp) {
        ForgetfulUser forgetfulUser = forgetfulUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Forgetful user not found. Id: %d", id)));
        if (otp == forgetfulUser.getOtp()) {
            forgetfulUser.setChecked(true);
            forgetfulUser.setExpirationDateForChange(LocalDateTime.now().plusMinutes(20));
            forgetfulUserRepository.save(forgetfulUser);
            return true;
        } else
            return false;
    }

    @Scheduled(fixedRate = 120000)
    public void deleteExpiredUsers(){
        forgetfulUserRepository.deleteExpiredUsers(LocalDateTime.now());
    }
}
