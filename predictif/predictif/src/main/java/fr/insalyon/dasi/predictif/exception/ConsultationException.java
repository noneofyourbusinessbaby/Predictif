/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.exception;

/**
 *
 * @author lfaustmann
 */
public class ConsultationException extends Exception{    
    public final static String AUCUN_RESULTAT_CONSULTATION = "Aucun resultat disponible";
    
    public ConsultationException(String s){  
        super(s);  
    }    
}
