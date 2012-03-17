package service.impl;

import java.util.Collection;
import java.util.Date;

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
	public void remove(Oferta oferta) {
		dao.delete(oferta);
		
	}
	
	@Override
	@Transactional
	public Oferta oferteaza(Oferta oferta) {
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
	public Oferta comanda(Oferta oferta, String adresaLivrare) {
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
	public Oferta proceseazaComanda(Oferta oferta) {
		if (oferta.getStatus().equals(ComenziConstants.OFERTA_ORDERED)) {
			oferta.setStatus(ComenziConstants.OFERTA_ORDER_PROCESSING);
			dao.update(oferta);
		}
		return oferta;
		
		
	}
	
	@Override
	@Transactional
	public Oferta finalizeazaComanda(Oferta oferta) {
		if (oferta.getStatus().equals(ComenziConstants.OFERTA_ORDER_PROCESSING)) {
			oferta.setStatus(ComenziConstants.OFERTA_ORDERED_DONE);
			dao.update(oferta);
		}
		return oferta;
		
	}

	@Override
	@Transactional
	public void updateClient(Oferta oferta, String client) {
		oferta.setClient(client);
		oferta.setLastModified(new Date());
		dao.update(oferta);
		
	}
	
	@Override
	@Transactional
	public Oferta add(Oferta oferta, String client, String region) {
		oferta.setStatus(ComenziConstants.OFERTA_NOUA);
		oferta.setClient(client);
		oferta.setCreated(new Date());
		oferta.setLastModified(new Date());
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
	public Oferta addProdus(Oferta oferta, Product product, int qty) {
		oferta.addProduct(product);
		product.setFinalPrice(product.getPrice());
		product.setQuantity(qty);
		oferta.setStatus(ComenziConstants.OFERTA_IN_NEGOCIERE);
		dao.update(oferta);
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
	public Oferta addProdus(Oferta oferta, Product product, int qty, long finalPrice) {
		oferta.addProduct(product);
		product.setFinalPrice(finalPrice);
		product.setQuantity(qty);
		oferta.setStatus(ComenziConstants.OFERTA_IN_NEGOCIERE);
		dao.update(oferta);
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
	public Oferta updateQuantityProdus(Oferta oferta, Product product, int qty) {
		this.updateProdus(oferta, product, product.getFinalPrice(), qty);
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
	public Oferta updatePriceProdus(Oferta oferta, Product product, long finalPrice) {
		this.updateProdus(oferta, product, finalPrice, product.getQuantity());
		Oferta x = null;
		try {
			x = (Oferta) oferta.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return x;
	}
	
	private void updateProdus(Oferta oferta, Product product, long finalPrice, int qty) {
		if (oferta.getItems().contains(product)) {
			product.setFinalPrice(finalPrice);
			product.setQuantity(qty);
			oferta.setStatus(ComenziConstants.OFERTA_IN_NEGOCIERE);
			dao.update(oferta);
		}
	}


	@Override
	@Transactional
	public Oferta stergeProdus(Oferta oferta, Product product) {
		oferta.getItems().remove(product);
		oferta.setStatus(ComenziConstants.OFERTA_IN_NEGOCIERE);
		dao.update(oferta);
		Oferta x = null;
		try {
			x = (Oferta) oferta.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return x;
	}

	public OfertaDao getDao() {
		return dao;
	}

	public void setDao(OfertaDao dao) {
		this.dao = dao;
	}
	
	


}
