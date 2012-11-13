package com.indizen.cursoSpring.web.gui.exporter;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.xml.sax.SAXException;

import com.indizen.cursoSpring.servicio.model.EntityBase;
import com.indizen.cursoSpring.servicio.util.BeanProxy;
import com.indizen.cursoSpring.web.util.BeanIntrospector;
import com.indizen.cursoSpring.web.util.Constants;
import com.indizen.cursoSpring.web.util.WordsConverter;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;

public abstract class Exporter<T extends EntityBase> implements Serializable {
	private static final long serialVersionUID = 1L;
	protected static final Logger LOGGER = Logger.getLogger(Exporter.class);
	protected static WordsConverter converter;
	protected BeanProxy proxy;
	protected Map<String,PropertyDescriptor> headers;
	
	public void init(Class<T> entityBase) {
		try
		{
			proxy = new BeanProxy(entityBase);
			headers= BeanIntrospector.getProperties(entityBase);
		}catch (IntrospectionException e) {
			LOGGER.error("Error al crear el proxy para exportar "+e.getMessage());
		}
	}
	
	public HttpServletResponse getResponse(){
		FacesContext context = FacesContext.getCurrentInstance();
		return (HttpServletResponse) context.getExternalContext().getResponse();
	}
	
	public FacesContext getFacesContext(){
		return FacesContext.getCurrentInstance();
	}
	
	public void setHeaderResponse(HttpServletResponse response, String contentType, String fileName){
		response.setContentType(contentType);			
		response.setHeader("Expires", "0");
        response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setHeader("Content-disposition", "attachment;filename="+ fileName);
	}
	
	/**CSV**/
	
	public abstract void csvExporter(List<T> results) throws IOException, IllegalAccessException, NoSuchMethodException, SAXException, IntrospectionException, NoSuchFieldException, InvocationTargetException;
	
	protected void writeHeaderCsv(Writer writer) throws IntrospectionException,IOException{
		String header="";
		int i = 0;
		for (Entry<String, PropertyDescriptor> entry : headers.entrySet()) {
			header=entry.getValue().getName();
			header=converter.desCamelCase(header);
			writer.append(Constants.DELIMITER_CSV+header+Constants.DELIMITER_CSV);
			if (i<(headers.size()-1))
			{
				writer.append(Constants.SEPARATOR);
			}
			else
			{
				writer.append(Constants.RETURN);
			}
			i++;
		}
	}
	
	/**PDF**/
	
	public abstract void pdfExporter(List<T> results) throws DocumentException, IOException, IntrospectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException;
	
	protected void writeHeaderPdf(PdfPTable pdfTable, Font font) throws IntrospectionException,IOException{
		String header="";
		for (Entry<String, PropertyDescriptor> entry : headers.entrySet()) {
			header=entry.getValue().getName();
			header=converter.desCamelCase(header);
			pdfTable.addCell(new Paragraph(header, font));
		}
	}
	
	/**XML**/
	
	public abstract void xmlExporter(List<T> results) throws DocumentException, IOException, IntrospectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException;
	
	
	/** XLS **/
	
	public abstract void xlsExporter(List<T> results) throws DocumentException, IOException, IntrospectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException;
	
	protected void writeHeaderXls(Sheet sheet, int rowIndex) throws IntrospectionException,IOException{
		String header="";
		Row rowHeader = sheet.createRow(rowIndex);
		int i = 0;
		for (Entry<String, PropertyDescriptor> entry : headers.entrySet()) {
			header=entry.getValue().getName();
			header=converter.desCamelCase(header);
			Cell cell = rowHeader.createCell(i);
			cell.setCellValue(new HSSFRichTextString(header));
			i++;
		}
	}
	
}
