package fr.maileva.connect.payslip.service;

import fr.maileva.connect.payslip.domain.Emittor;
import fr.maileva.connect.payslip.repository.EmittorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Emittor}.
 */
@Service
@Transactional
public class EmittorService {

    private final Logger log = LoggerFactory.getLogger(EmittorService.class);

    private final EmittorRepository emittorRepository;

    public EmittorService(EmittorRepository emittorRepository) {
        this.emittorRepository = emittorRepository;
    }

    /**
     * Save a emittor.
     *
     * @param emittor the entity to save.
     * @return the persisted entity.
     */
    public Emittor save(Emittor emittor) {
        log.debug("Request to save Emittor : {}", emittor);
        return emittorRepository.save(emittor);
    }

    /**
     * Get all the emittors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Emittor> findAll(Pageable pageable) {
        log.debug("Request to get all Emittors");
        return emittorRepository.findAll(pageable);
    }


    /**
     * Get one emittor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Emittor> findOne(Long id) {
        log.debug("Request to get Emittor : {}", id);
        return emittorRepository.findById(id);
    }

    /**
     * Delete the emittor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Emittor : {}", id);
        emittorRepository.deleteById(id);
    }
}
