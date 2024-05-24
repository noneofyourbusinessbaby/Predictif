/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.persistence;

import fr.insalyon.dasi.predictif.models.Personne;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author lfaustmann
 */
public class PersonneDao {

    public Personne findByEmail(String email) {
        Personne personne = null;
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            TypedQuery<Personne> query = em.createQuery("SELECT p FROM Personne p WHERE p.email = :email", Personne.class);
            query.setParameter("email", email);
            personne = query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return personne;
    }

    public Personne findById(Long idPersonne) {
        Personne personne = null;
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            TypedQuery<Personne> query = em.createQuery("SELECT p FROM Personne p WHERE p.id = :id", Personne.class);
            query.setParameter("id", idPersonne);
            personne = query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return personne;
    }

    public void create(Personne personne) {
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            em.persist(personne);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update(Personne personne) {
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            em.merge(personne);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}