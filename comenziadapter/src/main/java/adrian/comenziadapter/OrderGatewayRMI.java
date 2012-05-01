package adrian.comenziadapter;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import domain.Oferta;
import domain.OfertaService;
import domain.Product;

public class OrderGatewayRMI implements OrderGateway {
	
	private OfertaService orders;
	
	public Oferta getById(long id) {
		return orders.getById(id);
	}
	public Collection<Oferta> getByStatus(String status) {
		return orders.getByStatus(status);
	}
	
	public Collection<Oferta> getByRegion(String region) {
		return orders.getByRegion(region);
	}
	public Collection<Oferta> getAll() {
		return orders.getAll();
	}
	public Collection<Oferta> getOferteComandate() {
		return orders.getOferteComandate();
	}
	public Collection<Oferta> getOferteNecomandate() {
		return orders.getOferteNecomandate();
	}
	
	
	public void remove(Long ofertaId) {
		orders.remove(ofertaId);
	}
	
	
	public void updateClient(Oferta oferta, String client) {
		orders.updateClient(oferta, client);
	}
	

	//-----------

	public Oferta add(Oferta oferta, String client, String region,boolean negotiated) {
		return orders.add(oferta, client, region, negotiated);
	}
	
	public Oferta oferteaza(Long ofertaId) {
		return orders.oferteaza(ofertaId);
	}
	public Oferta comanda(Long ofertaId,  String adresaLivrare) {
		return orders.comanda(ofertaId, adresaLivrare);
	}
	public Oferta proceseazaComanda(Long ofertaId) {
		return orders.proceseazaComanda(ofertaId);
	}
	public Oferta finalizeazaComanda(Long ofertaId) {
		return orders.finalizeazaComanda(ofertaId);
	}
	
	//-----
	
	public Oferta addProdus(Long ofertaId, Product product, int qty) {
		return orders.addProdus(ofertaId,product, qty);
	}
	public Oferta addProdus(Long ofertaId, Product product, int qty, long finalPrice) {
		return orders.addProdus(ofertaId, product, qty, finalPrice);
	}
	public Oferta updatePriceProdus(Long ofertaId, Long productId, long finalPrice) {
		return orders.updatePriceProdus(ofertaId, productId, finalPrice);
	}
	public Oferta updateQuantityProdus(Long ofertaId, Long productId, int qty) {
		return orders.updateQuantityProdus(ofertaId, productId, qty);
	}
	public Oferta stergeProdus(Long ofertaId, Long productId) {
		return orders.stergeProdus(ofertaId, productId);
	}
	
	public void setOrders(OfertaService orders) {
		this.orders = orders;
	}
	@Override
	public Collection<Oferta> getByClient(String client) {
		return orders.getByClient(client);
	}
	@Override
	public Oferta cancel(Long ofertaId) {
		return orders.cancel(ofertaId);
		
	}

	
	
	
	
	
	
	

}
