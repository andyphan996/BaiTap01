package iotstar.vn.dao;

import iotstar.vn.config.JpaConfig;
import iotstar.vn.entity.Category;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CategoryDAO {

    public List<Category> findAll() {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Category c ORDER BY c.id", Category.class)
                      .getResultList();
        } finally {
            em.close();
        }
    }

    public Category findById(Long id) {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    public void save(Category category) {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(category);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void update(Category category) {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(category);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            Category c = em.find(Category.class, id);
            if (c != null) em.remove(c);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}