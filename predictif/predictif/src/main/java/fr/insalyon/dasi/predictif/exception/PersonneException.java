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

public class PersonneException extends Exception{    
    public final static String IDENTIFIANTS_INVALIDES = "Les identifiants sont invalides";
    public final static String PROBLEME_TECHNIQUE = "Un problème technique est survenu";
    public final static String AUCUN_RESULTAT_PERSONNE = "Aucun resultat disponible";
    
    public PersonneException(String s){  
        super(s);  
    }    
}