package fr.maileva.connect.payslip.repository;

import fr.maileva.connect.payslip.domain.User2;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the User2 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface User2Repository extends JpaRepository<User2, Long> {

}
