package webresources;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import model.Category;

import org.exolab.castor.xml.dtd.GeneralEntity;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.jersey.api.core.ResourceContext;
import com.sun.jersey.api.uri.UriBuilderImpl;

import response.CategoryProductsResponse;
import response.StockResponse;

import utils.Converter;

import dao.CategoryDao;
import dto.CategoryDTO;
import dto.CategoryProductsDTO;
import dto.ProductDTO;

@Service
@Scope(value = "request")
@Produces(MediaType.APPLICATION_XML)
public class CategoryResource {
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Context
	private ResourceContext rc;
	
	Request request;
	UriInfo uriInfo;
	
	String category;

	public CategoryResource() {}
	
	@GET
	@Transactional
	public Response getCategory() {
		try {
			Category c = categoryDao.get(Long.parseLong(category));
			
			if (c==null)
				return Response.status(410).build(); //gone
			
			CategoryProductsDTO categoryProducts = Converter.toCategoryProductsDTO(c, uriInfo);
			
			GenericEntity<CategoryProductsDTO> entity = new GenericEntity<CategoryProductsDTO>(categoryProducts) {};
			return Response.ok().entity(entity).build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
			
		}
		
	}
	

	
	/**
	 * Updates a category.
	 * @param categoryDTO The new version of the category.
	 * @return links to related services
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Transactional
	public Response updateCategory(CategoryDTO categoryDTO) {
		try {
			Category old;
			old = categoryDao.get(Long.parseLong(this.category));
			
			if (old == null) {
				return Response.status(410).build(); //gone
			}
			
			old.setName(categoryDTO.name);
			categoryDao.update(old);
			
			CategoryDTO categoryDto = Converter.toDTO(old, uriInfo);
			
			GenericEntity<CategoryDTO> entity = new GenericEntity<CategoryDTO>(categoryDto) {};
			
			return Response.ok().entity(entity).build();
			
		} catch ( Exception e) {
			return Response.serverError().build();
		}
		
	}
	
	@DELETE
	@Transactional
	public Response deleteCategory() {
		System.out.println("DELETEEEEEEE");
		Category toDelete = null;
		try {
			
			toDelete = categoryDao.get(Long.parseLong(this.category));
			if (toDelete == null) {
				return Response.status(410).build(); //gone
			}
			categoryDao.delete(toDelete);
			Collection<CategoryDTO> categories = Converter.toDTO(categoryDao.getAll(), this.uriInfo);
			
			GenericEntity<Collection<CategoryDTO>> entity = new GenericEntity<Collection<CategoryDTO>>(categories) {};
			return Response.ok().entity(entity).build();
			
			
		} catch (Exception e) {
			return Response.serverError().build();
		}
		
	}
	

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
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

	public void setCategory(String category) {
		this.category = category;
	}
	
	@Path("/product")
	public ProductResource getProduct() {
		ProductResource productResource = rc.getResource(ProductResource.class);
		productResource.setRequest(request);
		productResource.setUriInfo(uriInfo);
		productResource.setCategory(category);
		return productResource;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
