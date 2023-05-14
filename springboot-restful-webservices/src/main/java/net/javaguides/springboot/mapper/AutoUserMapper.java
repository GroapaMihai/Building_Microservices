package net.javaguides.springboot.mapper;

import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoUserMapper {

    AutoUserMapper MAPPER = Mappers.getMapper(AutoUserMapper.class);

    // Sometimes the fields might have different names, so we specify the mapping
    // @Mapping(source = "email", target = "emailAddress")
    UserDto mapToUserDto(User user);

    // @Mapping(source = "emailAddress", target = "email")
    User mapToUser(UserDto userDto);
}
