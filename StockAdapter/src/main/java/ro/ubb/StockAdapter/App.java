package ro.ubb.StockAdapter;

import ro.ubb.StockAdapter.client.ClientFactory;
import ro.ubb.StockAdapter.dto.CategoryDTO;
import ro.ubb.StockAdapter.gateway.StockGateway;
import ro.ubb.StockAdapter.gateway.exceptions.StockGatewayException;
import ro.ubb.StockAdapter.gateway.impl.StockGatewayJersey;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        StockGateway gateway = new StockGatewayJersey();
        
        
        CategoryDTO clienti = new CategoryDTO();
        clienti.name = "clienti3";
        
        try {
			gateway.createCategory(clienti);
		} catch (StockGatewayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("---------------------------");
        System.out.println(clienti.id);
        System.out.println(clienti.name);
    }
}
