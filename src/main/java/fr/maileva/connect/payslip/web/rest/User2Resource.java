package fr.maileva.connect.payslip.web.rest;

import fr.maileva.connect.payslip.service.User2Service;
import fr.maileva.connect.payslip.web.rest.errors.BadRequestAlertException;
import fr.maileva.connect.payslip.service.dto.User2DTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.maileva.connect.payslip.domain.User2}.
 */
@RestController
@RequestMapping("/api")
public class User2Resource {

    private final Logger log = LoggerFactory.getLogger(User2Resource.class);

    private static final String ENTITY_NAME = "user2";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final User2Service user2Service;

    public User2Resource(User2Service user2Service) {
        this.user2Service = user2Service;
    }

    /**
     * {@code POST  /user-2-s} : Create a new user2.
     *
     * @param user2DTO the user2DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new user2DTO, or with status {@code 400 (Bad Request)} if the user2 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-2-s")
    public ResponseEntity<User2DTO> createUser2(@RequestBody User2DTO user2DTO) throws URISyntaxException {
        log.debug("REST request to save User2 : {}", user2DTO);
        if (user2DTO.getId() != null) {
            throw new BadRequestAlertException("A new user2 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User2DTO result = user2Service.save(user2DTO);
        return ResponseEntity.created(new URI("/api/user-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-2-s} : Updates an existing user2.
     *
     * @param user2DTO the user2DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated user2DTO,
     * or with status {@code 400 (Bad Request)} if the user2DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the user2DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-2-s")
    public ResponseEntity<User2DTO> updateUser2(@RequestBody User2DTO user2DTO) throws URISyntaxException {
        log.debug("REST request to update User2 : {}", user2DTO);
        if (user2DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        User2DTO result = user2Service.save(user2DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, user2DTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-2-s} : get all the user2S.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of user2S in body.
     */
    @GetMapping("/user-2-s")
    public List<User2DTO> getAllUser2S() {
        log.debug("REST request to get all User2S");
        return user2Service.findAll();
    }

    /**
     * {@code GET  /user-2-s/:id} : get the "id" user2.
     *
     * @param id the id of the user2DTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the user2DTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-2-s/{id}")
    public ResponseEntity<User2DTO> getUser2(@PathVariable Long id) {
        log.debug("REST request to get User2 : {}", id);
        Optional<User2DTO> user2DTO = user2Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(user2DTO);
    }

    /**
     * {@code DELETE  /user-2-s/:id} : delete the "id" user2.
     *
     * @param id the id of the user2DTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-2-s/{id}")
    public ResponseEntity<Void> deleteUser2(@PathVariable Long id) {
        log.debug("REST request to delete User2 : {}", id);
        user2Service.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
