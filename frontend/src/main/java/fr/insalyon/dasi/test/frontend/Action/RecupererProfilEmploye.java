package fr.insalyon.dasi.test.frontend.Action;

import javax.servlet.http.HttpServletRequest;

import fr.insalyon.dasi.predictif.models.Employe;
import fr.insalyon.dasi.predictif.services.Services;

public class RecupererProfilEmploye extends Action{

    public RecupererProfilEmploye(Services service) {
        super(service);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void execute(HttpServletRequest request) {
       
        Long id = Long.parseLong(request.getParameter("personneId"));

        Employe employe = Services.getEmployeById(id);

        request.setAttribute("personne", employe);
        
    }

}
