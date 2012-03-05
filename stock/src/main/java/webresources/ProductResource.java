package webresources;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import model.Category;
import model.Product;
import model.Property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import response.InsertedProductResponse;
import response.StockResponse;

import utils.Converter;

import dao.CategoryDao;
import dao.ProductDao;
import dto.CategoryProductsDTO;
import dto.LinkedService;
import dto.ProductDTO;
import dto.ServiceLink;

@Service
@Scope(value = "request")
@Produces( { "application/xml" })
public class ProductResource {
	
	@Context
	private Request request;
	
	@Context
	private UriInfo uriInfo;
	
	@Autowired
	private CategoryDao cDao;
	
	@Autowired
	private ProductDao pdao;
	
	private String category;
	
	public ProductResource() {
		
	}
	
	@GET
	@Transactional
	@Path("/{productId}")
	public Response getProduct( @PathParam(value = "productId") Long productId) {
		try {
			Product product = pdao.getById(productId.longValue());
			if (product == null) 
				return Response.status(410).build(); //gone
			
			ProductDTO dto = Converter.toDTO(product, Long.parseLong(this.category), uriInfo);
			
			GenericEntity<ProductDTO> entity = new GenericEntity<ProductDTO>(dto) {};
			
			return Response.ok().entity(entity).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Transactional
	public Response insertProduct(ProductDTO product) {
		try {
			product.id = 0L;
			Product p = Converter.toProduct(product);
			
			for(Property pr : p.getProperties()) {
				pr.setId(0L);
			}
			
			Category category = cDao.get(Long.parseLong(this.category));
			if (category == null) 
				return Response.status(410).build();
			
			pdao.insert(p);
			category.addProduct(p);
			cDao.update(category);
			
			
			ProductDTO pdto = Converter.toDTO(p, Long.parseLong(this.category), uriInfo);
			
			GenericEntity<ProductDTO> entity = new GenericEntity<ProductDTO>(pdto) {};
			
			return Response.status(201).entity(entity).location(new URI(this.getGetURI(pdto))).build();
			
			
		} catch (Exception e) {
			return Response.serverError().build();
		}
		
		
	}
	
	@POST
	@Path(value = "/{productId}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	@Transactional
	public Response updateProduct(ProductDTO product, @PathParam("productId") Long productId) {
		try {
			System.out.println("update----------------------");
			Product old = pdao.getById(productId);
			if (old == null) {
				return Response.notModified().build();
			}
			old.setName(product.name);
			old.setPrice(product.price);
			old.setStock(product.stock);
			old.getProperties().clear();
			old.getProperties().addAll((Converter.toProperties(product.properties)));
			old.setDescription(product.description);
			
			Category category = cDao.get(Long.parseLong(this.category));
			old = Converter.toProduct(product);
			old.setCategory(category);
			old.setId(productId);
			pdao.update(old);
			
			ProductDTO dto = Converter.toDTO(old, Long.parseLong(this.category), uriInfo);
			
			GenericEntity<ProductDTO> entity = new GenericEntity<ProductDTO>(dto) {};
			return Response.ok().entity(entity).build();
			
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}
	
	@DELETE
	@Path(value = "/{productId}")
	@Consumes(MediaType.APPLICATION_XML)
	@Transactional
	public Response deleteProduct(@PathParam("productId") Long productId) {
		try {
			Product product = pdao.getById(productId.longValue());
			if (product == null) {
				return Response.status(410).build(); //gone (not found)
			}
			
			
			Category c = cDao.get(Long.parseLong(category));
			c.getProducts().remove(product);
			product.setCategory(null);
			pdao.delete(product);
			cDao.update(c);
			
			CategoryProductsDTO categoryProducts = Converter.toCategoryProductsDTO(c, uriInfo);
			
			GenericEntity<CategoryProductsDTO> entity = new GenericEntity<CategoryProductsDTO>(categoryProducts) {};
			return Response.ok().entity(entity).build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}
	

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public UriInfo getUriInfo() {
		return uriInfo;
	}

	public void setUriInfo(UriInfo uriInfo) {
		this.uriInfo = uriInfo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	private String getGetURI(LinkedService linkedService) {
		for (ServiceLink uri : linkedService.links) {
			if (uri.name.equals("GET")) {
				return uri.getURI();
			}
		}
		return "";
	}
	
	

}
