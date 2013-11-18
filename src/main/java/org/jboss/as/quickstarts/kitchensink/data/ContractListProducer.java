package org.jboss.as.quickstarts.kitchensink.data;

import org.jboss.as.quickstarts.kitchensink.model.contract;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 12/11/13
 * Time: 13:46
 * To change this template use File | Settings | File Templates.
 */

@RequestScoped
public class ContractListProducer {
    @Inject
    private ContractRepository taxiRepository;

    private List<contract> contracts;

    // @Named provides access the return value via the EL variable name "members" in the UI (e.g.
    // Facelets or JSP view)
    @Produces
    @Named
    public List<contract> getContracts() {
        return contracts;
    }

    public void onContractListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final contract contracts) {
        retrieveAllMembersOrderedByCustomer();
    }

    @PostConstruct
    public void retrieveAllMembersOrderedByCustomer() {
        contracts = taxiRepository.findAllOrderedByCustomerName();
    }


}
