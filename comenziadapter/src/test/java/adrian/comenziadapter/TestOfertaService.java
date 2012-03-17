package adrian.comenziadapter;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Oferta;
import domain.OfertaService;
import domain.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springContext.xml"})
public class TestOfertaService {
	
	@Autowired
	OfertaService service;
	
	@Test
	public void testOfertaFlow() {
		
		Oferta o = new Oferta();
		Oferta x;
		x = service.add(o, "adrian2", "Cluj");
		assertEquals("new", x.getStatus());
	
		Product p1 = new Product();
		p1.setDetails("details");
		p1.setPrice(555L);
		p1.setFinalPrice(555L);
		p1.setName("product1");
		p1.setQuantity(555);
		
		x = service.addProdus(x, p1, 13);
		assertEquals( 1, x.getItems().size());
		assertEquals(555, x.getItems().toArray(new Product[] {})[0].getFinalPrice());
		assertEquals("inprogress", x.getStatus());
		
		Product p2 = new Product();
		p2.setDetails("details2");
		p2.setPrice(666L);
		p2.setFinalPrice(666L);
		p2.setName("product2");
		p2.setQuantity(666);
		
		x = service.addProdus(x, p2, 10);
		assertEquals( 2, x.getItems().size());
		
	
		x = service.stergeProdus(x, p2);
		assertEquals( 1, x.getItems().size());
		
		Product auxProduct = x.getItems().toArray(new Product[] {})[0];
		x= service.updatePriceProdus(x, auxProduct, 111L);
		
		assertEquals(111L, x.getItems().toArray(new Product[] {})[0].getFinalPrice());
		assertEquals(13, x.getItems().toArray(new Product[] {})[0].getQuantity());
		
		x = service.oferteaza(x);
		assertEquals("ready", x.getStatus());
		
		x = service.comanda(x, "Plopilor 60");
		assertEquals("unprocessed", x.getStatus());
		assertNotNull(x.getComanda());
		assertEquals("Plopilor 60", x.getComanda().getShipAddress());
		
		x = service.proceseazaComanda(x);
		assertEquals("processing", x.getStatus());
		
		x = service.finalizeazaComanda(x);
		assertEquals("processed", x.getStatus());
		
		
	}

}
