package utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final EntityManagerFactory emf;
    static {
        try {
            emf = Persistence.createEntityManagerFactory("dbrest");
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Error al iniciar JPA: " + e);
        }
    }
    public static EntityManager createEntityManager() {
        return emf.createEntityManager();
    }
    public static void shutdown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}