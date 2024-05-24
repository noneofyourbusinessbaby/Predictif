/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 *
 * @author lfaustmann
 */
@Entity
public class Employe extends Personne{

    private Boolean estDisponible;
    @OneToMany(mappedBy = "employe", fetch = FetchType.EAGER)
    private List<Consultation> consultations;
    
    public Employe() {
    }

    public Employe(String nom, String prenom, String email, char genre, String motDePasse, String telephone, Boolean estDisponible) {
        super(nom, prenom, email, genre, motDePasse, telephone);
        this.estDisponible = estDisponible;
        this.consultations = new ArrayList<>();
    }

    public void addConsultation(Consultation consultation) {
        consultations.add(consultation);
    }

    public Boolean getEstDisponible() {
        return estDisponible;
    }

    public void setEstDisponible(Boolean estDisponible) {
        this.estDisponible = estDisponible;
    }    

    public List<Consultation> getConsultations() {
        return consultations;
    }
    
    @Override
    public String toString() {
        return "Employe{" + super.toString() + " estDisponible=" + estDisponible + ", consultations=" + consultations + '}';
    }
}
