/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.frontend.Action;

import fr.insalyon.dasi.predictif.models.Client;
import fr.insalyon.dasi.predictif.models.Consultation;
import fr.insalyon.dasi.predictif.models.Employe;
import fr.insalyon.dasi.predictif.services.Services;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author nhajjhassa
 */
public class RecupererFicheClientAction extends Action{

    public RecupererFicheClientAction(Services service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        
        if (request.getParameter("personneId") == null) return;
        
        Long personneId = Long.parseLong(request.getParameter("personneId"));
                
        Client client = Services.getClientById(personneId);
        
        if (client == null ) return;
        
        request.setAttribute("client",client);
      }
    
}
