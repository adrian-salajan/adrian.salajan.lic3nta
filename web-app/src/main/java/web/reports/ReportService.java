package web.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import domain.Oferta;
import domain.Product;

public class ReportService {
	
//	public OutputStream createReport(Oferta oferta) {
//		try {
//			File file = new File("/reports/comanda.jrxml");
//			InputStream input = new FileInputStream(file);
//			System.out.println(file.getAbsolutePath());
//			JasperDesign design = JRXmlLoader.load(input);
//			JasperReport report = JasperCompileManager.compileReport(design);
//			Map<String,Object> parameters = getOfertaParameters(oferta);
//			JasperPrint print = JasperFillManager.fillReport(report, parameters);
//			OutputStream os= new ByteArrayOutputStream();
//			os.write(JasperExportManager.exportReportToPdf(print));
//			return os;
//			
//		} catch (Exception e) {
//			
//		}
//		
//		return null;
//	}

	public Map<String,Object> getOfertaParameters(Oferta oferta) {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("cid", String.valueOf(oferta.getId()));
		m.put("client",oferta.getClient());
		m.put("judet", oferta.getComanda().getShipAddressRegion());
		m.put("adresa", oferta.getComanda().getShipAddress());
		
		List<ProductReport> items = new ArrayList<ProductReport>();
		long total = 0;
		long t = 0;
		for (Product p : oferta.getItems()) {
			ProductReport pr = new ProductReport();
			pr.setId(p.getId());
			pr.setName(p.getName());
			pr.setDesc(p.getDetails());
			pr.setPrice(String.valueOf(p.getFinalPrice()));
			pr.setQty(String.valueOf(p.getQuantity()));
			t = p.getQuantity() * p.getFinalPrice();
			total += t;
			pr.setQop(String.valueOf(t));
			items.add(pr);
		}
		JRDataSource data = new JRBeanCollectionDataSource(items); 
		m.put("data", data);
		m.put("total", total);
		return m;
	}
	

}
