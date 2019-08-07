package fr.maileva.connect.payslip.web.rest;

import fr.maileva.connect.payslip.PayslipApp;
import fr.maileva.connect.payslip.domain.Emittor;
import fr.maileva.connect.payslip.domain.UserApi;
import fr.maileva.connect.payslip.repository.EmittorRepository;
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
 * Integration tests for the {@link EmittorResource} REST controller.
 */
@SpringBootTest(classes = PayslipApp.class)
public class EmittorResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_SIRET = "1";
    private static final String UPDATED_COMPANY_SIRET = "8";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CAN_EMIT = false;
    private static final Boolean UPDATED_CAN_EMIT = true;

    private static final String DEFAULT_CAN_EMIT_SINCE = "AAAAAAAAAA";
    private static final String UPDATED_CAN_EMIT_SINCE = "BBBBBBBBBB";

    @Autowired
    private EmittorRepository emittorRepository;

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

    private MockMvc restEmittorMockMvc;

    private Emittor emittor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmittorResource emittorResource = new EmittorResource(emittorRepository);
        this.restEmittorMockMvc = MockMvcBuilders.standaloneSetup(emittorResource)
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
    public static Emittor createEntity(EntityManager em) {
        Emittor emittor = new Emittor()
            .code(DEFAULT_CODE)
            .companyName(DEFAULT_COMPANY_NAME)
            .companySiret(DEFAULT_COMPANY_SIRET)
            .login(DEFAULT_LOGIN)
            .canEmit(DEFAULT_CAN_EMIT)
            .canEmitSince(DEFAULT_CAN_EMIT_SINCE);
        // Add required entity
        UserApi userApi;
        if (TestUtil.findAll(em, UserApi.class).isEmpty()) {
            userApi = UserApiResourceIT.createEntity(em);
            em.persist(userApi);
            em.flush();
        } else {
            userApi = TestUtil.findAll(em, UserApi.class).get(0);
        }
        emittor.setCreatedBy(userApi);
        return emittor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emittor createUpdatedEntity(EntityManager em) {
        Emittor emittor = new Emittor()
            .code(UPDATED_CODE)
            .companyName(UPDATED_COMPANY_NAME)
            .companySiret(UPDATED_COMPANY_SIRET)
            .login(UPDATED_LOGIN)
            .canEmit(UPDATED_CAN_EMIT)
            .canEmitSince(UPDATED_CAN_EMIT_SINCE);
        // Add required entity
        UserApi userApi;
        if (TestUtil.findAll(em, UserApi.class).isEmpty()) {
            userApi = UserApiResourceIT.createUpdatedEntity(em);
            em.persist(userApi);
            em.flush();
        } else {
            userApi = TestUtil.findAll(em, UserApi.class).get(0);
        }
        emittor.setCreatedBy(userApi);
        return emittor;
    }

    @BeforeEach
    public void initTest() {
        emittor = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmittor() throws Exception {
        int databaseSizeBeforeCreate = emittorRepository.findAll().size();

        // Create the Emittor
        restEmittorMockMvc.perform(post("/api/emittors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emittor)))
            .andExpect(status().isCreated());

        // Validate the Emittor in the database
        List<Emittor> emittorList = emittorRepository.findAll();
        assertThat(emittorList).hasSize(databaseSizeBeforeCreate + 1);
        Emittor testEmittor = emittorList.get(emittorList.size() - 1);
        assertThat(testEmittor.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEmittor.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testEmittor.getCompanySiret()).isEqualTo(DEFAULT_COMPANY_SIRET);
        assertThat(testEmittor.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testEmittor.isCanEmit()).isEqualTo(DEFAULT_CAN_EMIT);
        assertThat(testEmittor.getCanEmitSince()).isEqualTo(DEFAULT_CAN_EMIT_SINCE);
    }

    @Test
    @Transactional
    public void createEmittorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emittorRepository.findAll().size();

        // Create the Emittor with an existing ID
        emittor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmittorMockMvc.perform(post("/api/emittors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emittor)))
            .andExpect(status().isBadRequest());

        // Validate the Emittor in the database
        List<Emittor> emittorList = emittorRepository.findAll();
        assertThat(emittorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = emittorRepository.findAll().size();
        // set the field null
        emittor.setCompanyName(null);

        // Create the Emittor, which fails.

        restEmittorMockMvc.perform(post("/api/emittors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emittor)))
            .andExpect(status().isBadRequest());

        List<Emittor> emittorList = emittorRepository.findAll();
        assertThat(emittorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompanySiretIsRequired() throws Exception {
        int databaseSizeBeforeTest = emittorRepository.findAll().size();
        // set the field null
        emittor.setCompanySiret(null);

        // Create the Emittor, which fails.

        restEmittorMockMvc.perform(post("/api/emittors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emittor)))
            .andExpect(status().isBadRequest());

        List<Emittor> emittorList = emittorRepository.findAll();
        assertThat(emittorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = emittorRepository.findAll().size();
        // set the field null
        emittor.setLogin(null);

        // Create the Emittor, which fails.

        restEmittorMockMvc.perform(post("/api/emittors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emittor)))
            .andExpect(status().isBadRequest());

        List<Emittor> emittorList = emittorRepository.findAll();
        assertThat(emittorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmittors() throws Exception {
        // Initialize the database
        emittorRepository.saveAndFlush(emittor);

        // Get all the emittorList
        restEmittorMockMvc.perform(get("/api/emittors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emittor.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].companySiret").value(hasItem(DEFAULT_COMPANY_SIRET.toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].canEmit").value(hasItem(DEFAULT_CAN_EMIT.booleanValue())))
            .andExpect(jsonPath("$.[*].canEmitSince").value(hasItem(DEFAULT_CAN_EMIT_SINCE.toString())));
    }
    
    @Test
    @Transactional
    public void getEmittor() throws Exception {
        // Initialize the database
        emittorRepository.saveAndFlush(emittor);

        // Get the emittor
        restEmittorMockMvc.perform(get("/api/emittors/{id}", emittor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emittor.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.companySiret").value(DEFAULT_COMPANY_SIRET.toString()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.canEmit").value(DEFAULT_CAN_EMIT.booleanValue()))
            .andExpect(jsonPath("$.canEmitSince").value(DEFAULT_CAN_EMIT_SINCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmittor() throws Exception {
        // Get the emittor
        restEmittorMockMvc.perform(get("/api/emittors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmittor() throws Exception {
        // Initialize the database
        emittorRepository.saveAndFlush(emittor);

        int databaseSizeBeforeUpdate = emittorRepository.findAll().size();

        // Update the emittor
        Emittor updatedEmittor = emittorRepository.findById(emittor.getId()).get();
        // Disconnect from session so that the updates on updatedEmittor are not directly saved in db
        em.detach(updatedEmittor);
        updatedEmittor
            .code(UPDATED_CODE)
            .companyName(UPDATED_COMPANY_NAME)
            .companySiret(UPDATED_COMPANY_SIRET)
            .login(UPDATED_LOGIN)
            .canEmit(UPDATED_CAN_EMIT)
            .canEmitSince(UPDATED_CAN_EMIT_SINCE);

        restEmittorMockMvc.perform(put("/api/emittors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmittor)))
            .andExpect(status().isOk());

        // Validate the Emittor in the database
        List<Emittor> emittorList = emittorRepository.findAll();
        assertThat(emittorList).hasSize(databaseSizeBeforeUpdate);
        Emittor testEmittor = emittorList.get(emittorList.size() - 1);
        assertThat(testEmittor.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEmittor.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testEmittor.getCompanySiret()).isEqualTo(UPDATED_COMPANY_SIRET);
        assertThat(testEmittor.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testEmittor.isCanEmit()).isEqualTo(UPDATED_CAN_EMIT);
        assertThat(testEmittor.getCanEmitSince()).isEqualTo(UPDATED_CAN_EMIT_SINCE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmittor() throws Exception {
        int databaseSizeBeforeUpdate = emittorRepository.findAll().size();

        // Create the Emittor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmittorMockMvc.perform(put("/api/emittors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emittor)))
            .andExpect(status().isBadRequest());

        // Validate the Emittor in the database
        List<Emittor> emittorList = emittorRepository.findAll();
        assertThat(emittorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmittor() throws Exception {
        // Initialize the database
        emittorRepository.saveAndFlush(emittor);

        int databaseSizeBeforeDelete = emittorRepository.findAll().size();

        // Delete the emittor
        restEmittorMockMvc.perform(delete("/api/emittors/{id}", emittor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Emittor> emittorList = emittorRepository.findAll();
        assertThat(emittorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Emittor.class);
        Emittor emittor1 = new Emittor();
        emittor1.setId(1L);
        Emittor emittor2 = new Emittor();
        emittor2.setId(emittor1.getId());
        assertThat(emittor1).isEqualTo(emittor2);
        emittor2.setId(2L);
        assertThat(emittor1).isNotEqualTo(emittor2);
        emittor1.setId(null);
        assertThat(emittor1).isNotEqualTo(emittor2);
    }
}
