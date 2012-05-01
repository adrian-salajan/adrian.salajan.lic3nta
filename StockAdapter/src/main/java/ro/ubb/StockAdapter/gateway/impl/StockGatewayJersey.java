package ro.ubb.StockAdapter.gateway.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.ws.rs.core.MediaType;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import ro.ubb.StockAdapter.client.ClientFactory;
import ro.ubb.StockAdapter.dto.CategoryDTO;
import ro.ubb.StockAdapter.dto.CategoryProductsDTO;
import ro.ubb.StockAdapter.dto.ProductDTO;
import ro.ubb.StockAdapter.gateway.GatewayHelper;
import ro.ubb.StockAdapter.gateway.StockGateway;
import ro.ubb.StockAdapter.gateway.exceptions.ExceptionMapper;
import ro.ubb.StockAdapter.gateway.exceptions.StockGatewayException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource.Builder;

public class StockGatewayJersey implements StockGateway {
	
	private Client client = ClientFactory.getClient();
	private String serviceUri = "http://localhost:8081/stock/";
	private String baseUri;
	
	@Override
	public Collection<CategoryDTO> getCategories()  throws StockGatewayException {
		try {
			Builder accept = client.resource(this.getRootResourceURI()).accept("application/xml");
			GenericType<Collection<CategoryDTO>> entity = new  GenericType<Collection<CategoryDTO>>() {};
			Collection<CategoryDTO> categories =  accept.get(entity);
			return categories;
		} catch (UniformInterfaceException e) {
			
			throw ExceptionMapper.map(e);
			
		} 
		
	}
	 
	@Override
	public void createCategory(CategoryDTO newCategory)  throws StockGatewayException {
		try {
			Builder accept = client.resource(this.getRootResourceURI()).accept(MediaType.APPLICATION_XML_TYPE).type(MediaType.APPLICATION_XML_TYPE);
			ClientResponse response = accept.put(ClientResponse.class, newCategory);
			
			CategoryProductsDTO category = response.getEntity(CategoryProductsDTO.class);
			newCategory.id = category.id;
			newCategory.name = category.name;
			newCategory.links = category.links;
		} catch (UniformInterfaceException e) {
			throw ExceptionMapper.map(e);
		}
		
	}




	@Override
	public void deleteCategory(CategoryDTO category)  throws StockGatewayException {
		try {
			Builder accept = client.resource(GatewayHelper.getDeleteURI(category)).accept("application/xml");
			ClientResponse response = accept.delete(ClientResponse.class);
		} catch (UniformInterfaceException e) {
			throw ExceptionMapper.map(e, category.id);
		}
		
	}





	@Override
	public void updateCategory(CategoryDTO category)  throws StockGatewayException {
		try {
			Builder accept = client.resource(GatewayHelper.getUpdateURI(category)).accept("application/xml").type("application/xml");
			ClientResponse response = accept.post(ClientResponse.class, category);
			CategoryDTO updatedCategory = response.getEntity(CategoryDTO.class);
			category.name = updatedCategory.name;
			category.links = updatedCategory.links;
		} catch (UniformInterfaceException e) {
			throw ExceptionMapper.map(e, category.id);
		}
		
		
	}





	@Override
	public CategoryProductsDTO getCategoryProducts(CategoryDTO category)  throws StockGatewayException {
		try {
			Builder accept = client.resource(GatewayHelper.getGetURI(category)).accept("application/xml").type("application/xml");
			ClientResponse response = accept.get(ClientResponse.class);
			return response.getEntity(CategoryProductsDTO.class);
		} catch (UniformInterfaceException e) {
			throw ExceptionMapper.map(e, category.id);
		}
	}





	@Override
	public Collection<ProductDTO> getProductsInCategory(CategoryDTO category)  throws StockGatewayException {
		try {
			Builder accept = client.resource(GatewayHelper.getGetURI(category)).accept("application/xml").type("application/xml");
			ClientResponse response = accept.get(ClientResponse.class);
			CategoryProductsDTO categoryProducts = response.getEntity(CategoryProductsDTO.class);
			return categoryProducts.products;
		} catch (UniformInterfaceException e) {
			throw ExceptionMapper.map(e, category.id);
		}
	}





	@Override
	public void insertProduct(CategoryDTO category, ProductDTO product)  throws StockGatewayException {
		try {
			Builder accept = client.resource(GatewayHelper.getInsertInURI(category)).accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_XML);
			ClientResponse clientResponse = accept.put(ClientResponse.class, product);
		
			ProductDTO newProduct = clientResponse.getEntity(ProductDTO.class);
			product.id = newProduct.id;
			product.description = newProduct.description;
			product.links = newProduct.links;
			product.name = newProduct.name;
			product.price = newProduct.price;
			product.properties = newProduct.properties;
			product.stock = newProduct.stock;
			product.categoryId = newProduct.categoryId;
		} catch (UniformInterfaceException e) {
			throw ExceptionMapper.map(e, category.id);
		}
		
	}





	@Override
	public void deleteProduct(ProductDTO product)  throws StockGatewayException {
		try {
			Builder accept = client.resource(GatewayHelper.getDeleteURI(product)).accept("application/xml");
			ClientResponse response = accept.delete(ClientResponse.class);
		} catch (UniformInterfaceException e) {
			throw ExceptionMapper.map(e,product.id);
		}
		
	}





	@Override
	public void updateProduct(ProductDTO product)  throws StockGatewayException {
		try {
			Builder accept = client.resource(GatewayHelper.getUpdateURI(product)).accept("application/xml").type("application/xml");
			ClientResponse response = accept.post(ClientResponse.class, product);
			
			ProductDTO newProduct = response.getEntity(ProductDTO.class);
			product.id = newProduct.id;
			product.description = newProduct.description;
			product.links = newProduct.links;
			product.name = newProduct.name;
			product.price = newProduct.price;
			product.properties = newProduct.properties;
			product.stock = newProduct.stock;
		} catch (UniformInterfaceException e) {
			throw ExceptionMapper.map(e, product.id);
		}
		
	}
	
	
	private String getRootResourceURI() {
		if (baseUri == null) {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setNamespaceAware(true); // never forget this!
				DocumentBuilder builder;
				try {
					builder = factory.newDocumentBuilder();
					Document doc = builder.parse(serviceUri + "application.wadl");
					XPathFactory xfactory = XPathFactory.newInstance();
					XPath xpath = xfactory.newXPath();
					xpath.setNamespaceContext(new NamespaceContext() {
						
						@Override
						public Iterator getPrefixes(String arg0) {
							 throw new UnsupportedOperationException();
						}
						
						@Override
						public String getPrefix(String arg0) {
							 throw new UnsupportedOperationException();
						}
						
						@Override
						public String getNamespaceURI(String prefix) {
							  if(prefix.equals("wadl"))
					                return "http://research.sun.com/wadl/2006/10";
					            else
					                return null;
						}
					});
					XPathExpression expression = xpath.compile("/wadl:application/wadl:resources/wadl:resource/@path");
					this.baseUri = (String) expression.evaluate(doc, XPathConstants.STRING);
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XPathExpressionException e) {
					e.printStackTrace();
				}
		}
		return serviceUri + baseUri;
		
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public CategoryProductsDTO getCategoryProducts(Long id)  throws StockGatewayException {
		CategoryProductsDTO category = null;
		try {
			Builder accept = client.resource(this.getRootResourceURI() + "/" + id).accept("application/xml").type("application/xml");
			ClientResponse response = accept.get(ClientResponse.class);
			category = response.getEntity(CategoryProductsDTO.class);
			return category;
		} catch (UniformInterfaceException e) {
			throw ExceptionMapper.map(e, id);
		}
	}

	@Override
	public ProductDTO getProduct(long productId, long categoryId) throws StockGatewayException {
		Builder accept = client.resource(this.getRootResourceURI() + "/" + categoryId + "/product/" + productId).accept("application/xml").type("application/xml");
		ClientResponse response = accept.get(ClientResponse.class);
		ProductDTO dto = response.getEntity(ProductDTO.class);
		return dto;
	}


}
