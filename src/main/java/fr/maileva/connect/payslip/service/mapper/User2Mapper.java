package fr.maileva.connect.payslip.service.mapper;

import fr.maileva.connect.payslip.domain.*;
import fr.maileva.connect.payslip.service.dto.User2DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link User2} and its DTO {@link User2DTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface User2Mapper extends EntityMapper<User2DTO, User2> {



    default User2 fromId(Long id) {
        if (id == null) {
            return null;
        }
        User2 user2 = new User2();
        user2.setId(id);
        return user2;
    }
}
