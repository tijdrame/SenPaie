package com.emard.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.emard.domain.Avantage;
import com.emard.domain.*; // for static metamodels
import com.emard.repository.AvantageRepository;
import com.emard.service.dto.AvantageCriteria;


/**
 * Service for executing complex queries for Avantage entities in the database.
 * The main input is a {@link AvantageCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Avantage} or a {@link Page} of {@link Avantage} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AvantageQueryService extends QueryService<Avantage> {

    private final Logger log = LoggerFactory.getLogger(AvantageQueryService.class);


    private final AvantageRepository avantageRepository;

    public AvantageQueryService(AvantageRepository avantageRepository) {
        this.avantageRepository = avantageRepository;
    }

    /**
     * Return a {@link List} of {@link Avantage} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Avantage> findByCriteria(AvantageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Avantage> specification = createSpecification(criteria);
        return avantageRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Avantage} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Avantage> findByCriteria(AvantageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Avantage> specification = createSpecification(criteria);
        return avantageRepository.findAll(specification, page);
    }

    /**
     * Function to convert AvantageCriteria to a {@link Specifications}
     */
    private Specifications<Avantage> createSpecification(AvantageCriteria criteria) {
        Specifications<Avantage> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Avantage_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Avantage_.libelle));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Avantage_.code));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), Avantage_.deleted));
            }
        }
        return specification;
    }

}
