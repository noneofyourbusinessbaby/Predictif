package fr.insalyon.dasi.test.frontend.Action;

import javax.servlet.http.HttpServletRequest;

import fr.insalyon.dasi.predictif.models.Client;
import fr.insalyon.dasi.predictif.services.Services;

public class RecupererProfilClient extends Action{

    public RecupererProfilClient(Services service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        
        Long id = Long.parseLong(request.getParameter("personneId"));

        Client client = Services.getClientById(id);
        
        request.setAttribute("personne", client);        
        
    }

}
