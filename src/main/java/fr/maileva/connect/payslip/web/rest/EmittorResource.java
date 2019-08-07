package fr.maileva.connect.payslip.web.rest;

import fr.maileva.connect.payslip.domain.Emittor;
import fr.maileva.connect.payslip.repository.EmittorRepository;
import fr.maileva.connect.payslip.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.maileva.connect.payslip.domain.Emittor}.
 */
@RestController
@RequestMapping("/api")
public class EmittorResource {

    private final Logger log = LoggerFactory.getLogger(EmittorResource.class);

    private static final String ENTITY_NAME = "emittor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmittorRepository emittorRepository;

    public EmittorResource(EmittorRepository emittorRepository) {
        this.emittorRepository = emittorRepository;
    }

    /**
     * {@code POST  /emittors} : Create a new emittor.
     *
     * @param emittor the emittor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emittor, or with status {@code 400 (Bad Request)} if the emittor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emittors")
    public ResponseEntity<Emittor> createEmittor(@Valid @RequestBody Emittor emittor) throws URISyntaxException {
        log.debug("REST request to save Emittor : {}", emittor);
        if (emittor.getId() != null) {
            throw new BadRequestAlertException("A new emittor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Emittor result = emittorRepository.save(emittor);
        return ResponseEntity.created(new URI("/api/emittors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emittors} : Updates an existing emittor.
     *
     * @param emittor the emittor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emittor,
     * or with status {@code 400 (Bad Request)} if the emittor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emittor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emittors")
    public ResponseEntity<Emittor> updateEmittor(@Valid @RequestBody Emittor emittor) throws URISyntaxException {
        log.debug("REST request to update Emittor : {}", emittor);
        if (emittor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Emittor result = emittorRepository.save(emittor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, emittor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /emittors} : get all the emittors.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emittors in body.
     */
    @GetMapping("/emittors")
    public ResponseEntity<List<Emittor>> getAllEmittors(Pageable pageable) {
        log.debug("REST request to get a page of Emittors");
        Page<Emittor> page = emittorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emittors/:id} : get the "id" emittor.
     *
     * @param id the id of the emittor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emittor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emittors/{id}")
    public ResponseEntity<Emittor> getEmittor(@PathVariable Long id) {
        log.debug("REST request to get Emittor : {}", id);
        Optional<Emittor> emittor = emittorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(emittor);
    }

    /**
     * {@code DELETE  /emittors/:id} : delete the "id" emittor.
     *
     * @param id the id of the emittor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emittors/{id}")
    public ResponseEntity<Void> deleteEmittor(@PathVariable Long id) {
        log.debug("REST request to delete Emittor : {}", id);
        emittorRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
