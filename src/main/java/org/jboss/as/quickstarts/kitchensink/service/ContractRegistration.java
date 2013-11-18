package org.jboss.as.quickstarts.kitchensink.service;

import org.jboss.as.quickstarts.kitchensink.model.contract;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 12/11/13
 * Time: 14:14
 * To change this template use File | Settings | File Templates.
 */

@Stateless
public class ContractRegistration {

    @Inject
    private Logger log;
    @Inject
    private EntityManager em;
    @Inject
    private Event<contract> memberEventSrc;

    public void register(contract contract) throws Exception {
        log.info("Registering " + contract.getCustomer());
       // em.persist(contract);
        if(!em.contains(contract.getCustomer())){
           em.merge(contract.getCustomer());
        }
        if(!em.contains(contract.getTaxi())){
            em.merge(contract.getTaxi()) ;
        }
        em.persist(em.contains(contract)? contract:em.merge(contract));
        memberEventSrc.fire(contract);
    }
    public void delete(contract deleteContract) throws Exception{

        log.info("Delecting" + deleteContract.getId());
        if(!em.contains(deleteContract.getCustomer())){
            em.merge(deleteContract.getCustomer());
        }
        if(!em.contains(deleteContract.getTaxi())){
            em.merge(deleteContract.getTaxi()) ;
        }
        em.remove(em.contains(deleteContract)? deleteContract:em.merge(deleteContract));
        memberEventSrc.fire(deleteContract);
    }
}


