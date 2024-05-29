package fr.insalyon.dasi.test.frontend.Action;

import javax.servlet.http.HttpServletRequest;

import fr.insalyon.dasi.predictif.models.Client;
import fr.insalyon.dasi.predictif.services.Services;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class InscrireUtilisateurAction extends Action {

    public InscrireUtilisateurAction(Services service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        char genre = request.getParameter("genre").charAt(0); // todo changer
        
        // localhost:8080/frontend/ActionServelet?todo=inscrire&prenom=Kiki&nom=hprt&email=kiki@kik2i&password=123456789&genre=M&dateNaissance=07/10/96 4:5 PM, PDT&telephone=01236547&adressePostale=69100
        
        Date dateDeNaissance;
        
        try {
            dateDeNaissance = DateFormat.getInstance().parse(request.getParameter("dateNaissance"));
        }
        catch(ParseException ex){
            return;
        }
        
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