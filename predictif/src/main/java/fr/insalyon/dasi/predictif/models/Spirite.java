/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author lfaustmann
 */
@Entity
public class Spirite extends Medium implements Serializable {
    
    private List<String> supports;
    
    public Spirite(List<String> supports, String denomination, char genre, String presentation) {
        super(denomination, genre, presentation);
        this.supports = supports;
    }

    public Spirite() {
    }

    public List<String> getSupports() {
        return supports;
    }

    public void setSupports(List<String> supports) {
        this.supports = supports;
    }
    
    @Override
    public String toString() {
        return "Spirite{" +super.toString()+  "supports=" + supports + '}';
    }
}
