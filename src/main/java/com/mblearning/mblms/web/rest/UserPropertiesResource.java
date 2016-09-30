package com.mblearning.mblms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mblearning.mblms.domain.UserProperties;

import com.mblearning.mblms.repository.UserPropertiesRepository;
import com.mblearning.mblms.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserProperties.
 */
@RestController
@RequestMapping("/api")
public class UserPropertiesResource {

    private final Logger log = LoggerFactory.getLogger(UserPropertiesResource.class);
        
    @Inject
    private UserPropertiesRepository userPropertiesRepository;

    /**
     * POST  /user-properties : Create a new userProperties.
     *
     * @param userProperties the userProperties to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userProperties, or with status 400 (Bad Request) if the userProperties has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/user-properties",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserProperties> createUserProperties(@RequestBody UserProperties userProperties) throws URISyntaxException {
        log.debug("REST request to save UserProperties : {}", userProperties);
        if (userProperties.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userProperties", "idexists", "A new userProperties cannot already have an ID")).body(null);
        }
        UserProperties result = userPropertiesRepository.save(userProperties);
        return ResponseEntity.created(new URI("/api/user-properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userProperties", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-properties : Updates an existing userProperties.
     *
     * @param userProperties the userProperties to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userProperties,
     * or with status 400 (Bad Request) if the userProperties is not valid,
     * or with status 500 (Internal Server Error) if the userProperties couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/user-properties",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserProperties> updateUserProperties(@RequestBody UserProperties userProperties) throws URISyntaxException {
        log.debug("REST request to update UserProperties : {}", userProperties);
        if (userProperties.getId() == null) {
            return createUserProperties(userProperties);
        }
        UserProperties result = userPropertiesRepository.save(userProperties);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userProperties", userProperties.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-properties : get all the userProperties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userProperties in body
     */
    @RequestMapping(value = "/user-properties",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UserProperties> getAllUserProperties() {
        log.debug("REST request to get all UserProperties");
        List<UserProperties> userProperties = userPropertiesRepository.findAll();
        return userProperties;
    }

    /**
     * GET  /user-properties/:id : get the "id" userProperties.
     *
     * @param id the id of the userProperties to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userProperties, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/user-properties/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserProperties> getUserProperties(@PathVariable Long id) {
        log.debug("REST request to get UserProperties : {}", id);
        UserProperties userProperties = userPropertiesRepository.findOne(id);
        return Optional.ofNullable(userProperties)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /user-properties/:id : delete the "id" userProperties.
     *
     * @param id the id of the userProperties to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/user-properties/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUserProperties(@PathVariable Long id) {
        log.debug("REST request to delete UserProperties : {}", id);
        userPropertiesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userProperties", id.toString())).build();
    }

}
