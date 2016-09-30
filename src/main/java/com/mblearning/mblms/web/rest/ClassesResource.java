package com.mblearning.mblms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mblearning.mblms.domain.Classes;

import com.mblearning.mblms.repository.ClassesRepository;
import com.mblearning.mblms.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Classes.
 */
@RestController
@RequestMapping("/api")
public class ClassesResource {

    private final Logger log = LoggerFactory.getLogger(ClassesResource.class);
        
    @Inject
    private ClassesRepository classesRepository;

    /**
     * POST  /classes : Create a new classes.
     *
     * @param classes the classes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classes, or with status 400 (Bad Request) if the classes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/classes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Classes> createClasses(@Valid @RequestBody Classes classes) throws URISyntaxException {
        log.debug("REST request to save Classes : {}", classes);
        if (classes.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("classes", "idexists", "A new classes cannot already have an ID")).body(null);
        }
        Classes result = classesRepository.save(classes);
        return ResponseEntity.created(new URI("/api/classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("classes", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /classes : Updates an existing classes.
     *
     * @param classes the classes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classes,
     * or with status 400 (Bad Request) if the classes is not valid,
     * or with status 500 (Internal Server Error) if the classes couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/classes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Classes> updateClasses(@Valid @RequestBody Classes classes) throws URISyntaxException {
        log.debug("REST request to update Classes : {}", classes);
        if (classes.getId() == null) {
            return createClasses(classes);
        }
        Classes result = classesRepository.save(classes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("classes", classes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /classes : get all the classes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of classes in body
     */
    @RequestMapping(value = "/classes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Classes> getAllClasses() {
        log.debug("REST request to get all Classes");
        List<Classes> classes = classesRepository.findAllWithEagerRelationships();
        return classes;
    }

    /**
     * GET  /classes/:id : get the "id" classes.
     *
     * @param id the id of the classes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classes, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/classes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Classes> getClasses(@PathVariable Long id) {
        log.debug("REST request to get Classes : {}", id);
        Classes classes = classesRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(classes)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /classes/:id : delete the "id" classes.
     *
     * @param id the id of the classes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/classes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteClasses(@PathVariable Long id) {
        log.debug("REST request to delete Classes : {}", id);
        classesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("classes", id.toString())).build();
    }

}
