package com.example.taskmanagement.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForgetfulUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime expirationDateForVerify;
    int otp;
    boolean isChecked;
    LocalDateTime expirationDateForChange;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;
}
