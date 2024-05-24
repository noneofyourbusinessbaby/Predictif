package fr.insalyon.dasi.test.frontend.Action;

import javax.servlet.http.HttpServletRequest;

import fr.insalyon.dasi.predictif.models.Client;
import fr.insalyon.dasi.predictif.services.Services;
import java.util.Date;
public class InscrireUtilisateurAction extends Action {

    public InscrireUtilisateurAction(Services service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        char genre = request.getParameter("genre").charAt(0);
        Date dateDeNaissance = new Date(request.getParameter("dateNaissance"));
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("password");
        String adressePostale = request.getParameter("adressePostale");
        String telephone = request.getParameter("telephone");


        if (prenom != null && nom != null && genre != 0 && dateDeNaissance != null && email != null && motDePasse != null && adressePostale != null && telephone != null) {

            Client client = new Client(adressePostale, dateDeNaissance, nom, prenom, email, genre, motDePasse, telephone);

            Services.inscrireClient(client);

            request.setAttribute("client", client);

        }

    }
}