package ro.ubb.comenzi.dao.impl;

import java.util.Collection;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import domain.Oferta;

import ro.ubb.comenzi.dao.OfertaDao;

@Repository(value = "ofertaDao")
public class OfertaDaoHibernate implements OfertaDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public OfertaDaoHibernate() {}

	@Override
	public Oferta getById(long id) {
		return (Oferta) this.getSessionFactory().getCurrentSession().get(Oferta.class, new Long(id));
	}

	@Override
	public Collection<Oferta> getByStatus(String status) {
		return this.getSessionFactory().getCurrentSession().createCriteria(Oferta.class).add(Restrictions.eq("status", status)).list();
	}

	@Override
	public Collection<Oferta> getByComandaStatus(String status) {
		return this.getSessionFactory().getCurrentSession().createCriteria(Oferta.class).add(Restrictions.eq("comanda.status", status)).list();
	}

	@Override
	public Collection<Oferta> getAll() {
		return this.getSessionFactory().getCurrentSession().createCriteria(Oferta.class).list();
	}

	@Override
	public Collection<Oferta> getOferteComandate() {
		return this.getSessionFactory().getCurrentSession().createCriteria(Oferta.class).add(Restrictions.isNotNull("comanda")).list();
	}

	@Override
	public Collection<Oferta> getOferteNecomandate() {
		return this.getSessionFactory().getCurrentSession().createCriteria(Oferta.class).add(Restrictions.isNull("comanda")).list();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void insert(Oferta oferta) {
		this.getSessionFactory().getCurrentSession().persist(oferta);
		
	}

	@Override
	public void delete(Oferta oferta) {
		this.getSessionFactory().getCurrentSession().delete(oferta);
	}

	@Override
	public void update(Oferta oferta) {
		Session session = this.getSessionFactory().getCurrentSession();
		session.merge(oferta);
		session.flush();
	}
	
	

}
