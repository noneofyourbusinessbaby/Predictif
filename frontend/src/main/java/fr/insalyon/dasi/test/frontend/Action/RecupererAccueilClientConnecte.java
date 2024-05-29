/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.frontend.Action;

import fr.insalyon.dasi.predictif.models.Client;
import fr.insalyon.dasi.predictif.services.Services;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author nhajjhassa
 */
public class RecupererAccueilClientConnecte extends Action {

    public RecupererAccueilClientConnecte(Services service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
       
        Long personneId = Long.parseLong(request.getSession().getAttribute("personneId").toString());
        
        Client client = Services.getClientById(personneId);
        
        request.setAttribute("client", client);
    }
}
