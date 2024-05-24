package fr.insalyon.dasi.test.frontend.Serialisation;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.predictif.models.Client;
import fr.insalyon.dasi.predictif.models.Employe;
import fr.insalyon.dasi.predictif.models.Personne;

/**
    * Serializes the complete user profile and sends it as a JSON response.
    * 
    */
public class ProfilUtilisateurCompletSerialisation extends Serialisation{

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) {
        
        Personne personne = (Personne)request.getAttribute("personne");
                    
        JsonObject container = new JsonObject();
        
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        
        JsonObject userJson = new JsonObject();
        

        if (personne != null) {
            userJson.addProperty("prenom" , personne.getPrenom());
            userJson.addProperty("nom" , personne.getNom());
            userJson.addProperty("mail" , personne.getEmail());
            userJson.addProperty("telephone", personne.getTelephone());
            userJson.addProperty("genre", personne.getGenre());

            if(personne instanceof Client){            
                
                Client client = (Client) personne;
                userJson.addProperty("adressePostale", client.getAdressePostale());
                userJson.addProperty("dateNaissance", client.getDateDeNaissance().toString());
                
            }
            else if (personne instanceof Employe){
                
                Employe employe = (Employe) personne;
                userJson.addProperty("estDisponible", employe.getEstDisponible());
            }
                        
            container.addProperty("type", personne.getClass().getSimpleName());
        }

        container.add("personne", userJson);
        
        try (PrintWriter out = response.getWriter()) {
            
            out.println(gson.toJson(container));
            out.close()  ;

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
