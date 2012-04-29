package web.integration;


public interface OfertaIntegrationService {
	
	public void create(Long ofertaId, String status);
	public void updateStatus(Long ofertaId, String status);
	public String getStatus(Long ofertaId);

}
