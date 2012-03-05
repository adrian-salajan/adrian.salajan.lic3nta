package ro.ubb.StockAdapter.gateway.exceptions;

/**
 * Exception informing that the entity was not found.
 * @author blitzkr1eg
 *
 */
public class EntityNotFoundException extends StockGatewayException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2181668528296067783L;

	public EntityNotFoundException(Long entityId, Exception e) {
			super(entityId > 0 ?"The entity with id " + entityId + " was not found.":"The entity was not found.", e);
	}

}
