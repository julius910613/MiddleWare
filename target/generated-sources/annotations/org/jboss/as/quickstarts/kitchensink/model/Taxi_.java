package org.jboss.as.quickstarts.kitchensink.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Taxi.class)
public abstract class Taxi_ {

	public static volatile SingularAttribute<Taxi, Long> id;
	public static volatile SingularAttribute<Taxi, Integer> prize;
	public static volatile SingularAttribute<Taxi, String> carregistrationid;
	public static volatile SingularAttribute<Taxi, Integer> quanity;
	public static volatile SingularAttribute<Taxi, String> taxiname;

}

