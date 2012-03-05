package ro.ubb.StockAdapter.dto;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;


public class UriBuilderUtil {
	
	public static URI getCategory(UriInfo uriInfo, Long id) {
		return uriInfo.getBaseUriBuilder().path("categories").path("{categoryId}").build(id);
	}
	
	public static URI insertCategory(UriInfo uriInfo) {
		return uriInfo.getBaseUriBuilder().path("categories").matrixParam("categoryId", new Object[] {}).build();
	}
	
	public static URI updateCategory(UriInfo uriInfo, Long id) {
		return uriInfo.getBaseUriBuilder().path("categories").path("{categoryId}").build(id);
	}
	
	public static URI deleteCategory(UriInfo uriInfo, Long id) {
		return uriInfo.getBaseUriBuilder().path("categories").path("{categoryId}").build(id);
	}
	
	public static URI getProductForCategory(UriBuilder uriInfo, Long categoryId, Long productId) {
		UriBuilder builder = uriInfo.clone();
		return builder.path("categories/" + categoryId).path("product/" + productId).build();
	}
	
	public static URI insertProductForCategory(UriBuilder uriInfo,Long categoryId) {
		UriBuilder builder = uriInfo.clone();
		return builder.path("categories/" + categoryId + "/product").build();
	}
	
	public static URI updateProductForCategory(UriBuilder uriInfo, Long categoryId,Long id) {
		UriBuilder builder = uriInfo.clone();
		return builder.path("categories/" + categoryId).path("product/" + id).build();
	}
	
	public static URI deleteProductForCategory(UriBuilder uriInfo, Long categoryId,Long id) {
		UriBuilder builder = uriInfo.clone();
		return builder.path("categories/" + categoryId).path("product/" + id).build();
	}
	
	
	


}
