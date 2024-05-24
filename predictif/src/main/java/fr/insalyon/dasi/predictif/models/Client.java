/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lfaustmann
 */
@Entity
public class Client extends Personne {
    
    private String adressePostale;
    @Temporal(value = TemporalType.DATE)
    private Date dateDeNaissance;
    @Embedded
    private ProfilAstral profilAstral;
    private Double longitude;
    private Double latitude;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Consultation> consultations;

    public Client() {
    }

    public Client(String adressePostale, Date dateDeNaissance, String nom, String prenom, String email, char genre, String motDePasse, String telephone) {
        super(nom, prenom, email, genre, motDePasse, telephone);
        this.adressePostale = adressePostale;
        this.dateDeNaissance = dateDeNaissance;
        this.consultations = new ArrayList<>();
    }

    public void addConsultation(Consultation consultation) {
        consultations.add(consultation);
    }
    
    public List<Consultation> getConsultations() {
        return consultations;
    }
    
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public ProfilAstral getProfilAstral() {
        return profilAstral;
    }

    public void setProfilAstral(ProfilAstral profilAstral) {
        this.profilAstral = profilAstral;
    }
    
    public String getAdressePostale() {
        return adressePostale;
    }

    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }
    
    @Override
    public String toString() {
        return "Client{" + "adressePostale=" + adressePostale + ", dateDeNaissance=" + dateDeNaissance + ", profilAstral=" + profilAstral + ", longitude=" + longitude + ", latitude=" + latitude + '}';
    }
}
