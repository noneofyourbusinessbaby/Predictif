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
import java.util.Date;
import java.util.List;

/**
 *
 * @author nhajjhassa
 */
public class ServiceSerialisation {
    
    private static String calculeStatut(Consultation consultation){
        
        if (consultation.getDateDebut() == null){
            return "Pas commence";
        }
        else {
            if (consultation.getDateFin() == null){
                return "En cours";
            }
            else {
                return "Validée";
            }
        }
    }
    
    public static JsonObject toJsonObjectConsultationClient(Consultation consultation){
        

        
        JsonObject container = new JsonObject();
        
        container.addProperty("denominationMedium", consultation.getMedium().getDenomination());
        container.addProperty("mediumType", consultation.getMedium().getClass().getSimpleName());
        container.addProperty("personneId", consultation.getClient().getId());
        
        Date dateDebut = consultation.getDateDebut();
        
        container.addProperty("dateDebut", dateDebut == null ? null : dateDebut.toString());
        
        container.addProperty("statut", ServiceSerialisation.calculeStatut(consultation));
        container.addProperty("commentaire", consultation.getCommentaire());
        container.addProperty("consultationId", consultation.getId());
        
        return container;
    }
    
    public static JsonObject toJsonObjectConsultationEmploye(Consultation consultation) {
 
        
        JsonObject container = new JsonObject();
        container.addProperty("denominationMedium", consultation.getMedium().getDenomination());
        container.addProperty("mediumType", consultation.getMedium().getClass().getSimpleName());
        container.addProperty("prenomClient", consultation.getClient().getPrenom());
        container.addProperty("nomClient", consultation.getClient().getNom());
        container.addProperty("personneId", consultation.getClient().getId());
        
        Date dateDebut = consultation.getDateDebut();
        
        container.addProperty("dateDebut", dateDebut == null ? null : dateDebut.toString());
        
        container.addProperty("statut", ServiceSerialisation.calculeStatut(consultation));
        container.addProperty("consultationId", consultation.getId());

                
        return container;
    }
    
    public static JsonObject toJsonObjectMedium(Medium medium){
        
        JsonObject jsonMedium = new JsonObject();
        
        jsonMedium.addProperty("mediumId", medium.getId());

        jsonMedium.addProperty("denominationMedium", medium.getDenomination());
        jsonMedium.addProperty("mediumType", medium.getClass().getSimpleName().toLowerCase());

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

    public static JsonArray toJsonObjectListeConsultation(Client client) {
        
        JsonArray listeConsultationJson = new JsonArray();
        
        List<Consultation> listeConsultation = client.getConsultations();
        
        if (listeConsultation != null) {
            
            for (Consultation consultation : listeConsultation) {
                listeConsultationJson.add(toJsonObjectConsultationClient(consultation));
            }
        }
       return listeConsultationJson;
    }
    
    public static JsonArray toJsonObjectListeConsultation(Employe employe) {
        
        JsonArray listeConsultationJson = new JsonArray();
        
        List<Consultation> listeConsultation = employe.getConsultations();
        
        for (Consultation consultation : listeConsultation) {
            listeConsultationJson.add(toJsonObjectConsultationEmploye(consultation));
        }
       
        return listeConsultationJson;
    }

    public static JsonObject toJsonObjectAideConsultation(List<String> predictions) {
        
        JsonObject predisctionsJson = new JsonObject();
        
        predisctionsJson.addProperty("Amour", predictions.get(0));
        predisctionsJson.addProperty("Santé", predictions.get(1));
        predisctionsJson.addProperty("Travail",  predictions.get(2));

        return predisctionsJson;
    }
}
