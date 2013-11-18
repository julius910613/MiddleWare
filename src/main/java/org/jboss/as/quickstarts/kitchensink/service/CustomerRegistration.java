package org.jboss.as.quickstarts.kitchensink.service;

import org.jboss.as.quickstarts.kitchensink.model.Customer;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 05/11/13
 * Time: 20:42
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class CustomerRegistration {
    @Inject
    private Logger log;
    @Inject
    private EntityManager em;
    @Inject
    private Event<Customer> memberEventSrc;

    public void register(Customer customer) throws Exception {
        log.info("Registering " + customer.getName());
        em.persist(customer);
        memberEventSrc.fire(customer);
    }

}
