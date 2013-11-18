package org.jboss.as.quickstarts.kitchensink.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.jboss.as.quickstarts.kitchensink.data.TaxiRepository;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 07/11/13
 * Time: 19:30
 * To change this template use File | Settings | File Templates.
 */


@SuppressWarnings("serial")
@Entity(name = "Taxi")
@XmlRootElement
@Table(name = "taxi")
public class Taxi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String taxiname;

    @NotNull
    private int quanity;

    @NotNull
    @Size(min = 10, max = 12)
    private String carregistrationid;

    @NotNull
    @Range(min = 3, max = 10)
    private int prize;


//   private Collection contracts;

//   @OneToMany(mappedBy = "taxi", cascade = CascadeType.ALL)
//    public Collection getContracts() {
//        return contracts;
//    }

//    public void setContracts(Collection contracts) {
//        this.contracts = contracts;
//    }

    public Taxi(){

    }

    public Taxi(long id){
        TaxiRepository taxiRepository = new TaxiRepository();
          Taxi t = taxiRepository.findById(id);
        this.id = t.getId();
        this.quanity = t.getQuanity();
        this.taxiname = t.getTaxiname();
        this.carregistrationid = t.getCarregistrationid();
        this.prize = t.getPrize();
    }

    public void setTaxiname(String taxiname) {
        this.taxiname = taxiname;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }

    public void setCarregistrationid(String carregistrationid) {
        this.carregistrationid = carregistrationid;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public Long getId() {
        return id;
    }

    public String getTaxiname() {
        return taxiname;
    }

    public int getQuanity() {
        return quanity;
    }

    public String getCarregistrationid() {
        return carregistrationid;
    }

    public int getPrize() {
        return prize;
    }
    @Override
    public boolean equals(Object o){
        Taxi customer = (Taxi)o;
        if(customer.getCarregistrationid().equals(this.getCarregistrationid())){
            return true;
        }
        else{
            return false;
        }
    }
}
