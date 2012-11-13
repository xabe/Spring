package com.indizen.cursoSpring.web.gui.rest;


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.xml.sax.SAXException;

import com.indizen.cursoSpring.servicio.model.rest.Rest;
import com.indizen.cursoSpring.web.gui.exporter.Exporter;
import com.indizen.cursoSpring.web.util.Constants;
import com.indizen.cursoSpring.web.util.WordsConverter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class RestExporter extends Exporter<Rest>{
	private static final long serialVersionUID = 1L;  
	
	static{
		converter = WordsConverter.getInstance();
	} 
	
	public RestExporter() {
		init(Rest.class);
	}
	
	/** CSV **/

	public void csvExporter(List<Rest> results) throws IOException, IllegalAccessException, NoSuchMethodException, SAXException, IntrospectionException, NoSuchFieldException, InvocationTargetException{
		HttpServletResponse response = getResponse();
		
		String fileName = "Rest.csv";
		
		setHeaderResponse(response, Constants.CONTENT_TYPE_CSV, fileName);
		
		PrintWriter writer = response.getWriter();
		
		writer.append(Constants.UTF_8_BOM_BEGINING);

		writeHeaderCsv(writer);
		
		if(results != null)
		{
			String header="";
			int j = 0;
			for (int i=0; i<results.size(); i++){
				proxy.setBean(results.get(i));
				j = 0;
				for (Entry<String, PropertyDescriptor> entry : headers.entrySet()) {
					header=entry.getValue().getName();
					Object value = proxy.get(header);
					if(value != null)
					{
						if(value instanceof Date)
						{
							writer.append(Constants.DELIMITER_CSV+Constants.getDateString((Date)value)+Constants.DELIMITER_CSV);
						}
						else
						{
							writer.append(Constants.DELIMITER_CSV+value.toString()+Constants.DELIMITER_CSV);
						}
					}
					else
					{
						writer.append(Constants.DELIMITER_CSV+Constants.EMPTY+Constants.DELIMITER_CSV);
					}					
					if (j<headers.size()-1){
						writer.append(Constants.SEPARATOR);
					}
					j++;
				}
				writer.append(Constants.RETURN);
			}
			writer.flush();
			writer.close();
		}			
        
		 getFacesContext().responseComplete();   
	}
	
	
	/** PDF **/
	
	public void pdfExporter(List<Rest> results) throws DocumentException, IOException, IntrospectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException{
		HttpServletResponse response = getResponse();
		
		String fileName = "Rest.pdf";
		
		Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
		
        document.open();  
        document.setPageSize(PageSize.A4);  
		
		PdfPTable pdfTable = new PdfPTable(headers.size());
    	Font font = FontFactory.getFont(FontFactory.TIMES, Constants.UTF_8);
    	Font headerFont = FontFactory.getFont(FontFactory.TIMES, Constants.UTF_8, Font.DEFAULTSIZE, Font.BOLD);    	
    	
    	writeHeaderPdf(pdfTable, headerFont);
    	
    	String header="";
		for (int i=0; i<results.size(); i++){
			proxy.setBean(results.get(i));
			for (Entry<String, PropertyDescriptor> entry : headers.entrySet()) {
				header=entry.getValue().getName();
				Object value = proxy.get(header);
				if(value != null)
				{
					if(value instanceof Date)
					{
						pdfTable.addCell(new Paragraph(Constants.getDateString((Date)value), font));
					}
					else
					{
						pdfTable.addCell(new Paragraph(value.toString(), font));
					}
				}
				else
				{
					pdfTable.addCell(new Paragraph(Constants.EMPTY, font));
				}					
			}			
		}
		
		document.add(pdfTable);
		
		document.close();
		
        setHeaderResponse(response, Constants.CONTENT_TYPE_PDF, fileName);
        response.setContentLength(baos.size());
        
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
        getFacesContext().responseComplete();   
	}
	
	/** XML **/
	
	public void xmlExporter(List<Rest> results) throws DocumentException, IOException, IntrospectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException{
		HttpServletResponse response = getResponse();
		
		String fileName = "Rest.xml";
		setHeaderResponse(response, Constants.CONTENT_TYPE_XML, fileName);
		
		OutputStream os = response.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os, Constants.UTF_8);
		
		PrintWriter writer = new PrintWriter(osw);	
		
		writer.write("<?xml version=\"1.0\"?>\n");
    	writer.write("<Rests>\n");
    	 
    	String header="";
    	
    	for (int i=0; i<results.size(); i++){
             writer.write("\t<rest>\n");
             proxy.setBean(results.get(i));
 			 for (Entry<String, PropertyDescriptor> entry : headers.entrySet()) {
 				header=entry.getValue().getName();
 				Object value = proxy.get(header);
 				String tag = header.toLowerCase();
 				writer.write("\t\t<" + tag + ">");
 				if(value != null)
 				{
 					if(value instanceof Date)
 					{
 						writer.write(Constants.getDateString((Date)value));
 					}
 					else
 					{
 						writer.write(value.toString());
 					}
 				}
 				else
 				{
 					writer.write(Constants.EMPTY);
 				}
 				writer.write("</" + tag + ">\n");
 			}
            writer.write("\t</rest>\n");
        }
		
    	writer.write("<Rests>\n");
    	
    	writer.flush();
		writer.close();

		 getFacesContext().responseComplete();   
	}
	
	/** XLS **/
	
	public void xlsExporter(List<Rest> results) throws DocumentException, IOException, IntrospectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException{
		HttpServletResponse response = getResponse();
		
		String fileName = "Rest.xls";
		
		Workbook wb = new HSSFWorkbook();
    	Sheet sheet = wb.createSheet();
		  	
    	
    	writeHeaderXls(sheet, 0);
    	
    	String header="";
    	int sheetRowIndex = 1;
    	Row row;
    	int j = 0;
		for (int i=0; i<results.size(); i++){
			
			proxy.setBean(results.get(i));			
			row = sheet.createRow(sheetRowIndex++);
			j = 0;
			for (Entry<String, PropertyDescriptor> entry : headers.entrySet()) {
				header=entry.getValue().getName();
				Object value = proxy.get(header);
				Cell cell = row.createCell(j);
				if(value != null)
				{
					if(value instanceof Date)
					{
						 cell.setCellValue(new HSSFRichTextString(Constants.getDateString((Date)value)));
					}
					else
					{
						cell.setCellValue(new HSSFRichTextString(value.toString()));
					}
				}
				else
				{
					cell.setCellValue(new HSSFRichTextString(Constants.EMPTY));
				}
				j++;
			}			
		}
		
		setHeaderResponse(response, Constants.CONTENT_TYPE_XLS, fileName);

        wb.write(response.getOutputStream());
        getFacesContext().responseComplete();    
	}

}
