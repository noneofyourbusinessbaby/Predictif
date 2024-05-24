/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.models;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author lfaustmann
 */
@Entity
public class Astrologue extends Medium implements Serializable {
    
    private String formation;
    private Integer promotion;

    public Astrologue(String formation, Integer promotion, String denomination, char genre, String presentation) {
        super(denomination, genre, presentation);
        this.formation = formation;
        this.promotion = promotion;
    }

    public Astrologue() {
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public Integer getPromotion() {
        return promotion;
    }

    public void setPromotion(Integer promotion) {
        this.promotion = promotion;
    }
    
    @Override
    public String toString() {
        return "Astrologue{" +super.toString()+ "formation=" + formation + ", promotion=" + promotion + '}';
    }

}
