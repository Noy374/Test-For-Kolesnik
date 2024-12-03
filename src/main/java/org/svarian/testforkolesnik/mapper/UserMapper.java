package org.svarian.testforkolesnik.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.svarian.testforkolesnik.entity.User;
import org.svarian.testforkolesnik.web.rest.dto.UserDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {


    UserDTO toDTO(User user);

    User fromDTO(UserDTO userDTO);
}
