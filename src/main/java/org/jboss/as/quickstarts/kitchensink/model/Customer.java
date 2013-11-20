package org.jboss.as.quickstarts.kitchensink.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
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
    private String personID;
    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @Size(min = 10, max = 12)
    @Digits(fraction = 0, integer = 12)
    @Column(name = "phone_number")
    private String phoneNumber;
  //  private Set<contract> contract = new HashSet<contract>();


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String licenseID) {
        personID = licenseID;
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
        if(customer.getPersonID().equals(this.getPersonID())){
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
