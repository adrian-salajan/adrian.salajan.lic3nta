package ro.ubb.comenzi;

import java.rmi.RMISecurityManager;
import java.security.AllPermission;

import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mysql.jdbc.Driver;

/**
 * Hello world!
 *
 */

public class MainOferta 
{
	
//	static {
//		try {
//			Class<com.mysql.jdbc.Driver> driver = (Class<Driver>) Class.forName("com.mysql.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("springContext.xml");
      
        RMISecurityManager rmiSecurityManager = new java.rmi.RMISecurityManager();
        rmiSecurityManager.checkPermission(new AllPermission());
        System.setSecurityManager(rmiSecurityManager);;
        System.out.println("configured OK.");
    }
}
