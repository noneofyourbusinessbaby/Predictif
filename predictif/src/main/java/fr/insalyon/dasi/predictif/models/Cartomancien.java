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
public class Cartomancien extends Medium implements Serializable{

    public Cartomancien(String denomination, char genre, String presentation) {
        super(denomination, genre, presentation);
    }

    public Cartomancien() {
    }
    
    @Override
    public String toString() {
        return "Cartomancien{" +super.toString()+ '}';
    }
}
