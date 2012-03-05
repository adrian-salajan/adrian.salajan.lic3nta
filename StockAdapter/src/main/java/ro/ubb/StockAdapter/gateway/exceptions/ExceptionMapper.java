package ro.ubb.StockAdapter.gateway.exceptions;

import com.sun.jersey.api.client.UniformInterfaceException;

public class ExceptionMapper {
	
	public static StockGatewayException map(UniformInterfaceException e, Long entityId) {
		try {
			if (e.getResponse().getStatus() >= 500)
				return new ServerException(e);
			if (e.getResponse().getStatus() == 410) {
				return  new EntityNotFoundException(entityId, e);
			}
		} catch (Exception npe) {
			return  new ServerException(e);
		}
		return new ServerException(e);
	}
	
	public static StockGatewayException map(UniformInterfaceException e) {
		return ExceptionMapper.map(e, -1L);
	}
}
