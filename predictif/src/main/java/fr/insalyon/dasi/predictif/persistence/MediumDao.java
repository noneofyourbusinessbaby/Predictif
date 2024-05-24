/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.persistence;

import fr.insalyon.dasi.predictif.models.Medium;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author lfaustmann
 */
public class MediumDao {
    
    public void create(Medium medium) {
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            em.persist(medium);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    } 

    public void update(Medium medium) {
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            em.merge(medium);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public List<Medium> getMediums() {
        List<Medium> mediums = null;
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            TypedQuery<Medium> query = em.createQuery("SELECT m FROM Medium m", Medium.class);
            mediums = query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mediums;
    }
    
    public Medium findById(Long idMedium) {
        Medium medium = null;
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            TypedQuery<Medium> query = em.createQuery("SELECT m FROM Medium m WHERE m.id = :id", Medium.class);
            query.setParameter("id", idMedium);
            medium = query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return medium;
    }
    
    public List<Medium> findTopX(Integer x) {
        List<Medium> mediums = null;
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            TypedQuery<Medium> query = em.createQuery("SELECT m FROM Medium m ORDER BY SIZE(m.consultations) DESC", Medium.class);
            mediums = query.setMaxResults(x).getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mediums;
    }
}
