package web.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;


import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import domain.Oferta;

public class ReportService {
	
	public OutputStream createReport(Oferta oferta) {
		try {
			File file = new File("/reports/comanda.jrxml");
			InputStream input = new FileInputStream(file);
			System.out.println(file.getAbsolutePath());
			JasperDesign design = JRXmlLoader.load(input);
			JasperReport report = JasperCompileManager.compileReport(design);
			Map<String,Object> parameters = getOfertaParameters(oferta);
			JasperPrint print = JasperFillManager.fillReport(report, parameters);
			OutputStream os= new ByteArrayOutputStream();
			os.write(JasperExportManager.exportReportToPdf(print));
			return os;
			
		} catch (Exception e) {
			
		}
		
		return null;
	}

	private Map getOfertaParameters(Oferta oferta) {
		Map<String,String> m = new HashMap<String,String>();
		m.put("cid", String.valueOf(oferta.getId()));
		m.put("client",oferta.getClient());
		m.put("judet", oferta.getComanda().getShipAddressRegion());
		m.put("adresa", oferta.getComanda().getShipAddress());
		
		return m;
	}

}
