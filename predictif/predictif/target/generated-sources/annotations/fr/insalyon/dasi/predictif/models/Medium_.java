package fr.insalyon.dasi.predictif.models;

import fr.insalyon.dasi.predictif.models.Consultation;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2024-05-29T18:14:19")
@StaticMetamodel(Medium.class)
public abstract class Medium_ { 

    public static volatile SingularAttribute<Medium, String> presentation;
    public static volatile SingularAttribute<Medium, Character> genre;
    public static volatile SingularAttribute<Medium, Long> id;
    public static volatile ListAttribute<Medium, Consultation> consultations;
    public static volatile SingularAttribute<Medium, String> denomination;

}