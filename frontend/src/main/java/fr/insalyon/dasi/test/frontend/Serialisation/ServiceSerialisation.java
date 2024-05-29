/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.test.frontend.Serialisation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.predictif.models.Astrologue;
import fr.insalyon.dasi.predictif.models.Client;
import fr.insalyon.dasi.predictif.models.Consultation;
import fr.insalyon.dasi.predictif.models.Employe;
import fr.insalyon.dasi.predictif.models.Medium;
import fr.insalyon.dasi.predictif.models.Personne;
import fr.insalyon.dasi.predictif.models.ProfilAstral;
import fr.insalyon.dasi.predictif.models.Spirite;
import java.util.List;

/**
 *
 * @author nhajjhassa
 */
public class ServiceSerialisation {
    
    public static JsonObject toJsonObjectConsultationClient(Consultation consultation){
        
        String stringStatut= "En cours";

        if (consultation.getDateFin() != null && consultation.getCommentaire() != null) {
            stringStatut = "Validée";
        }
        
        JsonObject container = new JsonObject();
        container.addProperty("denominationMedium", consultation.getMedium().getDenomination());
        container.addProperty("dateDebut", consultation.getDateDebut().toString());
        container.addProperty("Statut", stringStatut);
        
        return container;
    }
    
    public static JsonObject toJsonObjectConsultationEmploye(Consultation consultation) {
 
        String stringStatut= "En cours";

        if (consultation.getDateFin() != null && consultation.getCommentaire() != null) {
            stringStatut = "Validée";
        }
        
        JsonObject container = new JsonObject();
        container.addProperty("denominationMedium", consultation.getMedium().getDenomination());
        container.addProperty("prenomClient", consultation.getClient().getPrenom());
        container.addProperty("nomClient", consultation.getClient().getNom());
        container.addProperty("dateDebut", consultation.getDateDebut().toString());
        container.addProperty("Statut", stringStatut);
        
        return container;
    }
    
    public static JsonObject toJsonObjectMedium(Medium medium){
        
        JsonObject jsonMedium = new JsonObject();
        
        jsonMedium.addProperty("mediumID", medium.getId());

        jsonMedium.addProperty("denomination", medium.getDenomination());

        jsonMedium.addProperty("genre", medium.getGenre());

        jsonMedium.addProperty("presentation", medium.getPresentation());

        if (medium instanceof Astrologue){
            jsonMedium.addProperty("formation", ((Astrologue) medium).getFormation());
            jsonMedium.addProperty("promotion", ((Astrologue) medium).getPromotion());
        }
        else if (medium instanceof Spirite){
            JsonArray jsonSupport = new JsonArray();
            for (String support : ((Spirite) medium).getSupports()){
                jsonSupport.add(support);
            }
            jsonMedium.add("support", jsonSupport);
        }
        else{
            // ON FAIT RIEN 
        }
        return jsonMedium;
    }
    
    public static JsonObject toJsonObjectPersonne(Personne personne){
        
        JsonObject container = new JsonObject();
        JsonObject userJson = new JsonObject();
        
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
            
        return userJson;   
    }
    
    public static JsonObject toJsonProfilAstral(Client client){
        
        ProfilAstral profilAstral = client.getProfilAstral();

        JsonObject jsonProfilAstral = new JsonObject();

        jsonProfilAstral.addProperty("signeChinois", profilAstral.getSigneChinois());
        jsonProfilAstral.addProperty("animalTotem", profilAstral.getAnimalTotem());
        jsonProfilAstral.addProperty("signeZodiaque", profilAstral.getSigneZodiaque());
        jsonProfilAstral.addProperty("couleurBonheur", profilAstral.getCouleurBonheur());

        return jsonProfilAstral;
    }

    static JsonArray toJsonObjectListeConsultation(Client client) {
        
        JsonArray listeConsultationJson = new JsonArray();
        
        List<Consultation> listeConsultation = client.getConsultations();
        
        if (listeConsultation != null) {
            
            for (Consultation consultation : listeConsultation) {
                listeConsultationJson.add(toJsonObjectConsultationClient(consultation));
            }
        }
       return listeConsultationJson;
    }
    
    static JsonArray toJsonObjectListeConsultation(Employe employe) {
        
        JsonArray listeConsultationJson = new JsonArray();
        
        List<Consultation> listeConsultation = employe.getConsultations();
        
        if (listeConsultation != null) {
            
            for (Consultation consultation : listeConsultation) {
                listeConsultationJson.add(toJsonObjectConsultationEmploye(consultation));
            }
        }
       return listeConsultationJson;
    }

    static JsonElement toJsonObjectAideConsultation(List<String> predictions) {
        
        JsonArray listePredisctionsJson = new JsonArray();
        
        for (String predisction : predictions){
            listePredisctionsJson.add(predisction);
        }
        return listePredisctionsJson;
    }
}
