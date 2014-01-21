package helper;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * 
 * Clase utilizada para configurar la persistencia.
 * 
 * @author Nathalia Ochoa
 * 
 * @since 1.0
 * @version 1.0 Jan 19, 2014
 */
public class PersistenceHelper {

	public static EntityManager getNewInstance() {

		EntityManager em = getEntityManagerFactory("reports")
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
