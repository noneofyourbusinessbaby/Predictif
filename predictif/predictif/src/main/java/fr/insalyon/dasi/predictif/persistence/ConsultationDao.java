/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.persistence;

import fr.insalyon.dasi.predictif.models.Consultation;
import fr.insalyon.dasi.predictif.models.Employe;
import fr.insalyon.dasi.predictif.models.Personne;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author lfaustmann
 */
public class ConsultationDao {
    
    public void create(Consultation consultation) {
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            em.persist(consultation);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Consultation findById(Long idConsultation) {
        Consultation consultation = null;
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            TypedQuery<Consultation> query = em.createQuery("SELECT c FROM Consultation c WHERE c.id = :id", Consultation.class);
            query.setParameter("id", idConsultation);
            consultation = query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return consultation;
    }    
    
    public void update(Consultation consultation) {
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            em.merge(consultation);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Consultation getConsultationEnCours(Employe employe) {
        Consultation consultation = null;
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            TypedQuery<Consultation> query = em.createQuery("SELECT c FROM Consultation c WHERE c.employe = :employe AND c.dateFin IS NULL", Consultation.class);
            query.setParameter("employe", employe);
            consultation = query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return consultation;
    }
}
