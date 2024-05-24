/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.terminal;

import fr.insalyon.dasi.predictif.models.Client;
import fr.insalyon.dasi.predictif.models.Consultation;
import fr.insalyon.dasi.predictif.models.Employe;
import fr.insalyon.dasi.predictif.models.Medium;
import fr.insalyon.dasi.predictif.services.Services;

import java.util.Date;
import java.util.List;

/**
 *
 * @author lfaustmann
 */
public class TerminalClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // Inscription
        // Montrer qu'un mail est envoyé
        Client client = new Client("123 Main St", new Date(2002,02,02), "DURIF", "Sylvain", "john.doe@example.com", 'H', "password", "1234567890");
        client = Services.inscrireClient(client);

        // Montrer que la connexion est rejeté en cas d'erreur
        Client clientTmp = (Client)Services.connexionPersonne(client.getEmail(), "pouetPouet");
        if (clientTmp != null) {
            client = clientTmp;
        }

        // Montrer la connexion passante
        clientTmp = (Client)Services.connexionPersonne(client.getEmail(), client.getMotDePasse());
        if (clientTmp != null) {
            client = clientTmp;
        }

        // Ensuite le client veux voir la liste des mediums pour choisir
        List<Medium> mediums = Services.getMediums();

        // Le client choisi consulte le détail d'un medium
        Medium medium = mediums.get(0);

        // Comme le medium lui plait il décide de créer une consultation avec ce medium
        Consultation consultation = Services.creerConsultation(client, medium);


        // WARNING: Un  autre client veut une consulatation avec le même medium pour montrer que les consultations sont bien indépendantes
        // et que comme on a un seul employé on ne peut pas faire deux consultations en même temps
        Client client2 = new Client("oh là on me dit oui Momo", new Date(1789,02,02), "HENNI", "Mohammed", "mohammed.henni@om.fr", 'H', "jeVaisToutCasser", "1234567890");
        client2 = Services.inscrireClient(client2);
        client2 = (Client)Services.connexionPersonne(client2.getEmail(), client2.getMotDePasse());
        Consultation consultation2 = Services.creerConsultation(client2, medium);


        // On passe du point de vue de l'employe
        Employe employe = consultation.getEmploye();
        employe = (Employe)Services.connexionPersonne(employe.getEmail(), employe.getMotDePasse());

        // On montre la consultation en attente qu'a l'employé
        Consultation consultationsEnAttente = Services.getConsultationEnCours(employe);
        Thread.sleep(2500);

        // L'employé consulte la fiche du client
        Client clientConsultation = consultationsEnAttente.getClient();
        Thread.sleep(1500);

        List<String> predictions = Services.getPredictions(client, 1,0,5);
        for (String prediction : predictions) {
            System.out.println(prediction);
        }

        // L'employé commence la consultation et donc averti le client qu'il est prêt
        Services.commencerConsultation(consultationsEnAttente.getId());

        //... on attend 3 sec pour simuler le temps de la consultation
        Thread.sleep(4000);

        // L'employé fini la consultation mets un message et fini la consultation
        Services.validerConsultation(consultationsEnAttente.getId(), "Encore un fou qui se prend pour dieu je le cite ''J'incarne le Christ cosmique'' ou ''j'ai vu des serpents danser au bout de mon lit'' ");
    }
}
