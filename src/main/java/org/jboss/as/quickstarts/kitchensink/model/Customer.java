package org.jboss.as.quickstarts.kitchensink.model;

import org.jboss.as.quickstarts.kitchensink.data.CustomerRepository;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 06/11/13
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */

@SuppressWarnings("serial")
@Entity (name = "Customer")
@XmlRootElement

public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String name;
    @NotNull
    @Size(min = 1, max = 20)
    private String password;
    @NotNull
    @Size(min = 1, max = 10)
    private String driverLicenseID;
  //  private Set<contract> contract = new HashSet<contract>();



    public Customer() {

    }

    public Customer(long id) {
        CustomerRepository customerRepository = new CustomerRepository();
        Customer c = customerRepository.findById(id);
        this.id = c.getId();
        this.name = c.getName();
        this.password = c.getPassword();
        this.driverLicenseID = c.getDriverLicenseID();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
//    @OneToMany(mappedBy = "Customer", cascade = CascadeType.ALL)
//     public Set<contract> getContract() {
//        return contract;
//    }
//
//    public void setContract(Set<contract> contract) {
//        this.contract = contract;
//    }

    public String getDriverLicenseID() {
        return driverLicenseID;
    }

    public void setDriverLicenseID(String licenseID) {
        driverLicenseID = licenseID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public boolean equals(Object o){
             Customer customer = (Customer)o;
        if(customer.getDriverLicenseID().equals(this.getDriverLicenseID())){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String toString(){
        return this.getName();
    }
}
