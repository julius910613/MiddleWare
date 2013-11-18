/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.kitchensink.data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import org.jboss.as.quickstarts.kitchensink.model.Taxi;

@ApplicationScoped
public class TaxiRepository {

    @Inject
    private EntityManager em;

    public Taxi findById(Long id) {
        return em.find(Taxi.class, id);
    }

    public Taxi findByCarID(String id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Taxi> criteria = cb.createQuery(Taxi.class);
        Root<Taxi> taxi = criteria.from(Taxi.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(Member_.email), email));
        criteria.select(taxi).where(cb.equal(taxi.get("carregistrationid"), id));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<Taxi> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Taxi> criteria = cb.createQuery(Taxi.class);
        Root<Taxi> taxi = criteria.from(Taxi.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(taxi).orderBy(cb.asc(taxi.get("taxiname")));
        return em.createQuery(criteria).getResultList();
    }
}