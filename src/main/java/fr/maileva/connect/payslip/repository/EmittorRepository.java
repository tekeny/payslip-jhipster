package fr.maileva.connect.payslip.repository;

import fr.maileva.connect.payslip.domain.Emittor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Emittor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmittorRepository extends JpaRepository<Emittor, Long> {

}
