package web.security;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

import web.entity.Region;
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
	
	@Transactional
	public List<Userrr> getAll() {
		CriteriaBuilder b = em.getCriteriaBuilder();
		CriteriaQuery<Userrr> criteria = em.getCriteriaBuilder().createQuery(Userrr.class);
		Root<Userrr> root = criteria.from(Userrr.class);
		criteria.select(root);
		criteria.where(b.notEqual(root.get("rolee"), "ADMIN"));
		TypedQuery<Userrr> query = em.createQuery(criteria);
		return query.getResultList();
	}
	
	@Transactional
	public Set<Region> getRegions() {
		CriteriaBuilder b = em.getCriteriaBuilder();
		CriteriaQuery<Region> criteria = em.getCriteriaBuilder().createQuery(Region.class);
		Root<Region> root = criteria.from(Region.class);
		criteria.select(root);
		TypedQuery<Region> query = em.createQuery(criteria);
		return new TreeSet<Region>(query.getResultList());
	}

}
