/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.frontend.Action;

import fr.insalyon.dasi.predictif.models.Personne;
import fr.insalyon.dasi.predictif.services.Services;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author nhajjhassa
 */
public class AuthentifierUtilisateurAction extends Action{

    public AuthentifierUtilisateurAction(Services service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
                
        String password = request.getParameter("password");
        
        String email = request.getParameter("email");

        Personne user = Services.connexionPersonne(email, password);
        
        if (user == null){
            return;
        }
                
        request.setAttribute("personne", user);
            
        request.getSession(true).setAttribute("personneId", user.getId());
    }
}
