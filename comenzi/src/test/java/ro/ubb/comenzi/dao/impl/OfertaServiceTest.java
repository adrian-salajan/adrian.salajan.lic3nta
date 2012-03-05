package ro.ubb.comenzi.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import domain.Oferta;
import domain.OfertaService;
import domain.Product;
import static org.springframework.util.Assert.*;

import ro.ubb.comenzi.util.ComenziConstants;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springContext.xml"})
@TransactionConfiguration(defaultRollback=false)
@Transactional
public class OfertaServiceTest {
	
	@Autowired
	OfertaService service;
	
	@Test
	@Rollback(value = true)
	public void testAdd() {
		Oferta oferta = new Oferta();
		service.add(oferta, "xxx yy");
	}
	
	@Test
	@Rollback(value = true)
	public void addProduct() {
		Oferta oferta = service.getById(1);
		Product p = new Product();
		p.setName("robinet");
		p.setDetails("robinet details");
		p.setPrice(132);
		service.addProdus(oferta, p, 5);
		Product p2 = new Product();
		p2.setName("teava");
		p2.setDetails("teava details");
		p2.setPrice(321);
		service.addProdus(oferta, p2, 10, 999);
		
	}
	
	@Test
	@Rollback(value = true)
	public void testUpdatePriceProdus() {
		Oferta o = service.getById(1);
		Product p = (Product) o.getItems().toArray()[0];
		service.updatePriceProdus(o, p, 555);
		o = service.getById(1);
		Product newp = (Product) o.getItems().toArray()[0];
		isTrue(555 == p.getFinalPrice());
		isTrue(p.getPrice() == newp.getPrice());
		isTrue(p.getQuantity() == newp.getQuantity());
	}
	
	@Test
	@Rollback(value = true)
	public void testUpdateQuantityProdus() {
		Oferta o = service.getById(1);
		Product p = (Product) o.getItems().toArray()[0];
		service.updateQuantityProdus(o, p, 33);
		o = service.getById(1);
		Product newp = (Product) o.getItems().toArray()[0];
		isTrue(p.getFinalPrice() == newp.getFinalPrice());
		isTrue(p.getPrice() == newp.getPrice());
		isTrue(33 == newp.getQuantity());
	}
	
	@Test
	@Rollback(value = true)
	public void testStergeProdus() {
		Oferta o = service.getById(1);
		Product sters = (Product) o.getItems().toArray()[0];
		
		 isTrue(o.getItems().contains(sters));
		 
		service.stergeProdus(o, sters);
		
		 o = service.getById(1);
		 isTrue(0 == o.getItems().size());
		 isTrue(!o.getItems().contains(sters));
	}
	@Test
	@Rollback(value = true)
	public void testOferteaza() {
		Oferta o = service.getById(1);
		isTrue(o.getStatus().equals(ComenziConstants.OFERTA_IN_NEGOCIERE));
		service.oferteaza(o);
		o = service.getById(1);
		isTrue(o.getStatus().equals(ComenziConstants.OFERTA_READY));
		
	}
	
	@Test
	@Rollback(value = true)
	public void testOfertaComandaFlow() {
		Oferta o = new Oferta();
		service.add(o, "john doe");
		isTrue(o.getStatus().equals(ComenziConstants.OFERTA_NOUA));
		Product p = new Product();
		p.setName("p1");
		p.setPrice(1);
		p.setQuantity(1);
		
		service.addProdus(o, p, 1);
		isTrue(o.getStatus().equals(ComenziConstants.OFERTA_IN_NEGOCIERE));
		
		service.oferteaza(o);
		isTrue(o.getStatus().equals(ComenziConstants.OFERTA_READY));
	
		service.comanda(o, "Plopilor 60");
		isTrue(o.getStatus().equals(ComenziConstants.OFERTA_ORDERED));
		
		service.proceseazaComanda(o);
		isTrue(o.getStatus().equals(ComenziConstants.OFERTA_ORDER_PROCESSING));
		
		service.finalizeazaComanda(o);
		isTrue(o.getStatus().equals(ComenziConstants.OFERTA_ORDERED_DONE));
		
	
		
	}
	
	
	
}
