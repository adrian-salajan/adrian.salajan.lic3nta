package webresources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import response.AllCategoriesResponse;

import com.sun.jersey.api.core.ResourceContext;

import utils.Converter;

import dao.CategoryDao;
import dto.CategoryDTO;
import dto.CategoryProductsDTO;
import dto.LinkedService;
import dto.ServiceLink;

@Service
@Scope(value = "request")
@Produces( { "application/xml" })
@Path("categories")
public class CategoriesResource {
	
	@Context
	Request request;
	@Context
	ResourceContext context;
	@Context
	UriInfo uriInfo;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@GET
	@Transactional
	public Response getCategoriesResource() {
		try {
			Collection<CategoryDTO> categories = Converter.toDTO(categoryDao.getAll(), this.uriInfo);
			
	
			
			GenericEntity<Collection<CategoryDTO>> entity = new GenericEntity<Collection<CategoryDTO>>(categories) {};
			return Response.ok().entity(entity).build();
			
		} catch (HibernateException e) {
			return Response.serverError().build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Transactional
	public Response insertCategory(CategoryDTO newCategory) {
		Category category = new Category();
		category.setName(newCategory.name);
		try {
			
			categoryDao.insert(category);
			CategoryProductsDTO categoryProducts = Converter.toCategoryProductsDTO(category, uriInfo);
			GenericEntity<CategoryProductsDTO> entity = new GenericEntity<CategoryProductsDTO>(categoryProducts) {};
			
			Response build = Response.status(201).entity(entity).location(new URI(this.getGetURI(categoryProducts))).build();
			return build;
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@Path("/{category}")	
	public CategoryResource getCategory(@PathParam("category") String category) {
		CategoryResource categoryResource = context.getResource(CategoryResource.class);
		categoryResource.setRequest(request);
		categoryResource.setUriInfo(uriInfo);
		categoryResource.setCategory(category);
		return categoryResource;
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
