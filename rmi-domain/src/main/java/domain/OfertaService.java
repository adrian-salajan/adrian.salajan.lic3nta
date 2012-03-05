package domain;

import java.util.Collection;

public interface OfertaService {
	
	public Oferta getById(long id);
	public Collection<Oferta> getByStatus(String status);
	public Collection<Oferta> getAll();
	public Collection<Oferta> getOferteComandate();
	public Collection<Oferta> getOferteNecomandate();
	
	
	public void remove(Oferta oferta);
	
	
	
	
	public void updateClient(Oferta oferta, String client);
	

	//-----------

	public Oferta add(Oferta oferta, String client);
	
	public Oferta oferteaza(Oferta oferta);
	public Oferta comanda(Oferta oferta,  String adresaLivrare);
	public Oferta proceseazaComanda(Oferta oferta);
	public Oferta finalizeazaComanda(Oferta oferta);
	
	//-----
	
	public Oferta addProdus(Oferta oferta, Product product, int qty);
	public Oferta addProdus(Oferta oferta, Product product, int qty, long finalPrice);
	public Oferta updatePriceProdus(Oferta oferta, Product product, long finalPrice);
	public Oferta updateQuantityProdus(Oferta oferta, Product product, int qty);
	
	public Oferta stergeProdus(Oferta oferta, Product product);
	
	
	

}
