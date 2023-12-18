package com.example.taskmanagement.mapper;

import com.example.taskmanagement.dao.entity.UnverifiedUser;
import com.example.taskmanagement.dao.entity.User;
import com.example.taskmanagement.model.request.UserRequestDTO;
import com.example.taskmanagement.security.MyUserDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userRequestDtoToUser(UserRequestDTO userRequestDTO);
    UnverifiedUser userRequestDtoToUnverifiedUser(UserRequestDTO userRequestDTO);
    User unverifiedUserToUser(UnverifiedUser unverifiedUser);
    MyUserDetail userToMyUserDetail(User user);
    User myUserDetailToUser(MyUserDetail myUserDetail);
}
