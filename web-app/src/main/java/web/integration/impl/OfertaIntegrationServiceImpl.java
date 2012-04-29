package web.integration.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
