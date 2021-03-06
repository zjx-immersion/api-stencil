package com.api.foobar.rest.rest;

import com.api.foobar.domain.Foo;
import com.api.foobar.repository.FooMemeryRepository;
import com.api.foobar.rest.rest.dto.FoosDTO;
import com.api.foobar.rest.rest.dto.ResponseResourceDTO;
import com.api.foobar.rest.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Foo.
 */
@RestController
@RequestMapping("/api")
public class FooResource {

    private final Logger log = LoggerFactory.getLogger(FooResource.class);

    @Inject
    private FooMemeryRepository fooRepository;

    /**
     * POST  /foos : Create a new foo.
     *
     * @param foo the foo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new foo, or with status 400 (Bad Request) if the foo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/foos",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Foo> createFoo(@RequestBody Foo foo) throws URISyntaxException {
        log.debug("REST request to save Foo : {}", foo);
        if (foo.getId() != null) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert("foo", "idexists", "A new foo cannot already have an ID"))
                    .body(null);
        }
        Foo result = fooRepository.save(foo);
        return ResponseEntity.created(new URI("/api/foos/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("foo", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /foos : Updates an existing foo.
     *
     * @param foo the foo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated foo,
     * or with status 400 (Bad Request) if the foo is not valid,
     * or with status 500 (Internal Server Error) if the foo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/foos",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Foo> updateFoo(@RequestBody Foo foo) throws URISyntaxException {
        log.debug("REST request to update Foo : {}", foo);
        if (foo.getId() == null) {
            return createFoo(foo);
        }
        Foo result = fooRepository.save(foo);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("foo", foo.getId().toString()))
                .body(result);
    }

    /**
     * GET  /foos : get all the foos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of foos in body
     */
    @RequestMapping(value = "/foos",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ResponseResourceDTO> getAllFoos() {
        log.debug("REST request to get all Foos");
        List<Foo> foos = fooRepository.findAll();
        ResponseResourceDTO foosResourceDTO = new ResponseResourceDTO(new FoosDTO(foos));
        return new ResponseEntity<ResponseResourceDTO>(foosResourceDTO, HttpStatus.OK);
    }

    /**
     * GET  /foos/:id : get the "id" foo.
     *
     * @param id the id of the foo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the foo, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/foos/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Foo> getFoo(@PathVariable Long id) {
        log.debug("REST request to get Foo : {}", id);
        Foo foo = fooRepository.findOne(id);
        return Optional.ofNullable(foo)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /foos/:id : delete the "id" foo.
     *
     * @param id the id of the foo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/foos/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFoo(@PathVariable Long id) {
        log.debug("REST request to delete Foo : {}", id);
        fooRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("foo", id.toString())).build();
    }

}
