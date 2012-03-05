package ro.ubb.StockAdapter.gateway;

import ro.ubb.StockAdapter.dto.LinkedService;
import ro.ubb.StockAdapter.dto.ServiceLink;

public class GatewayHelper {
	
	private static final String insert = "INSERT";
	private static final String delete = "DELETE";
	private static final String update = "UPDATE";
	private static final String get = "GET";
	private static final String up = "UP";
	private static final String insertin = "INSERT-IN";
	
	public static String getInsertURI(LinkedService entity) {
		for (ServiceLink uri : entity.links) {
			if (uri.name.equals(GatewayHelper.insert)) {
				return uri.uri.toString();
			}
		}
		return "";
	}
	
	public static String getDeleteURI(LinkedService entity) {
		for (ServiceLink uri : entity.links) {
			if (uri.name.equals(GatewayHelper.delete)) {
				return uri.uri.toString();
			}
		}
		return "";
	}
	
	public static String getUpdateURI(LinkedService entity) {
		for (ServiceLink uri : entity.links) {
			if (uri.name.equals(GatewayHelper.update)) {
				return uri.uri.toString();
			}
		}
		return "";
	}
	
	public static String getGetURI(LinkedService entity) {
		for (ServiceLink uri : entity.links) {
			if (uri.name.equals(GatewayHelper.get)) {
				return uri.uri.toString();
			}
		}
		return "";
	}
	
	public static String getUpURI(LinkedService entity) {
		for (ServiceLink uri : entity.links) {
			if (uri.name.equals(GatewayHelper.up)) {
				return uri.uri.toString();
			}
		}
		return "";
	}
	
	public static String getInsertInURI(LinkedService entity) {
		for (ServiceLink uri : entity.links) {
			if (uri.name.equals(GatewayHelper.insertin)) {
				return uri.uri.toString();
			}
		}
		return "";
	}

}
