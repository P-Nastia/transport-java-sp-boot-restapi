package org.example.data.mappers;

import org.example.data.dtos.account.RegisterUserDTO;
import org.example.data.dtos.account.UserItemDTO;
import org.example.entities.account.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserItemDTO toDTO(UserEntity category);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserEntity fromRegisterDTO(RegisterUserDTO dto);
}
