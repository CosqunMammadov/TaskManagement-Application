package com.example.taskmanagement.service;

import com.example.taskmanagement.dao.entity.UnverifiedUser;
import com.example.taskmanagement.dao.repository.UnverifiedUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnverifiedUserService {

    private final UnverifiedUserRepository unverifiedUserRepository;

    public UnverifiedUser add(UnverifiedUser unverifiedUser){
        return unverifiedUserRepository.save(unverifiedUser);
    }

    public void delete(UnverifiedUser unverifiedUser){
        unverifiedUserRepository.delete(unverifiedUser);
    }

    public UnverifiedUser getById(Long id){
        return unverifiedUserRepository.findById(id).orElseThrow(() -> new RuntimeException("unverified user not found id: " + id));
    }
}
