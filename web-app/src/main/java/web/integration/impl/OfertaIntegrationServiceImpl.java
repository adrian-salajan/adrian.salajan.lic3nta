package web.integration.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import web.integration.IntegrationConstants;
import web.integration.OfertaIntegration;
import web.integration.OfertaIntegrationService;

@Repository
public class OfertaIntegrationServiceImpl implements OfertaIntegrationService {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void updateStatus(Long ofertaId, String status) {
		OfertaIntegration oferta = em.find(OfertaIntegration.class, ofertaId);
		oferta.setStatus(status);
		em.merge(oferta);
	}

	@Override
	@Transactional
	public String getStatus(Long ofertaId) {
		return em.find(OfertaIntegration.class, ofertaId).getStatus();
	}

	@Override
	@Transactional
	public void create(Long ofertaId, String status) {
		OfertaIntegration ofertaIntegration = new OfertaIntegration();
		ofertaIntegration.setId(ofertaId);
		ofertaIntegration.setStatus(status);
		em.persist(ofertaIntegration);
		
	}

	@Override
	@Transactional
	public void setLock(Long ofertaId, boolean lock) {
		OfertaIntegration oferta = em.find(OfertaIntegration.class, ofertaId);
		oferta.setLocked(lock);
		em.merge(oferta);
	}

	@Override
	@Transactional
	public boolean getLock(Long ofertaId) {
		return em.find(OfertaIntegration.class, ofertaId).getLocked();
		
	}

}
