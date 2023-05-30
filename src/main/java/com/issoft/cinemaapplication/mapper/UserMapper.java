package com.issoft.cinemaapplication.mapper;

import com.issoft.cinemaapplication.dto.user.UserInDto;
import com.issoft.cinemaapplication.dto.user.UserOutDto;
import com.issoft.cinemaapplication.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "systemRoleId", source = "systemRole.id")
    UserOutDto toDto(final User user);

    List<UserOutDto> toDto(final List<User> users);

    @Mapping(target = "systemRole.id", source = "userInDto.systemRoleId")
    User toEntity(final UserInDto userInDto);

    @Mapping(target = "systemRole.id", source = "userOutDto.systemRoleId")
    User toEntity(final UserOutDto userOutDto);

    @Mappings({
            @Mapping(target = "id", source = "user.id"),
            @Mapping(target = "name", source = "userInDto.name"),
            @Mapping(target = "surname", source = "userInDto.surname"),
            @Mapping(target = "login", source = "userInDto.login"),
            @Mapping(target = "password", source = "userInDto.password"),
            @Mapping(target = "email", source = "userInDto.email"),
            @Mapping(target = "phone", source = "userInDto.phone"),
            @Mapping(target = "discount", source = "userInDto.discount"),
            @Mapping(target = "systemRole.id", source = "userInDto.systemRoleId")
    })
    User fillFrom(final User user, final UserInDto userInDto);
}
