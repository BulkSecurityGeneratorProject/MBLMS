package com.mblearning.mblms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mblearning.mblms.domain.Profiles;

import com.mblearning.mblms.repository.ProfilesRepository;
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
 * REST controller for managing Profiles.
 */
@RestController
@RequestMapping("/api")
public class ProfilesResource {

    private final Logger log = LoggerFactory.getLogger(ProfilesResource.class);
        
    @Inject
    private ProfilesRepository profilesRepository;

    /**
     * POST  /profiles : Create a new profiles.
     *
     * @param profiles the profiles to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profiles, or with status 400 (Bad Request) if the profiles has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/profiles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Profiles> createProfiles(@Valid @RequestBody Profiles profiles) throws URISyntaxException {
        log.debug("REST request to save Profiles : {}", profiles);
        if (profiles.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("profiles", "idexists", "A new profiles cannot already have an ID")).body(null);
        }
        Profiles result = profilesRepository.save(profiles);
        return ResponseEntity.created(new URI("/api/profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("profiles", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /profiles : Updates an existing profiles.
     *
     * @param profiles the profiles to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profiles,
     * or with status 400 (Bad Request) if the profiles is not valid,
     * or with status 500 (Internal Server Error) if the profiles couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/profiles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Profiles> updateProfiles(@Valid @RequestBody Profiles profiles) throws URISyntaxException {
        log.debug("REST request to update Profiles : {}", profiles);
        if (profiles.getId() == null) {
            return createProfiles(profiles);
        }
        Profiles result = profilesRepository.save(profiles);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("profiles", profiles.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profiles : get all the profiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of profiles in body
     */
    @RequestMapping(value = "/profiles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Profiles> getAllProfiles() {
        log.debug("REST request to get all Profiles");
        List<Profiles> profiles = profilesRepository.findAllWithEagerRelationships();
        return profiles;
    }

    /**
     * GET  /profiles/:id : get the "id" profiles.
     *
     * @param id the id of the profiles to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profiles, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/profiles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Profiles> getProfiles(@PathVariable Long id) {
        log.debug("REST request to get Profiles : {}", id);
        Profiles profiles = profilesRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(profiles)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /profiles/:id : delete the "id" profiles.
     *
     * @param id the id of the profiles to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/profiles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProfiles(@PathVariable Long id) {
        log.debug("REST request to delete Profiles : {}", id);
        profilesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("profiles", id.toString())).build();
    }

}
