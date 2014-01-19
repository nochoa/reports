package ${namePackage};

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ${nameClass} {

	public static EntityManager getNewInstance() {

		EntityManager em = getEntityManagerFactory("${persistenceUnit}")
				.createEntityManager();
		return em;
	}

	private static EntityManagerFactory getEntityManagerFactory(
			String persistenceUnit) {
		return Persistence.createEntityManagerFactory(persistenceUnit);
	}
	public static List<?> createQuery(String query) {
		EntityManager em = PersistenceHelper.getNewInstance();
		Query consulta = em.createQuery(query);
		return consulta.getResultList();
	}

}
