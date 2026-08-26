package iotstar.vn.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.TimeZone;
public class JpaConfig {
	static {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
    }
    private static final EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("jpa-hibernate-postgresql");

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}