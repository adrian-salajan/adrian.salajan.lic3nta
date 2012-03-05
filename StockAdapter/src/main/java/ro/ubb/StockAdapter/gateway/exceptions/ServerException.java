package ro.ubb.StockAdapter.gateway.exceptions;

public class ServerException extends StockGatewayException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6075819054402588584L;

	public ServerException(Exception e) {
		super("The server could not fulfill your request.", e);
	}

}
