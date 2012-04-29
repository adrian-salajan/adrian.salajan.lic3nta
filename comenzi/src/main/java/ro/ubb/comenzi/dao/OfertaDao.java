package ro.ubb.comenzi.dao;

import java.util.Collection;

import domain.Oferta;


public interface OfertaDao {
	
	
	public void insert(Oferta oferta);
	public void delete(Oferta oferta);
	public Oferta update(Oferta oferta);
	
	public Oferta getById(long id);
	public Collection<Oferta> getByStatus(String status);
	public Collection<Oferta> getByComandaStatus(String status);
	public Collection<Oferta> getAll();
	public Collection<Oferta> getOferteComandate();
	public Collection<Oferta> getOferteNecomandate();
	public Collection<Oferta> getByClient(String client);
	public Collection<Oferta> getByRegion(String region);

}
