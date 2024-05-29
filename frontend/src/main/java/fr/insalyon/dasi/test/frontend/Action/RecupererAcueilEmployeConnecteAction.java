/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.frontend.Action;

import fr.insalyon.dasi.predictif.models.Employe;
import fr.insalyon.dasi.predictif.services.Services;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author nhajjhassa
 */
public class RecupererAcueilEmployeConnecteAction extends Action {

    public RecupererAcueilEmployeConnecteAction(Services service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        
        Long employeId = Long.parseLong(request.getSession().getAttribute("personneId").toString());
                
        Employe employe = Services.getEmployeById(employeId);
        
        if (employe == null) return;

        request.setAttribute("employe", employe);
    }
}
