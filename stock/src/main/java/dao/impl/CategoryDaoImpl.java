package dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.TypedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import model.Category;
import dao.CategoryDao;

@Component("categoryDao")
public class CategoryDaoImpl implements CategoryDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void insert(Category category) {
		getSessionFactory().getCurrentSession().persist(category);
	}

	@Override
	public void delete(Category category) {
		getSessionFactory().getCurrentSession().delete(category);
		
	}

	@Override
	public Category get(long id) {
		return (Category) getSessionFactory().getCurrentSession().get(Category.class, id);
		
	}

	@Override
	public Category get(String name) {
		return (Category) getSessionFactory().getCurrentSession().createCriteria(Category.class).add(Restrictions.eq("name", name)).uniqueResult();
			
			
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void update(Category category) {
		 getSessionFactory().getCurrentSession().merge(category);
	}

	@Override
	public Collection<Category> getAll() {
		 return (Collection<Category>) getSessionFactory().getCurrentSession().createCriteria(Category.class).list();
	}
	
	

}
