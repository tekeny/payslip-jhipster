package fr.maileva.connect.payslip.repository;

import fr.maileva.connect.payslip.domain.UserApi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserApi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserApiRepository extends JpaRepository<UserApi, Long> {

}
