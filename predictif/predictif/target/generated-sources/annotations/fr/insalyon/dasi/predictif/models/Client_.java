package fr.insalyon.dasi.predictif.models;

import fr.insalyon.dasi.predictif.models.Consultation;
import fr.insalyon.dasi.predictif.models.ProfilAstral;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2024-05-29T18:14:19")
@StaticMetamodel(Client.class)
public class Client_ extends Personne_ {

    public static volatile SingularAttribute<Client, Date> dateDeNaissance;
    public static volatile SingularAttribute<Client, Double> latitude;
    public static volatile SingularAttribute<Client, ProfilAstral> profilAstral;
    public static volatile SingularAttribute<Client, String> adressePostale;
    public static volatile ListAttribute<Client, Consultation> consultations;
    public static volatile SingularAttribute<Client, Double> longitude;

}