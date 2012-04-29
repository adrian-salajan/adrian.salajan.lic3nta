package domain;

import java.util.Collection;

public interface OfertaService {
	
	public Oferta getById(long id);
	public Collection<Oferta> getByStatus(String status);
	public Collection<Oferta> getAll();
	public Collection<Oferta> getOferteComandate();
	public Collection<Oferta> getOferteNecomandate();
	public Collection<Oferta> getByClient(String client);
	public Collection<Oferta> getByRegion(String region);
	
	public void remove(Long ofertaId);
	
	
	
	
	public Oferta updateClient(Oferta oferta, String client);
	

	//-----------

	public Oferta add(Oferta oferta, String client, String region,boolean negotiate );
	
	public Oferta oferteaza(Long ofertaId);
	public Oferta comanda(Long ofertaId,  String adresaLivrare);
	public Oferta proceseazaComanda(Long ofertaId);
	public Oferta finalizeazaComanda(Long ofertaId);
	
	//-----
	
	public Oferta addProdus(Long ofertaId, Product product, int qty);
	public Oferta addProdus(Long ofertaId, Product product, int qty, long finalPrice);
	public Oferta updatePriceProdus(Long ofertaId, Long productId, long finalPrice);
	public Oferta updateQuantityProdus(Long ofertaId, Long productId, int qty);
	
	public Oferta stergeProdus(Long ofertaId, Long productId);
	public Oferta cancel(Long ofertaId);
	
	
	
	

}
