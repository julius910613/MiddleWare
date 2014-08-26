package org.jboss.as.quickstarts.kitchensink.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(contract.class)
public abstract class contract_ {

	public static volatile SingularAttribute<contract, Long> id;
	public static volatile SingularAttribute<contract, String> contractDate;
	public static volatile SingularAttribute<contract, Taxi> taxi;
	public static volatile SingularAttribute<contract, Customer> customer;

}

