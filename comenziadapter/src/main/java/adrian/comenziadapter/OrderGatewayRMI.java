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
	public Collection<Oferta> getAll() {
		return orders.getAll();
	}
	public Collection<Oferta> getOferteComandate() {
		return orders.getOferteComandate();
	}
	public Collection<Oferta> getOferteNecomandate() {
		return orders.getOferteNecomandate();
	}
	
	
	public void remove(Oferta oferta) {
		orders.remove(oferta);
	}
	
	
	public void updateClient(Oferta oferta, String client) {
		orders.updateClient(oferta, client);
	}
	

	//-----------

	public Oferta add(Oferta oferta, String client, String region) {
		return orders.add(oferta, client, region);
	}
	
	public Oferta oferteaza(Oferta oferta) {
		return orders.oferteaza(oferta);
	}
	public Oferta comanda(Oferta oferta,  String adresaLivrare) {
		return orders.comanda(oferta, adresaLivrare);
	}
	public Oferta proceseazaComanda(Oferta oferta) {
		return proceseazaComanda(oferta);
	}
	public Oferta finalizeazaComanda(Oferta oferta) {
		return orders.finalizeazaComanda(oferta);
	}
	
	//-----
	
	public Oferta addProdus(Oferta oferta, Product product, int qty) {
		return orders.addProdus(oferta,product, qty);
	}
	public Oferta addProdus(Oferta oferta, Product product, int qty, long finalPrice) {
		return orders.addProdus(oferta, product, qty, finalPrice);
	}
	public Oferta updatePriceProdus(Oferta oferta, Product product, long finalPrice) {
		return orders.updatePriceProdus(oferta, product, finalPrice);
	}
	public Oferta updateQuantityProdus(Oferta oferta, Product product, int qty) {
		return orders.updateQuantityProdus(oferta, product, qty);
	}
	public Oferta stergeProdus(Oferta oferta, Product product) {
		return orders.stergeProdus(oferta, product);
	}
	
	public void setOrders(OfertaService orders) {
		this.orders = orders;
	}

	
	
	
	
	
	
	

}
