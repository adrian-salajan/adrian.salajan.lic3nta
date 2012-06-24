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
	
	private static final String REGION_SALES = "select * from region where region.id not in " +
			" (select region_id from userrr_region inner join userrr on userrr_region.userrr_id = userrr.id " +
			"where userrr.rolee = 'SALES') "; 
	
	@Transactional
	public Userrr getUser(String username) {
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
	
	@Transactional
	public Set<Region> getRegionsForSales() {
		List<Region> list = em.createNativeQuery(REGION_SALES, Region.class).getResultList();
		return new TreeSet<Region>(list);
	}

	@Transactional
	public void update(Userrr user) {
		em.merge(user);
	}

	@Transactional
	public Region getRegion(String region) {
		CriteriaBuilder b = em.getCriteriaBuilder();
		CriteriaQuery<Region> criteria = em.getCriteriaBuilder().createQuery(Region.class);
		Root<Region> root = criteria.from(Region.class);
		criteria.select(root);
		criteria.where(b.equal(root.get("name"), region));
		TypedQuery<Region> query = em.createQuery(criteria);
		return query.getSingleResult();
	}

}
