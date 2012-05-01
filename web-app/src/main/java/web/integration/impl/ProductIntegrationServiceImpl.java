package web.integration.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import domain.Product;

import web.integration.ProductIntegration;
import web.integration.ProductIntegrationService;
import web.mvc.model.ProductOrdered;

@Repository
public class ProductIntegrationServiceImpl implements ProductIntegrationService {
	
	@PersistenceContext
	private EntityManager em;

	@Transactional()
	private void addMapping(Long stockId, Long orderedProductId, Long categoryId) {
		ProductIntegration p = new ProductIntegration();
		p.setStockId(stockId);
		p.setOrderedProductId(orderedProductId);
		p.setCategoryId(categoryId);
		try {
			em.persist(p);
		} catch (Exception e) {
			System.out.println("info: Product Integration prevented duplicate.");
		}
		
		
	}

	@Override
	@Transactional
	public ProductIntegration getStockId(Long orderedProductId) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ProductIntegration> criteriaQuery = builder.createQuery(ProductIntegration.class);
		Root<ProductIntegration> from = criteriaQuery.from(ProductIntegration.class);
		criteriaQuery.where(builder.equal(from.get("orderedProductId"), orderedProductId));
		return em.createQuery(criteriaQuery).getSingleResult();
	}

	@Override
	public void mapProducts(List<ProductOrdered> stockProducts,
			Collection<Product> orderedProducts) {
		
		for (ProductOrdered stock : stockProducts) {
			String name = stock.getProduct().getName();
			
			for (Product ordered : orderedProducts) {
				if (name.equals(ordered.getName())) {
					this.addMapping(stock.getProduct().getId(), ordered.getId(), stock.getProduct().getCategoryId());
					break;
				}
			}
			
		}
		
	}

}
