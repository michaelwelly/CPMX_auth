package com.croco.auth.mapper;

import com.croco.auth.dto.UserDTO;
import com.croco.auth.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);
}
