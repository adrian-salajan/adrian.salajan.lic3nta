package ro.ubb.StockAdapter.gateway.exceptions;

public class StockGatewayException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6696293174437157741L;

	public StockGatewayException(Exception e) {
		super(e);
	}
	
	public StockGatewayException(String message, Exception e) {
		super(message, e);
	}

}
