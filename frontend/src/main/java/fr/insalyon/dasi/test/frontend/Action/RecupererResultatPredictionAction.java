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
public class RecupererResultatPredictionAction extends Action {

    public RecupererResultatPredictionAction(Services service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        
        //if (request.getSession().getAttribute("personneId") == null) return;
        
       // Long employeId = Long.parseLong(request.getSession().getAttribute("personneId").toString());
       
        Long employeId = Long.parseLong(request.getParameter("personneId"));
              
        Integer amour = Integer.parseInt(request.getParameter("amour"));
        Integer sante = Integer.parseInt(request.getParameter("sante"));
        Integer travail = Integer.parseInt(request.getParameter("travail"));
                    
        Client client = Services.getClientById(employeId);
        
        if (client == null) return;
        
        List<String> predictions = Services.getPredictions(client, amour, sante, travail);
        
        request.setAttribute("predictions", predictions);
    }
    
}
