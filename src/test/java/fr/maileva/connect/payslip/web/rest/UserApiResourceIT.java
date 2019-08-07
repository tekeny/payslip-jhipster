package fr.maileva.connect.payslip.web.rest;

import fr.maileva.connect.payslip.PayslipApp;
import fr.maileva.connect.payslip.domain.UserApi;
import fr.maileva.connect.payslip.repository.UserApiRepository;
import fr.maileva.connect.payslip.service.UserApiService;
import fr.maileva.connect.payslip.service.dto.UserApiDTO;
import fr.maileva.connect.payslip.service.mapper.UserApiMapper;
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
 * Integration tests for the {@link UserApiResource} REST controller.
 */
@SpringBootTest(classes = PayslipApp.class)
public class UserApiResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_USER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_USER_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DATE = "BBBBBBBBBB";

    @Autowired
    private UserApiRepository userApiRepository;

    @Autowired
    private UserApiMapper userApiMapper;

    @Autowired
    private UserApiService userApiService;

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

    private MockMvc restUserApiMockMvc;

    private UserApi userApi;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserApiResource userApiResource = new UserApiResource(userApiService);
        this.restUserApiMockMvc = MockMvcBuilders.standaloneSetup(userApiResource)
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
    public static UserApi createEntity(EntityManager em) {
        UserApi userApi = new UserApi()
            .userId(DEFAULT_USER_ID)
            .userLogin(DEFAULT_USER_LOGIN)
            .date(DEFAULT_DATE);
        return userApi;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserApi createUpdatedEntity(EntityManager em) {
        UserApi userApi = new UserApi()
            .userId(UPDATED_USER_ID)
            .userLogin(UPDATED_USER_LOGIN)
            .date(UPDATED_DATE);
        return userApi;
    }

    @BeforeEach
    public void initTest() {
        userApi = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserApi() throws Exception {
        int databaseSizeBeforeCreate = userApiRepository.findAll().size();

        // Create the UserApi
        UserApiDTO userApiDTO = userApiMapper.toDto(userApi);
        restUserApiMockMvc.perform(post("/api/user-apis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userApiDTO)))
            .andExpect(status().isCreated());

        // Validate the UserApi in the database
        List<UserApi> userApiList = userApiRepository.findAll();
        assertThat(userApiList).hasSize(databaseSizeBeforeCreate + 1);
        UserApi testUserApi = userApiList.get(userApiList.size() - 1);
        assertThat(testUserApi.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserApi.getUserLogin()).isEqualTo(DEFAULT_USER_LOGIN);
        assertThat(testUserApi.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createUserApiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userApiRepository.findAll().size();

        // Create the UserApi with an existing ID
        userApi.setId(1L);
        UserApiDTO userApiDTO = userApiMapper.toDto(userApi);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserApiMockMvc.perform(post("/api/user-apis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userApiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserApi in the database
        List<UserApi> userApiList = userApiRepository.findAll();
        assertThat(userApiList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserApis() throws Exception {
        // Initialize the database
        userApiRepository.saveAndFlush(userApi);

        // Get all the userApiList
        restUserApiMockMvc.perform(get("/api/user-apis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userApi.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].userLogin").value(hasItem(DEFAULT_USER_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getUserApi() throws Exception {
        // Initialize the database
        userApiRepository.saveAndFlush(userApi);

        // Get the userApi
        restUserApiMockMvc.perform(get("/api/user-apis/{id}", userApi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userApi.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.userLogin").value(DEFAULT_USER_LOGIN.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserApi() throws Exception {
        // Get the userApi
        restUserApiMockMvc.perform(get("/api/user-apis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserApi() throws Exception {
        // Initialize the database
        userApiRepository.saveAndFlush(userApi);

        int databaseSizeBeforeUpdate = userApiRepository.findAll().size();

        // Update the userApi
        UserApi updatedUserApi = userApiRepository.findById(userApi.getId()).get();
        // Disconnect from session so that the updates on updatedUserApi are not directly saved in db
        em.detach(updatedUserApi);
        updatedUserApi
            .userId(UPDATED_USER_ID)
            .userLogin(UPDATED_USER_LOGIN)
            .date(UPDATED_DATE);
        UserApiDTO userApiDTO = userApiMapper.toDto(updatedUserApi);

        restUserApiMockMvc.perform(put("/api/user-apis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userApiDTO)))
            .andExpect(status().isOk());

        // Validate the UserApi in the database
        List<UserApi> userApiList = userApiRepository.findAll();
        assertThat(userApiList).hasSize(databaseSizeBeforeUpdate);
        UserApi testUserApi = userApiList.get(userApiList.size() - 1);
        assertThat(testUserApi.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserApi.getUserLogin()).isEqualTo(UPDATED_USER_LOGIN);
        assertThat(testUserApi.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserApi() throws Exception {
        int databaseSizeBeforeUpdate = userApiRepository.findAll().size();

        // Create the UserApi
        UserApiDTO userApiDTO = userApiMapper.toDto(userApi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserApiMockMvc.perform(put("/api/user-apis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userApiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserApi in the database
        List<UserApi> userApiList = userApiRepository.findAll();
        assertThat(userApiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserApi() throws Exception {
        // Initialize the database
        userApiRepository.saveAndFlush(userApi);

        int databaseSizeBeforeDelete = userApiRepository.findAll().size();

        // Delete the userApi
        restUserApiMockMvc.perform(delete("/api/user-apis/{id}", userApi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserApi> userApiList = userApiRepository.findAll();
        assertThat(userApiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserApi.class);
        UserApi userApi1 = new UserApi();
        userApi1.setId(1L);
        UserApi userApi2 = new UserApi();
        userApi2.setId(userApi1.getId());
        assertThat(userApi1).isEqualTo(userApi2);
        userApi2.setId(2L);
        assertThat(userApi1).isNotEqualTo(userApi2);
        userApi1.setId(null);
        assertThat(userApi1).isNotEqualTo(userApi2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserApiDTO.class);
        UserApiDTO userApiDTO1 = new UserApiDTO();
        userApiDTO1.setId(1L);
        UserApiDTO userApiDTO2 = new UserApiDTO();
        assertThat(userApiDTO1).isNotEqualTo(userApiDTO2);
        userApiDTO2.setId(userApiDTO1.getId());
        assertThat(userApiDTO1).isEqualTo(userApiDTO2);
        userApiDTO2.setId(2L);
        assertThat(userApiDTO1).isNotEqualTo(userApiDTO2);
        userApiDTO1.setId(null);
        assertThat(userApiDTO1).isNotEqualTo(userApiDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userApiMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userApiMapper.fromId(null)).isNull();
    }
}
