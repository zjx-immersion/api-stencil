package com.api.foobar.rest.rest;

import com.api.foobar.Application;
import com.api.foobar.ApplicationWebXml;
import com.api.foobar.domain.Foo;
import com.api.foobar.repository.FooMemeryRepository;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the FooResource REST controller.
 *
 * @see FooResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
//@ContextConfiguration(classes = ApplicationWebXml.class, loader = AnnotationConfigContextLoader.class)
//@TestPropertySource(locations="classpath:test.properties")
@WebAppConfiguration
@IntegrationTest
public class FooResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";

    @Inject
    private FooMemeryRepository fooRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFooMockMvc;

    private Foo foo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FooResource fooResource = new FooResource();
        ReflectionTestUtils.setField(fooResource, "fooRepository", fooRepository);
        this.restFooMockMvc = MockMvcBuilders.standaloneSetup(fooResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        foo = new Foo();
//        foo.setId(new Long(12345678));
        foo.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createFoo() throws Exception {
        int databaseSizeBeforeCreate = fooRepository.findAll().size();

        // Create the Foo

        restFooMockMvc.perform(post("/api/foos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(foo)))
                .andExpect(status().isCreated());

        // Validate the Foo in the database
        List<Foo> foos = fooRepository.findAll();
        assertThat(foos).hasSize(databaseSizeBeforeCreate + 1);
        Foo testFoo = foos.get(foos.size() - 1);
        assertThat(testFoo.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllFoos() throws Exception {
        // Initialize the database
        Foo foo = new Foo(new Long(123), DEFAULT_VALUE);
        fooRepository.saveAndFlush(foo);

        // Get all the foos
        restFooMockMvc.perform(get("/api/foos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[*].attributes.id").value(hasItem(foo.getId().intValue())))
                .andExpect(jsonPath("$.data[*].attributes.value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getFoo() throws Exception {
        // Initialize the database
        Foo foo = new Foo(new Long(123), DEFAULT_VALUE);
        fooRepository.saveAndFlush(foo);

        // Get the foo
        restFooMockMvc.perform(get("/api/foos/{id}", foo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(foo.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFoo() throws Exception {
        // Get the foo
        restFooMockMvc.perform(get("/api/foos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

//    @Test
//    @Transactional
//    public void updateFoo() throws Exception {
//        // Initialize the database
//        Foo foo = new Foo(new Long(123), DEFAULT_VALUE);
//        fooRepository.saveAndFlush(foo);
//        int databaseSizeBeforeUpdate = fooRepository.findAll().size();
//
//        // Update the foo
//        Foo updatedFoo = new Foo();
//        updatedFoo.setId(foo.getId());
//        updatedFoo.setValue(UPDATED_VALUE);
//
//        restFooMockMvc.perform(put("/api/foos")
//                .contentType(TestUtil.APPLICATION_JSON_UTF8)
//                .content(TestUtil.convertObjectToJsonBytes(updatedFoo)))
//                .andExpect(status().isOk());
//
//        // Validate the Foo in the database
//        List<Foo> foos = fooRepository.findAll();
//        assertThat(foos).hasSize(databaseSizeBeforeUpdate);
//        Foo testFoo = foos.get(foos.size() - 1);
//        assertThat(testFoo.getValue()).isEqualTo(UPDATED_VALUE);
//    }

    @Test
    @Transactional
    public void deleteFoo() throws Exception {
        // Initialize the database
        Foo foo = new Foo(new Long(123), DEFAULT_VALUE);
        fooRepository.saveAndFlush(foo);
        int databaseSizeBeforeDelete = fooRepository.findAll().size();

        // Get the foo
        restFooMockMvc.perform(delete("/api/foos/{id}", foo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Foo> foos = fooRepository.findAll();
        assertThat(foos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
