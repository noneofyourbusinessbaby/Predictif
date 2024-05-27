/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.frontend.Action;

import fr.insalyon.dasi.predictif.models.Client;
import fr.insalyon.dasi.predictif.models.Consultation;
import fr.insalyon.dasi.predictif.models.Medium;
import fr.insalyon.dasi.predictif.services.Services;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author nhajjhassa
 */
public class CreerConsultationAction extends Action {

    public CreerConsultationAction(Services service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        
        Long personneId = Long.parseLong(request.getSession().getAttribute("personneId").toString());
        
        System.out.println(personneId);
        Client client = Services.getClientById(personneId);
        
        Medium medium = Services.getMediumById(Long.parseLong(request.getParameter("mediumId")));
        
        if (medium != null && client != null) {
            Consultation consultation = Services.creerConsultation(client, medium);
            request.setAttribute("consultation", consultation);
        }
    }  
}
