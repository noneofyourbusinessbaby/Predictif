package fr.insalyon.dasi.test.frontend.Action;

import javax.servlet.http.HttpServletRequest;

import fr.insalyon.dasi.predictif.models.Client;
import fr.insalyon.dasi.predictif.models.Employe;
import fr.insalyon.dasi.predictif.models.Personne;
import fr.insalyon.dasi.predictif.services.Services;

public class RecupererProfilConnecteAction extends Action{

    public RecupererProfilConnecteAction(Services service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        
        Long personneId = Long.parseLong(request.getSession().getAttribute("personneId").toString());
        
        Personne personne = Services.getClientById(personneId);
                
        if (personne == null)
        {
            personne = Services.getEmployeById(personneId);
        }
        
        request.setAttribute("personne", personne);                
    }
}
