/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.terminal;

import fr.insalyon.dasi.predictif.models.Client;
import fr.insalyon.dasi.predictif.models.Employe;
import fr.insalyon.dasi.predictif.models.Medium;
import fr.insalyon.dasi.predictif.persistence.JpaUtil;
import fr.insalyon.dasi.predictif.services.Services;

import java.util.List;

/**
 *
 * @author lfaustmann
 */
public class TerminalEmploye {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JpaUtil.creerFabriquePersistance();
        JpaUtil.creerContextePersistance();
        
        Services.initData();
        
        Employe employe = new  Employe("Just", "1", "just.un@bitcoin.com", 'H', "ViveOm", "0669696969", Boolean.TRUE);
        Services.connexionPersonne(employe.getEmail(), employe.getMotDePasse());

        List<Medium> mediums =  Services.getMediumsTopX(5);
        for (Medium medium : mediums){
            System.out.println(medium);
        }

        List<Client> clients = Services.getClients();
        for (Client client : clients){
            System.out.println(client);
        }

        List<Employe> employes = Services.getEmployes();
        for (Employe employe1 : employes){
            System.out.println(employe1);
        }
        
        JpaUtil.fermerFabriquePersistance();
    }
    
}
