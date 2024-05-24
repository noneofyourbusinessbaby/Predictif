/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.terminal;

import fr.insalyon.dasi.predictif.persistence.JpaUtil;
import fr.insalyon.dasi.predictif.services.Services;

/**
 *
 * @author lfaustmann
 */
public class TerminalInit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JpaUtil.creerFabriquePersistance();
        JpaUtil.creerContextePersistance();

        Services.initData();

        JpaUtil.fermerFabriquePersistance();
    }
    
}
