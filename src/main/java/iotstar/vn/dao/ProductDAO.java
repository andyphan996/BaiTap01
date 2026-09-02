package iotstar.vn.dao;

import iotstar.vn.config.JpaConfig;
import iotstar.vn.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProductDAO {

    public List<Product> findAll() {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT p FROM Product p ORDER BY p.id DESC", Product.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Product findById(Long id) {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public void save(Product product) {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void update(Product product) {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(product);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            Product p = em.find(Product.class, id);
            if (p != null) em.remove(p);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    public List<Product> findLatest(int limit) {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            TypedQuery<Product> q = em.createQuery(
                    "SELECT p FROM Product p ORDER BY p.createdAt DESC, p.id DESC", Product.class);
            q.setMaxResults(limit);
            return q.getResultList();
        } finally {
            em.close();
        }
    }


    public List<Product> findPage(int pageNumber, int pageSize) {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            TypedQuery<Product> q = em.createQuery(
                    "SELECT p FROM Product p ORDER BY p.createdAt DESC, p.id DESC", Product.class);
            int firstResult = (pageNumber - 1) * pageSize;
            q.setFirstResult(firstResult);
            q.setMaxResults(pageSize);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public long countAll() {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(p) FROM Product p", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}
