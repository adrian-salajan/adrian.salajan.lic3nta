package web.security;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import web.entity.Userrr;

@Repository
public class SecurityUtils {
	

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Userrr getUser(String username) {
		//return (Userrr) sessionFactory.getCurrentSession().createCriteria(Userrr.class).add(Restrictions.eq("username", username)).uniqueResult();
		CriteriaBuilder b = em.getCriteriaBuilder();
		CriteriaQuery<Userrr> criteria = em.getCriteriaBuilder().createQuery(Userrr.class);
		Root<Userrr> root = criteria.from(Userrr.class);
		criteria.select(root);
		criteria.where(b.equal(root.get("username"), username));
		TypedQuery<Userrr> query = em.createQuery(criteria);
		return query.getSingleResult();
	}

}
