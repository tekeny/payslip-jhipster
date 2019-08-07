package fr.maileva.connect.payslip.service.mapper;

import fr.maileva.connect.payslip.domain.*;
import fr.maileva.connect.payslip.service.dto.UserApiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserApi} and its DTO {@link UserApiDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserApiMapper extends EntityMapper<UserApiDTO, UserApi> {



    default UserApi fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserApi userApi = new UserApi();
        userApi.setId(id);
        return userApi;
    }
}
