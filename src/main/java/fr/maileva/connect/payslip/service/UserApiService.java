package fr.maileva.connect.payslip.service;

import fr.maileva.connect.payslip.domain.UserApi;
import fr.maileva.connect.payslip.repository.UserApiRepository;
import fr.maileva.connect.payslip.service.dto.UserApiDTO;
import fr.maileva.connect.payslip.service.mapper.UserApiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserApi}.
 */
@Service
@Transactional
public class UserApiService {

    private final Logger log = LoggerFactory.getLogger(UserApiService.class);

    private final UserApiRepository userApiRepository;

    private final UserApiMapper userApiMapper;

    public UserApiService(UserApiRepository userApiRepository, UserApiMapper userApiMapper) {
        this.userApiRepository = userApiRepository;
        this.userApiMapper = userApiMapper;
    }

    /**
     * Save a userApi.
     *
     * @param userApiDTO the entity to save.
     * @return the persisted entity.
     */
    public UserApiDTO save(UserApiDTO userApiDTO) {
        log.debug("Request to save UserApi : {}", userApiDTO);
        UserApi userApi = userApiMapper.toEntity(userApiDTO);
        userApi = userApiRepository.save(userApi);
        return userApiMapper.toDto(userApi);
    }

    /**
     * Get all the userApis.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserApiDTO> findAll() {
        log.debug("Request to get all UserApis");
        return userApiRepository.findAll().stream()
            .map(userApiMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one userApi by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserApiDTO> findOne(Long id) {
        log.debug("Request to get UserApi : {}", id);
        return userApiRepository.findById(id)
            .map(userApiMapper::toDto);
    }

    /**
     * Delete the userApi by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserApi : {}", id);
        userApiRepository.deleteById(id);
    }
}
