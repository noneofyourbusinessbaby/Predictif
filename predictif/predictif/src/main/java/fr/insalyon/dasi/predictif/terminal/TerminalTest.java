/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.terminal;

import fr.insalyon.dasi.predictif.exception.PersonneException;
import fr.insalyon.dasi.predictif.models.Client;
import fr.insalyon.dasi.predictif.models.Consultation;
import fr.insalyon.dasi.predictif.models.Medium;
import fr.insalyon.dasi.predictif.persistence.JpaUtil;
import fr.insalyon.dasi.predictif.services.Services;
import static java.lang.System.in;
import java.util.Date;
import java.util.List;

/**
 *
 * @author lfaustmann
 */
public class TerminalTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws PersonneException {
        JpaUtil.creerFabriquePersistance();
        JpaUtil.creerContextePersistance();
        // TODO code application logic here
        Client client = new Client("69100", new Date(2002, 7, 17), "Niney", "Pierre", "pierre.niney@insa-lyon.fr", 'M', "123456789", "0783382317");
        client = Services.inscrireClient(client);
        System.out.println(client);
        
        
        Client personne = (Client) Services.connexionPersonne("pierre.niney@insa-lyon.fr", "123456789");
        System.out.println("Le client connecte est: "+personne);
        
        // Test init data
        Services.initData();
        
        List<Medium> mediums = Services.getMediums();
        for (Medium medium : mediums){
            System.out.println(medium);
        }
        // Demande consultation
        Consultation consultation = Services.creerConsultation(client, mediums.get(2));
        Services.getConsultationEnCours(consultation.getEmploye());
        Services.commencerConsultation(consultation.getId());
        Services.validerConsultation(consultation.getId(), "zebi");
        
        // GetMedium
        Medium medium = Services.getMediumById(consultation.getMedium().getId());
        System.out.println(medium);
        
        //GetTopX
        List<Medium> mediumsTop2 = Services.getMediumsTopX(2);
        System.out.println(mediumsTop2);
        
        // Predictions
        List<String> predictions = Services.getPredictions(client, 2, 1, 4);
        System.out.println(predictions);
               
        JpaUtil.fermerFabriquePersistance();
    }
    
}
