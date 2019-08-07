package fr.maileva.connect.payslip.service;

import fr.maileva.connect.payslip.domain.User2;
import fr.maileva.connect.payslip.repository.User2Repository;
import fr.maileva.connect.payslip.service.dto.User2DTO;
import fr.maileva.connect.payslip.service.mapper.User2Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link User2}.
 */
@Service
@Transactional
public class User2Service {

    private final Logger log = LoggerFactory.getLogger(User2Service.class);

    private final User2Repository user2Repository;

    private final User2Mapper user2Mapper;

    public User2Service(User2Repository user2Repository, User2Mapper user2Mapper) {
        this.user2Repository = user2Repository;
        this.user2Mapper = user2Mapper;
    }

    /**
     * Save a user2.
     *
     * @param user2DTO the entity to save.
     * @return the persisted entity.
     */
    public User2DTO save(User2DTO user2DTO) {
        log.debug("Request to save User2 : {}", user2DTO);
        User2 user2 = user2Mapper.toEntity(user2DTO);
        user2 = user2Repository.save(user2);
        return user2Mapper.toDto(user2);
    }

    /**
     * Get all the user2S.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<User2DTO> findAll() {
        log.debug("Request to get all User2S");
        return user2Repository.findAll().stream()
            .map(user2Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one user2 by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<User2DTO> findOne(Long id) {
        log.debug("Request to get User2 : {}", id);
        return user2Repository.findById(id)
            .map(user2Mapper::toDto);
    }

    /**
     * Delete the user2 by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete User2 : {}", id);
        user2Repository.deleteById(id);
    }
}
