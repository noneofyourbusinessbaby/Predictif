/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.predictif.persistence;

import fr.insalyon.dasi.predictif.models.Employe;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author lfaustmann
 */
public class EmployeDao {
    
    public Employe findEmployeDisponible(char genre) {
        Employe employe = null;
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            TypedQuery<Employe> query = em.createQuery("SELECT e FROM Employe e WHERE e.estDisponible = :estDisponible AND e.genre = :genre ORDER BY SIZE(e.consultations) asc", Employe.class);
            query.setParameter("estDisponible", Boolean.TRUE);
            query.setParameter("genre", genre);
            employe = query.setMaxResults(1).getSingleResult();
        }catch (NoResultException ex) {
            System.out.println("Trace : Aucun employé disponible trouvé"); 
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return employe;
    }
    
    public List<Employe> getEmployes() {
        List<Employe> employes = null;
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            TypedQuery<Employe> query = em.createQuery("SELECT e FROM Employe e", Employe.class);
            employes = query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return employes;
    }
}
