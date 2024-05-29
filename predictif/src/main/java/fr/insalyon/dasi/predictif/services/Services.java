/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.services;

import com.google.maps.model.LatLng;
import fr.insalyon.dasi.predictif.exception.MediumException;
import static fr.insalyon.dasi.predictif.exception.MediumException.AUCUN_RESULTAT_MEDIUM;
import fr.insalyon.dasi.predictif.exception.ConsultationException;
import static fr.insalyon.dasi.predictif.exception.ConsultationException.AUCUN_RESULTAT_CONSULTATION;
import fr.insalyon.dasi.predictif.exception.PersonneException;
import static fr.insalyon.dasi.predictif.exception.PersonneException.AUCUN_RESULTAT_PERSONNE;
import static fr.insalyon.dasi.predictif.exception.PersonneException.IDENTIFIANTS_INVALIDES;
import fr.insalyon.dasi.predictif.models.Astrologue;
import fr.insalyon.dasi.predictif.models.Cartomancien;
import fr.insalyon.dasi.predictif.models.Client;
import fr.insalyon.dasi.predictif.models.Consultation;
import fr.insalyon.dasi.predictif.models.Employe;
import fr.insalyon.dasi.predictif.models.Medium;
import fr.insalyon.dasi.predictif.models.Personne;
import fr.insalyon.dasi.predictif.models.ProfilAstral;
import fr.insalyon.dasi.predictif.models.Spirite;
import fr.insalyon.dasi.predictif.persistence.ClientDao;
import fr.insalyon.dasi.predictif.persistence.ConsultationDao;
import fr.insalyon.dasi.predictif.persistence.EmployeDao;
import fr.insalyon.dasi.predictif.persistence.JpaUtil;
import fr.insalyon.dasi.predictif.persistence.MediumDao;
import fr.insalyon.dasi.predictif.persistence.PersonneDao;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author lfaustmann
 */
public class Services {

    /**
    * Inscription d'un client + création du profil astral + appel API GEO pour récupérer les coordonnées
    * + envoi de mail de confirmation d'inscription
    * @param client le client à inscrire
    * @return le client inscrit
     * */
    public static Client inscrireClient(Client client)
    {
        try{    
            PersonneDao personneDao = new PersonneDao();
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            ProfilAstral profilAstral = creerProfilAstral(client);
            client.setProfilAstral(profilAstral);
            
            LatLng coordsClient = GeoNetApi.getLatLng(client.getAdressePostale());
            if(coordsClient == null)
            {
                throw new NullPointerException("L'adresse postale est incorrecte");
            }
            client.setLatitude(coordsClient.lat);
            client.setLongitude(coordsClient.lng);
            
            personneDao.create(client);

            JpaUtil.validerTransaction();
            Message.envoyerMail("contact@predict.if", client.getEmail(), "Bienvenue chez PREDICT'IF", "Bonjour " + client.getPrenom() + ", nous vous confirmons votre inscription au service PREDICT'IF. Rendez-vous vite sur notre site pour consulter votre profil astrologique et profiter des dons incroyables de nos mediums");
            System.out.println("Trace : succès inscription du client : " + client);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            Message.envoyerMail("contact@predict.if", client.getEmail(), "Echec de l'inscription chez PREDICT'IF", "Bonjour " + client.getPrenom() + ", votre inscription au service Predict'IF a malencontreusement échoué... Merci de recommencer ultérieurement.");
            System.out.println("Trace : échec inscription du client");
            client = null;
        }
        finally {
            JpaUtil.fermerContextePersistance();        
        }
        return client;
    }


    /**
     * Connnexion d'une personne
     * @param email email de la personne
     * @param motDePasse mot de passe de la personne
     * @throws PersonneException si les identifiants sont invalides
     * @return la personne connectée (Ici un client ou un employé)
     * */
    public static Personne connexionPersonne(String email, String motDePasse)
    {
        Personne personneConnecte = null;        
        try{  
            PersonneDao personneDao = new PersonneDao();
            JpaUtil.creerContextePersistance();            
            personneConnecte =  personneDao.findByEmail(email);
            
            if(personneConnecte == null || !personneConnecte.getMotDePasse().equals(motDePasse))
            {
                throw new PersonneException(IDENTIFIANTS_INVALIDES);
            }
            
            System.out.println("Trace : succès connexion de la personne : " + personneConnecte);
        }
        catch(PersonneException ex){
            ex.printStackTrace();
            System.out.println("Trace : Identifiants invalides"); 
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Trace : échec connexion de la personne");                     
        }
        finally {
        JpaUtil.fermerContextePersistance();
        }
        return personneConnecte;
    }
    
    public static void initData(){
        List<String> spritesSupports = new ArrayList();
        spritesSupports.add("Boule de cristal");
        spritesSupports.add("Oreilles de lapin");
        spritesSupports.add("Marc de café");
        Spirite spirite = new Spirite(spritesSupports, "Josette", 'F', "Josette la rossette");
        
        Cartomancien cartomancien = new Cartomancien("Irma", 'F', "Je vais te manger l'oreille");
        
        Astrologue astrologue = new Astrologue("ESA", 2002, "Thomas Pesquet", 'H', "Pour toi je décroche les étoiles");
        
        creerUnMedium(spirite);
        creerUnMedium(cartomancien);
        creerUnMedium(astrologue);
        
        // Création des employés
        Employe employe1 = new Employe("Martin", "Camille", "martion.camille@gmail.com", 'F', "123456789", "0783382317", Boolean.TRUE);
        Employe employe2 = new Employe("Conte", "Justina", "justin.conte@gmail.com", 'F', "123456789", "0769696969", Boolean.TRUE);
        Employe employe3 = new Employe("Peaumo", "Thea", "theo.peaumo@gmail.com", 'F', "123456789", "0769696969", Boolean.TRUE);
        Employe employe4 = new Employe("Just", "1", "just.un@bitcoin.com", 'H', "ViveOm", "0669696969", Boolean.TRUE);
        
        creerUnEmploye(employe1);
        creerUnEmploye(employe2);
        creerUnEmploye(employe3);
        creerUnEmploye(employe4);
    }

    /**
     * Création d'une consultation en regardant quel employé est disponible pour jouer le médium
     * et avec le critère du genre en fonction du médium choisi par le client
     * puis mets l'employé en indisponible et crée la consultation et notifie l'employé par SMS
     * @param client le client de la consultation
     * @param medium le medium de la consultation
     * @return la consultation créée ou null si aucun employé n'est disponible
     * */
    public static Consultation creerConsultation(Client client, Medium medium){
        Consultation consultation = null;
        try{    
            ConsultationDao consultationDao = new ConsultationDao();
            EmployeDao employeDao = new EmployeDao();
            PersonneDao personneDao = new PersonneDao();
            MediumDao mediumDao = new MediumDao();
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            Employe employe = employeDao.findEmployeDisponible(medium.getGenre());
            if(employe == null)
            {
                throw new PersonneException(AUCUN_RESULTAT_PERSONNE);
            }
            
            consultation = new Consultation(employe, client, medium);
            consultationDao.create(consultation);

            medium.addConsultation(consultation);
            employe.setEstDisponible(Boolean.FALSE);
            employe.addConsultation(consultation);
            client.addConsultation(consultation);
            personneDao.update(client);
            mediumDao.update(medium);
            personneDao.update(employe);
                
            JpaUtil.validerTransaction();
            Message.envoyerNotification(employe.getTelephone(),
                    "Bonjour " + consultation.getEmploye().getPrenom()
                            + ". Consultation requise pour "
                            + (client.getGenre() == 'H' ? "M" : "Mme")
                            + " " + client.getPrenom()
                            + " " + client.getNom() + ". Médium à incarner : "
                            + (medium.getGenre() == 'H' ? "M" : "Mme") + " "
                            + medium.getDenomination());
            System.out.println("Trace : succès création consultation : " + consultation);
        }
        catch(PersonneException ex){
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            System.out.println("Trace : Aucun employé n'est disponible"); 
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            System.out.println("Trace : échec création consultation");            
        }
        finally {
            JpaUtil.fermerContextePersistance();        
        }
        return consultation;
    }

    /**
     * Récupère la consultation en attente pour un employé (elle est récupéré soit si elle est en attente ou en cours cela
     * permet de reprendre une consultation en cours si l'employé a été déconnecté par exemple)
     * @param employe l'employé pour lequel on veut récupérer la consultation en attente
     * @return la consultation en attente ou null si aucune consultation n'est en attente
     * */
    public static Consultation getConsultationEnCours(Employe employe) {
        Consultation consultationEnAttente = null;
        try{  
            ConsultationDao consultationDao = new ConsultationDao();
            JpaUtil.creerContextePersistance(); 
            
            consultationEnAttente = consultationDao.getConsultationEnCours(employe);
            if(consultationEnAttente == null)
            {
                throw new ConsultationException(AUCUN_RESULTAT_CONSULTATION);
            }
            System.out.println("Trace : succès de la récupération de la consultation en cours : " + consultationEnAttente);
        }
        catch(ConsultationException ex){
            ex.printStackTrace();
            System.out.println("Trace : Aucune consultation en attente"); 
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Trace : échec de la récupération de la consultation en cours");            
        }
        finally {  
            JpaUtil.fermerContextePersistance(); 
        }
        return consultationEnAttente;
    }

    /**
     * Change le statut de la consultation en cours pour la marquer comme commencée
     * et envoie un SMS au client pour le prévenir
     * @param idConsultation identifiant de la consultation à commencer
     * */
    public static void commencerConsultation(Long idConsultation){
        try{    
            ConsultationDao consultationDao = new ConsultationDao();
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();            
            
            Consultation consultation = consultationDao.findById(idConsultation); 
            if(consultation == null)
            {
                throw new ConsultationException(AUCUN_RESULTAT_CONSULTATION);
            }
            
            consultation.setDateDebut(new Date());
            consultationDao.update(consultation);  
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM 'à' HH'h'mm");

            JpaUtil.validerTransaction();
            Message.envoyerNotification(consultation.getClient().getTelephone(),
                    "Bonjour " + consultation.getClient().getPrenom()
                            + ". J'ai bien reçu votre demande de consultation du "
                            + dateFormat.format(consultation.getDateDebut())
                            + ". Vous pouvez dès à présent me contacter au "
                            + consultation.getEmploye().getTelephone()
                            + ". A tout de suite ! Médiumiquement vôtre, "
                            + (consultation.getMedium().getGenre() == 'H' ? "M" : "Mme")
                            + " " + consultation.getMedium().getDenomination());
            System.out.println("Trace : succès du début de la consultation : "+ consultation);
        }
        catch(ConsultationException ex){
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            System.out.println("Trace : Consultation inconnue"); 
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            System.out.println("Trace : échec du début de la consultation");            
        }
        finally {
            JpaUtil.fermerContextePersistance();        
        }
    }

    /**
     * Valide une consultation en ajoutant un commentaire et en changeant le statut de l'employé à disponible
     * @param idConsultation identifiant de la consultation à valider
     * @param commentaire commentaire à ajouter à la consultation
     * @return true si la consultation a été validée, false sinon (si la consultation n'existe pas ou autre problème en DB)
     */
    public static  Boolean validerConsultation(Long idConsultation, String commentaire){
        Boolean aEteChange = false;
        try{    
            ConsultationDao consultationDao = new ConsultationDao();
            PersonneDao personneDao = new PersonneDao();
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();            
            
            Consultation consultation = consultationDao.findById(idConsultation); 
            if(consultation == null)
            {
                throw new ConsultationException(AUCUN_RESULTAT_CONSULTATION);
            }
            consultation.setDateFin(new Date());
            consultation.setCommentaire(commentaire);
            consultationDao.update(consultation);            
            
            Employe employe = consultation.getEmploye();
            employe.setEstDisponible(Boolean.TRUE);
            personneDao.update(employe);
            
            aEteChange = true;

            JpaUtil.validerTransaction();
            System.out.println("Trace : succès validation de la consultation : "+consultation);
        }
        catch(ConsultationException ex){
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            System.out.println("Trace : Consultation inconnue"); 
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            System.out.println("Trace : échec validation de la consultation");            
        }
        finally {
            JpaUtil.fermerContextePersistance();        
        }
        return aEteChange;
    }

    /**
     * Récupère les mediums existants
     * @return la liste des mediums existants
     * */
    public static List<Medium> getMediums(){
        List<Medium> mediums = null;        
        try{  
            MediumDao mediumDao = new MediumDao();
            JpaUtil.creerContextePersistance();            
            mediums =  mediumDao.getMediums();
            if(mediums == null)
            {
                throw new MediumException(AUCUN_RESULTAT_MEDIUM);
            }
            System.out.println("Trace : succès récupération des médiums");
        }
        catch(MediumException ex){
            ex.printStackTrace();
            System.out.println("Trace : Aucun medium existant"); 
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Trace : échec récupération des médiums");                     
        }
        finally {
        JpaUtil.fermerContextePersistance();
        }
        return mediums;
    }   

    /**
     * Récupère un client par son id
     * @param idClient l'id du client à récupérer
     * @return le client récupéré ou null si aucun client n'existe avec cet id
     * */
    public static Client getClientById(Long idClient) {
        Client client = null;        
        try{  
            PersonneDao personneDao = new PersonneDao();
            JpaUtil.creerContextePersistance();            
            Personne personne =  personneDao.findById(idClient);
            
            if (personne instanceof Client)
            {
                client = (Client) personne;
            }
            else {
                throw new PersonneException(AUCUN_RESULTAT_PERSONNE);
            }
            
            System.out.println("Trace : succès récupération du client : " + client);
        }
        catch(PersonneException ex){
            ex.printStackTrace();
            System.out.println("Trace : Client inconnu"); 
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Trace : échec récupération du client " );                     
        }
        finally {
        JpaUtil.fermerContextePersistance();
        }
        return client;
    }

    /**
     * Récupère un employé par son id
     * @param idEmploye l'id de l'employé à récupérer
     * @return l'employé récupéré ou null si aucun employé n'existe avec cet id
     */
    public static Employe getEmployeById(Long idEmploye) {
        Employe employe = null;        
        try{  
            PersonneDao personneDao = new PersonneDao();
            JpaUtil.creerContextePersistance();            
            Personne personne =  personneDao.findById(idEmploye);
            
            if (personne instanceof Employe)
            {
                employe = (Employe) personne;
            }
            else {
                throw new PersonneException(AUCUN_RESULTAT_PERSONNE);
            }
            
            System.out.println("Trace : succès récupération de l'employe : " + employe);
        }
        catch(PersonneException ex){
            ex.printStackTrace();
            System.out.println("Trace : Employé inconnu"); 
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Trace : échec récupération de l'employe ");                     
        }
        finally {
        JpaUtil.fermerContextePersistance();
        }
        return employe;
    }

    /**
     * Récupère un medium par son id
     * @param idMedium l'id du medium à récupérer
     * @return le medium récupéré ou null si aucun medium n'existe avec cet id
     */
    public static Medium getMediumById(Long idMedium) {
        Medium medium = null;        
        try{  
            MediumDao mediumDao = new MediumDao();
            JpaUtil.creerContextePersistance();            
            medium =  mediumDao.findById(idMedium);
            if(medium == null)
            {
                throw new MediumException(AUCUN_RESULTAT_MEDIUM);
            }
            
            System.out.println("Trace : succès récupération du medium : " + medium);
        }
        catch(MediumException ex){
            ex.printStackTrace();
            System.out.println("Trace : Medium inconnu"); 
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Trace : échec récupération du medium ");                     
        }
        finally {
        JpaUtil.fermerContextePersistance();
        }
        return medium;
    }


    /**
     * Récupère les mediums les plus demandés
     * @param x le nombre de mediums à récupérer
     * @return la liste des mediums les plus demandés ou null si aucun medium n'existe
     */
    public static List<Medium> getMediumsTopX(Integer x) {
        List<Medium> mediums = null;        
        try{  
            MediumDao mediumDao = new MediumDao();
            JpaUtil.creerContextePersistance();            
            mediums =  mediumDao.findTopX(x);
            if(mediums == null)
            {
                throw new MediumException((AUCUN_RESULTAT_MEDIUM));
            }
            
            System.out.println("Trace : succès récupération du TopX des médiums : " + mediums);
        }
        catch(MediumException ex){
            ex.printStackTrace();
            System.out.println("Trace : Aucun medium existant"); 
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Trace : échec récupération du du TopX des médiums " );                     
        }
        finally {
        JpaUtil.fermerContextePersistance();
        }
        return mediums;
    }

    /**
     * Récupère les clients existants
     * @return la liste des clients existants
     */
    public static List<Client> getClients() {
        List<Client> clients = null;        
        try{  
            ClientDao clientDao = new ClientDao();
            JpaUtil.creerContextePersistance();            
            clients =  clientDao.getClients();
            
            if(clients == null)
            {
                throw new PersonneException(AUCUN_RESULTAT_PERSONNE);
            }
            
            System.out.println("Trace : succès récupération des clients");
        }
        catch(PersonneException ex){
            ex.printStackTrace();
            System.out.println("Trace : Aucun client existant"); 
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Trace : échec récupération des clients");                     
        }
        finally {
        JpaUtil.fermerContextePersistance();
        }
        return clients;
    }


    /**
     * Récupère les employes existants
     * @return la liste des employes existants
     */
    public static List<Employe> getEmployes() {
        List<Employe> employes = null;        
        try{  
            EmployeDao employeDao = new EmployeDao();
            JpaUtil.creerContextePersistance();            
            employes =  employeDao.getEmployes();
            
            if(employes == null)
            {
                throw new PersonneException(AUCUN_RESULTAT_PERSONNE);
            }
            
            System.out.println("Trace : succès récupération des employes");
        }
        catch(PersonneException ex){
            ex.printStackTrace();
            System.out.println("Trace : Aucun employé existant"); 
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Trace : échec récupération des employes");                     
        }
        finally {
        JpaUtil.fermerContextePersistance();
        }
        return employes;
    }

    /**
     * Génération des prédictions pour un client auprès de l'api astroNet
     * @param client le client pour lequel on veut générer les prédictions
     * @param amour l'indice d'amour du client
     * @param sante l'indice de santé du client
     * @param travail l'indice de travail du client
     * @return la liste des prédictions générées
     */
    public static List<String> getPredictions(Client client, int amour, int sante, int travail) {
        List<String> predictions = null;
        try{  
            AstroNetApi astroApi = new AstroNetApi();
            predictions = astroApi.getPredictions(client.getProfilAstral().getCouleurBonheur(), client.getProfilAstral().getAnimalTotem(), amour, sante, travail);
            System.out.println("Trace : succès récupération des prédictions ");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Trace : échec récupération des prédictions ");                     
        }
        return predictions;
    }

    /**
     * Ajoute un medium à la base de données
     * @param medium le medium à ajouter
     * @return le medium ajouté
     * */
    private static Medium creerUnMedium(Medium medium){
        try{    
            MediumDao mediumDao = new MediumDao();
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            mediumDao.create(medium);

            JpaUtil.validerTransaction();
            System.out.println("Trace : succès création medium");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            System.out.println("Trace : échec création medium");            
        }
        finally {
            JpaUtil.fermerContextePersistance();        
        }
        return medium;
    }

    /**
     * Ajoute un employe à la base de données
     * @param employe l'employe à ajouter
     * @return l'employe ajouté
     * */
    private static Employe creerUnEmploye(Employe employe){
        try{    
            PersonneDao personneDao = new PersonneDao();
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            personneDao.create(employe);

            JpaUtil.validerTransaction();
            System.out.println("Trace : succès création employe");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            System.out.println("Trace : échec création employe");            
        }
        finally {
            JpaUtil.fermerContextePersistance();        
        }
        return employe;
    }

    /**
     * Création du profil astral d'un client
     * @param client le client pour lequel on veut créer le profil astral
     * @return le profil astral créé
     * */
    private static ProfilAstral creerProfilAstral(Client client) throws IOException{
        ProfilAstral profilcreate;
        try{    
            AstroNetApi astroApi = new AstroNetApi();
            List<String> profil = astroApi.getProfil(client.getPrenom(), client.getDateDeNaissance());
            String signeZodiaque = profil.get(0);
            String signeChinois = profil.get(1);
            String couleur = profil.get(2);
            String animal = profil.get(3);
            
            profilcreate =  new ProfilAstral(signeZodiaque, signeChinois, couleur, animal);
            System.out.println("Trace : succès création profil astrale");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            profilcreate = null;
        }
        return profilcreate;
    }
}
