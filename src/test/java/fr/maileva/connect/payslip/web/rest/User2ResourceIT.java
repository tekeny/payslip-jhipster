package fr.maileva.connect.payslip.web.rest;

import fr.maileva.connect.payslip.PayslipApp;
import fr.maileva.connect.payslip.domain.User2;
import fr.maileva.connect.payslip.repository.User2Repository;
import fr.maileva.connect.payslip.service.User2Service;
import fr.maileva.connect.payslip.service.dto.User2DTO;
import fr.maileva.connect.payslip.service.mapper.User2Mapper;
import fr.maileva.connect.payslip.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static fr.maileva.connect.payslip.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link User2Resource} REST controller.
 */
@SpringBootTest(classes = PayslipApp.class)
public class User2ResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_USER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_USER_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DATE = "BBBBBBBBBB";

    @Autowired
    private User2Repository user2Repository;

    @Autowired
    private User2Mapper user2Mapper;

    @Autowired
    private User2Service user2Service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restUser2MockMvc;

    private User2 user2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final User2Resource user2Resource = new User2Resource(user2Service);
        this.restUser2MockMvc = MockMvcBuilders.standaloneSetup(user2Resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static User2 createEntity(EntityManager em) {
        User2 user2 = new User2()
            .userId(DEFAULT_USER_ID)
            .userLogin(DEFAULT_USER_LOGIN)
            .date(DEFAULT_DATE);
        return user2;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static User2 createUpdatedEntity(EntityManager em) {
        User2 user2 = new User2()
            .userId(UPDATED_USER_ID)
            .userLogin(UPDATED_USER_LOGIN)
            .date(UPDATED_DATE);
        return user2;
    }

    @BeforeEach
    public void initTest() {
        user2 = createEntity(em);
    }

    @Test
    @Transactional
    public void createUser2() throws Exception {
        int databaseSizeBeforeCreate = user2Repository.findAll().size();

        // Create the User2
        User2DTO user2DTO = user2Mapper.toDto(user2);
        restUser2MockMvc.perform(post("/api/user-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user2DTO)))
            .andExpect(status().isCreated());

        // Validate the User2 in the database
        List<User2> user2List = user2Repository.findAll();
        assertThat(user2List).hasSize(databaseSizeBeforeCreate + 1);
        User2 testUser2 = user2List.get(user2List.size() - 1);
        assertThat(testUser2.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUser2.getUserLogin()).isEqualTo(DEFAULT_USER_LOGIN);
        assertThat(testUser2.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createUser2WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = user2Repository.findAll().size();

        // Create the User2 with an existing ID
        user2.setId(1L);
        User2DTO user2DTO = user2Mapper.toDto(user2);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUser2MockMvc.perform(post("/api/user-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user2DTO)))
            .andExpect(status().isBadRequest());

        // Validate the User2 in the database
        List<User2> user2List = user2Repository.findAll();
        assertThat(user2List).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUser2S() throws Exception {
        // Initialize the database
        user2Repository.saveAndFlush(user2);

        // Get all the user2List
        restUser2MockMvc.perform(get("/api/user-2-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(user2.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].userLogin").value(hasItem(DEFAULT_USER_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getUser2() throws Exception {
        // Initialize the database
        user2Repository.saveAndFlush(user2);

        // Get the user2
        restUser2MockMvc.perform(get("/api/user-2-s/{id}", user2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(user2.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.userLogin").value(DEFAULT_USER_LOGIN.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUser2() throws Exception {
        // Get the user2
        restUser2MockMvc.perform(get("/api/user-2-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUser2() throws Exception {
        // Initialize the database
        user2Repository.saveAndFlush(user2);

        int databaseSizeBeforeUpdate = user2Repository.findAll().size();

        // Update the user2
        User2 updatedUser2 = user2Repository.findById(user2.getId()).get();
        // Disconnect from session so that the updates on updatedUser2 are not directly saved in db
        em.detach(updatedUser2);
        updatedUser2
            .userId(UPDATED_USER_ID)
            .userLogin(UPDATED_USER_LOGIN)
            .date(UPDATED_DATE);
        User2DTO user2DTO = user2Mapper.toDto(updatedUser2);

        restUser2MockMvc.perform(put("/api/user-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user2DTO)))
            .andExpect(status().isOk());

        // Validate the User2 in the database
        List<User2> user2List = user2Repository.findAll();
        assertThat(user2List).hasSize(databaseSizeBeforeUpdate);
        User2 testUser2 = user2List.get(user2List.size() - 1);
        assertThat(testUser2.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUser2.getUserLogin()).isEqualTo(UPDATED_USER_LOGIN);
        assertThat(testUser2.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingUser2() throws Exception {
        int databaseSizeBeforeUpdate = user2Repository.findAll().size();

        // Create the User2
        User2DTO user2DTO = user2Mapper.toDto(user2);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUser2MockMvc.perform(put("/api/user-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user2DTO)))
            .andExpect(status().isBadRequest());

        // Validate the User2 in the database
        List<User2> user2List = user2Repository.findAll();
        assertThat(user2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUser2() throws Exception {
        // Initialize the database
        user2Repository.saveAndFlush(user2);

        int databaseSizeBeforeDelete = user2Repository.findAll().size();

        // Delete the user2
        restUser2MockMvc.perform(delete("/api/user-2-s/{id}", user2.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<User2> user2List = user2Repository.findAll();
        assertThat(user2List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(User2.class);
        User2 user21 = new User2();
        user21.setId(1L);
        User2 user22 = new User2();
        user22.setId(user21.getId());
        assertThat(user21).isEqualTo(user22);
        user22.setId(2L);
        assertThat(user21).isNotEqualTo(user22);
        user21.setId(null);
        assertThat(user21).isNotEqualTo(user22);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(User2DTO.class);
        User2DTO user2DTO1 = new User2DTO();
        user2DTO1.setId(1L);
        User2DTO user2DTO2 = new User2DTO();
        assertThat(user2DTO1).isNotEqualTo(user2DTO2);
        user2DTO2.setId(user2DTO1.getId());
        assertThat(user2DTO1).isEqualTo(user2DTO2);
        user2DTO2.setId(2L);
        assertThat(user2DTO1).isNotEqualTo(user2DTO2);
        user2DTO1.setId(null);
        assertThat(user2DTO1).isNotEqualTo(user2DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(user2Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(user2Mapper.fromId(null)).isNull();
    }
}
