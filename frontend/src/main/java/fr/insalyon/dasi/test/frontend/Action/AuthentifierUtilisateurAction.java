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
        // todo vérifier que les champs ne sont pas null

        Personne user = Services.connexionPersonne(email, password);

        request.setAttribute("personne", user);        
    }
}
