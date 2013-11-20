package org.jboss.as.quickstarts.kitchensink.data;

import org.jboss.as.quickstarts.kitchensink.model.Customer;
import org.jboss.as.quickstarts.kitchensink.model.Taxi;
import org.jboss.as.quickstarts.kitchensink.model.contract;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 12/11/13
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */

@ApplicationScoped
public class ContractRepository {

    @Inject
    private EntityManager em;

    public contract findById(Long id) {
        return em.find(contract.class, id);
    }



    public contract findByContractDate(String customerID, String dateOfContract) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<contract> criteria = cb.createQuery(contract.class);
        Root<contract> contract = criteria.from(contract.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(Member_.email), email));
        criteria.select(contract).where(cb.equal(contract.get("customer_id"), customerID), cb.equal(contract.get("contract_date"), dateOfContract));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<contract> findAllOrderedByCustomerName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<contract> criteria = cb.createQuery(contract.class);
        Root<contract> contract = criteria.from(contract.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(contract).orderBy(cb.asc(contract.get("customer")));
        return em.createQuery(criteria).getResultList();
    }
}
