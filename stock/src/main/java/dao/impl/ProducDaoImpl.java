package dao.impl;

import java.util.Collection;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import model.Product;
import dao.ProductDao;

@Component(value = "productDao")
public class ProducDaoImpl implements ProductDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public void insert(Product product) {
		Session currentSession = getSessionFactory().getCurrentSession();
		currentSession.persist(product);
		currentSession.flush();
		
	}

	public void update(Product product) {
		getSessionFactory().getCurrentSession().merge(product);
		
	}

	public void delete(Product product) {
		getSessionFactory().getCurrentSession().delete(product);
		
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Product getById(long id) {
		return (Product) getSessionFactory().getCurrentSession().get(Product.class, new Long(id));
		
	}

	@Override
	public Collection<Product> getByName(String name, int results, int page) {
		return (Collection<Product>) getSessionFactory().getCurrentSession().createCriteria(Product.class)
				.add(Restrictions.eq("name", name)).setMaxResults(results).setFirstResult(results * page).list();
	}
	
	

}
