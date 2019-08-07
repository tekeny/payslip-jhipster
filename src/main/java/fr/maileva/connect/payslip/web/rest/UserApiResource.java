package fr.maileva.connect.payslip.web.rest;

import fr.maileva.connect.payslip.service.UserApiService;
import fr.maileva.connect.payslip.web.rest.errors.BadRequestAlertException;
import fr.maileva.connect.payslip.service.dto.UserApiDTO;

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
 * REST controller for managing {@link fr.maileva.connect.payslip.domain.UserApi}.
 */
@RestController
@RequestMapping("/api")
public class UserApiResource {

    private final Logger log = LoggerFactory.getLogger(UserApiResource.class);

    private static final String ENTITY_NAME = "userApi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserApiService userApiService;

    public UserApiResource(UserApiService userApiService) {
        this.userApiService = userApiService;
    }

    /**
     * {@code POST  /user-apis} : Create a new userApi.
     *
     * @param userApiDTO the userApiDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userApiDTO, or with status {@code 400 (Bad Request)} if the userApi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-apis")
    public ResponseEntity<UserApiDTO> createUserApi(@RequestBody UserApiDTO userApiDTO) throws URISyntaxException {
        log.debug("REST request to save UserApi : {}", userApiDTO);
        if (userApiDTO.getId() != null) {
            throw new BadRequestAlertException("A new userApi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserApiDTO result = userApiService.save(userApiDTO);
        return ResponseEntity.created(new URI("/api/user-apis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-apis} : Updates an existing userApi.
     *
     * @param userApiDTO the userApiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userApiDTO,
     * or with status {@code 400 (Bad Request)} if the userApiDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userApiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-apis")
    public ResponseEntity<UserApiDTO> updateUserApi(@RequestBody UserApiDTO userApiDTO) throws URISyntaxException {
        log.debug("REST request to update UserApi : {}", userApiDTO);
        if (userApiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserApiDTO result = userApiService.save(userApiDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userApiDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-apis} : get all the userApis.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userApis in body.
     */
    @GetMapping("/user-apis")
    public List<UserApiDTO> getAllUserApis() {
        log.debug("REST request to get all UserApis");
        return userApiService.findAll();
    }

    /**
     * {@code GET  /user-apis/:id} : get the "id" userApi.
     *
     * @param id the id of the userApiDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userApiDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-apis/{id}")
    public ResponseEntity<UserApiDTO> getUserApi(@PathVariable Long id) {
        log.debug("REST request to get UserApi : {}", id);
        Optional<UserApiDTO> userApiDTO = userApiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userApiDTO);
    }

    /**
     * {@code DELETE  /user-apis/:id} : delete the "id" userApi.
     *
     * @param id the id of the userApiDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-apis/{id}")
    public ResponseEntity<Void> deleteUserApi(@PathVariable Long id) {
        log.debug("REST request to delete UserApi : {}", id);
        userApiService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
