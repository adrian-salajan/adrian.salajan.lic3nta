package adrian.comenziadapter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import domain.OfertaService;

public class Main {

	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		 ApplicationContext context = new ClassPathXmlApplicationContext("springRMI.xml");
		 
		 OrderGatewayRMI rmi = (OrderGatewayRMI) context.getBean("orderGateway");
		 
			System.out.println( rmi.getAll());		
			System.out.println("----");
	}

}
