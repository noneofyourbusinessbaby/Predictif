package fr.insalyon.dasi.predictif.models;

import fr.insalyon.dasi.predictif.models.Consultation;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2024-05-29T18:14:19")
@StaticMetamodel(Employe.class)
public class Employe_ extends Personne_ {

    public static volatile SingularAttribute<Employe, Boolean> estDisponible;
    public static volatile ListAttribute<Employe, Consultation> consultations;

}