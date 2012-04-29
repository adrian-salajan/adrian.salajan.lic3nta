package service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Comanda;
import domain.Oferta;
import domain.OfertaService;
import domain.Product;

import ro.ubb.comenzi.dao.OfertaDao;
import ro.ubb.comenzi.util.ComenziConstants;

@Service(value="orderService")
public class OfertaServiceImpl implements OfertaService {
	
	@Autowired
	private OfertaDao dao;
	
	public OfertaServiceImpl() {}

	@Override
	@Transactional
	public Oferta getById(long id) {
		return dao.getById(id);
	}

	@Override
	@Transactional
	public Collection<Oferta> getByStatus(String status) {
		return dao.getByStatus(status);
	}

	@Override
	@Transactional
	public Collection<Oferta> getAll() {
		System.out.println("rmi");
		return dao.getAll();
	}

	@Override
	@Transactional
	public Collection<Oferta> getOferteComandate() {
		return dao.getOferteComandate();
	}

	@Override
	@Transactional
	public Collection<Oferta> getOferteNecomandate() {
		return  dao.getOferteNecomandate();
	}

	
	@Override
	@Transactional
	public void remove(Long ofertaId) {
		Oferta oferta = dao.getById(ofertaId);
		dao.delete(oferta);
		
	}
	
	@Override
	@Transactional
	public Oferta oferteaza(Long ofertaId) {
		Oferta oferta = dao.getById(ofertaId);
		if (oferta.getStatus().equals(ComenziConstants.OFERTA_IN_NEGOCIERE)) {
			oferta.setStatus(ComenziConstants.OFERTA_READY);
			
			//TODO: print offer
			oferta.setLastModified(new Date());
			dao.update(oferta);
		}
		return oferta;
	}

	@Override
	@Transactional
	public Oferta comanda(Long ofertaId, String adresaLivrare) {
		Oferta oferta = dao.getById(ofertaId);
		if (oferta.getStatus().equals(ComenziConstants.OFERTA_READY)) {
			Comanda c  = oferta.getComanda();
			c.setShipAddress(adresaLivrare);
			c.setCreated(new Date()); 
			oferta.setLastModified(new Date());
			oferta.setStatus(ComenziConstants.OFERTA_ORDERED);
			dao.update(oferta);
			
		}
		return oferta;
	}

	@Override
	@Transactional
	public Oferta proceseazaComanda(Long ofertaId) {
		Oferta oferta = dao.getById(ofertaId);
		if (oferta.getStatus().equals(ComenziConstants.OFERTA_ORDERED)) {
			oferta.setStatus(ComenziConstants.OFERTA_ORDER_PROCESSING);
			dao.update(oferta);
		}
		return oferta;
		
		
	}
	
	@Override
	@Transactional
	public Oferta finalizeazaComanda(Long ofertaId) {
		Oferta oferta = dao.getById(ofertaId);
		if (oferta.getStatus().equals(ComenziConstants.OFERTA_ORDER_PROCESSING)) {
			oferta.setStatus(ComenziConstants.OFERTA_ORDERED_DONE);
			dao.update(oferta);
		}
		return oferta;
		
	}

	@Override
	@Transactional
	public Oferta updateClient(Oferta oferta, String client) {
		oferta.setClient(client);
		oferta.setLastModified(new Date());
		return dao.update(oferta);
		
	}
	
	@Override
	@Transactional
	public Oferta add(Oferta oferta, String client, String region, boolean negotiate) {
		oferta.setStatus(negotiate == true ? ComenziConstants.OFERTA_IN_NEGOCIERE : ComenziConstants.OFERTA_READY);
		oferta.setClient(client);
		oferta.setCreated(new Date());
		oferta.setLastModified(new Date());
		for (Product p : oferta.getItems()) {
			p.setFinalPrice(p.getPrice());
		}
		Comanda c = new Comanda();
		c.setShipAddressRegion(region);
		oferta.setComanda(c);
		dao.insert(oferta);
		Oferta x = null;
		try {
			x = (Oferta) oferta.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return x;
	}
	

	@Override
	@Transactional
	public Oferta addProdus(Long ofertaId, Product product, int qty) {
		Oferta oferta = this.getById(ofertaId);
		oferta.addProduct(product);
		product.setFinalPrice(product.getPrice());
		product.setQuantity(qty);
		oferta.setStatus(ComenziConstants.OFERTA_IN_NEGOCIERE);
		return dao.update(oferta);
	}
	
	@Override
	@Transactional
	public Oferta addProdus(Long ofertaId, Product product, int qty, long finalPrice) {
		Oferta oferta = this.getById(ofertaId);
		oferta.addProduct(product);
		product.setFinalPrice(finalPrice);
		product.setQuantity(qty);
		oferta.setStatus(ComenziConstants.OFERTA_IN_NEGOCIERE);
		return dao.update(oferta);
	}
	
	@Override
	@Transactional
	public Oferta updateQuantityProdus(Long ofertaId, Long productId, int qty) {
		Oferta oferta = this.getById(ofertaId);
		return this.updateProdus(oferta, productId, null, qty);
	
	}
	
	@Override
	@Transactional
	public Oferta updatePriceProdus(Long ofertaId, Long productId, long finalPrice) {
		Oferta oferta = this.getById(ofertaId);
		return this.updateProdus(oferta, productId, finalPrice, null);
		
	}
	
	private Oferta updateProdus(Oferta oferta, Long productId, Long finalPrice, Integer qty) {
		for (Product p : oferta.getItems()) {
			if (p.getId() == productId) {
				if (finalPrice != null) {
					p.setFinalPrice(finalPrice);
				}
				if (qty != null) {
					p.setQuantity(qty);
				}
				break;
			}
		}
		oferta.setStatus(ComenziConstants.OFERTA_IN_NEGOCIERE);
		return dao.update(oferta);
		
	}


	@Override
	@Transactional
	public Oferta stergeProdus(Long ofertaId, Long productId) {
		Oferta oferta = dao.getById(ofertaId);
		oferta = this.getById(oferta.getId());
		Iterator<Product> iterator = oferta.getItems().iterator();
		while (iterator.hasNext()) {
			Product p = iterator.next();
			if (p.getId() == productId)
				iterator.remove();
			
		}
		oferta.setStatus(ComenziConstants.OFERTA_IN_NEGOCIERE);
		return dao.update(oferta);
	}

	public OfertaDao getDao() {
		return dao;
	}

	public void setDao(OfertaDao dao) {
		this.dao = dao;
	}

	@Override
	@Transactional
	public Collection<Oferta> getByClient(String client) {
		return dao.getByClient(client);
	}

	@Override
	@Transactional
	public Collection<Oferta> getByRegion(String region) {
		return dao.getByRegion(region);
	}

	@Override
	@Transactional
	public Oferta cancel(Long ofertaId) {
		Oferta oferta = dao.getById(ofertaId);
		oferta.setStatus(ComenziConstants.OFERTA_CANCELED);
		Oferta canceled = dao.update(oferta);
		return canceled;
	}
	
	


}
