package repositories;

import jakarta.persistence.EntityManager;
import utils.JpaUtil;

import java.util.List;
import java.util.Optional;

public abstract class JpaRepository<T, ID> implements Repository<T, ID> {

    private final Class<T> clase;

    protected JpaRepository(Class<T> clase) {
        this.clase = clase;
    }
    @Override
    public void save(T entidad) {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entidad);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error al guardar: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    @Override
    public Optional<T> findById(ID id) {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            return Optional.ofNullable(em.find(clase, id));
        } finally {
            em.close();
        }
    }
    @Override
    public List<T> findAll() {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            String jpql = "FROM " + clase.getSimpleName();
            return em.createQuery(jpql, clase).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(T entidad) {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entidad);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error al actualizar: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    @Override
    public void delete(ID id) {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            T entidad = em.find(clase, id);
            if (entidad != null) {
                em.remove(entidad);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error al eliminar: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}
