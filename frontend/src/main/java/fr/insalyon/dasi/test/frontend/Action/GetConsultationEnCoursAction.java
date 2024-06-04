/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.frontend.Action;

import fr.insalyon.dasi.predictif.models.Consultation;
import fr.insalyon.dasi.predictif.models.Employe;
import fr.insalyon.dasi.predictif.services.Services;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author nhajjhassa
 */
public class GetConsultationEnCoursAction extends Action {

    public GetConsultationEnCoursAction(Services service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        
        Long personneId = Long.parseLong(request.getSession().getAttribute("personneId").toString());
        
        Employe employe = Services.getEmployeById(personneId);
                
        if (employe == null) return;
        
        Consultation consultationEncours = Services.getConsultationEnCours(employe);
        
            
        request.setAttribute("consultationEnCours", consultationEncours);      
        request.setAttribute("employe", employe);      
    }
}
