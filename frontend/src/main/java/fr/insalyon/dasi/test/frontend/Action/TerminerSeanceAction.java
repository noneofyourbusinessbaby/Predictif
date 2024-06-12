/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.frontend.Action;

import fr.insalyon.dasi.predictif.services.Services;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author nhajjhassa
 */
public class TerminerSeanceAction extends Action{

    public TerminerSeanceAction(Services service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        
        
        String commentaire = request.getParameter("commentaire");
        Long consultationId = Long.parseLong(request.getParameter("consultationId"));
        
        

   
        Boolean val = Services.validerConsultation(consultationId, commentaire);
        

    }
    
}
