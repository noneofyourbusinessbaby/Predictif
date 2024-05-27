package fr.insalyon.dasi.test.frontend.Serialisation;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.predictif.models.Client;
import fr.insalyon.dasi.predictif.models.Consultation;
import fr.insalyon.dasi.predictif.models.Employe;
import fr.insalyon.dasi.predictif.models.Personne;
import fr.insalyon.dasi.predictif.models.ProfilAstral;
import java.util.List;

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
            List<Consultation> listeConsulations = null;
            userJson.addProperty("prenom" , personne.getPrenom());
            userJson.addProperty("nom" , personne.getNom());
            userJson.addProperty("mail" , personne.getEmail());
            userJson.addProperty("telephone", personne.getTelephone());
            userJson.addProperty("genre", personne.getGenre());

            if(personne instanceof Client){            
                
                Client client = (Client) personne;
                
                userJson.addProperty("adressePostale", client.getAdressePostale());
                
                userJson.addProperty("dateNaissance", client.getDateDeNaissance().toString());
                
                ProfilAstral profilAstral = client.getProfilAstral();
                                
                JsonObject jsonProfilAstral = new JsonObject();
                
                jsonProfilAstral.addProperty("signeChinois", profilAstral.getSigneChinois());
                jsonProfilAstral.addProperty("animalTotem", profilAstral.getAnimalTotem());
                jsonProfilAstral.addProperty("signeZodiaque", profilAstral.getSigneZodiaque());
                jsonProfilAstral.addProperty("couleurBonheur", profilAstral.getCouleurBonheur());
                
                userJson.add("profilAstral", jsonProfilAstral);
                
                listeConsulations = client.getConsultations();

                
            }
            else if (personne instanceof Employe){
                
                Employe employe = (Employe) personne;
                userJson.addProperty("estDisponible", employe.getEstDisponible());
                listeConsulations = employe.getConsultations();
            }
                        
            container.addProperty("type", personne.getClass().getSimpleName());
            
            
 /**           JsonArray listeConsultationsJson = new JsonArray();

            
            for (Consultation consultation : listeConsulations){
                
               JsonObject jsonObject = new JsonObject();
               
               jsonObject.addProperty("dateDebut", consultation.getDateDebut().toString());
               jsonObject.addProperty("mediumConsultation", consultation.getMedium().getDenomination());
               
               if (consultation.getDateFin()!= null && consultation.getCommentaire() != null){
                    jsonObject.addProperty("statut", "Finie");
               }
               else {
                   jsonObject.addProperty("statut", "En cours");
               }
               
               listeConsultationsJson.add(jsonObject);
            }
            
            userJson.add("consultations", listeConsultationsJson);        */
            
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
