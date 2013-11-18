package org.jboss.as.quickstarts.kitchensink.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 12/11/13
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */

@SuppressWarnings("serial")
@Entity(name = "contract")
@XmlRootElement

public class contract implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @NotNull
    @Column(name = "contract_date")
    private String contractDate;
    @ManyToOne
    @JoinColumn(name = "taxi_id", nullable = false)
    private Taxi taxi;

    public Taxi getTaxi() {
        return taxi;
    }

    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
//        if (!customer.getContract().contains(this)) {
//            customer.getContract().add(this);
//        }
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }
}
