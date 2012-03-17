package ro.ubb.comenzi;

import java.rmi.RMISecurityManager;
import java.security.AllPermission;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Hello world!
 *
 */

public class MainOferta 
{
	

    public static void main( String[] args )
    {
//    	
//    	  RMISecurityManager rmiSecurityManager = new java.rmi.RMISecurityManager();
//          rmiSecurityManager.checkPermission(new AllPermission());
//          System.setSecurityManager(rmiSecurityManager);
          
        ApplicationContext context = new ClassPathXmlApplicationContext("springContext.xml");
      
      
        System.out.println("Comenzi configured OK.");
    }
}
