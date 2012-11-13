package com.indizen.cursoSpring.web.gui.importer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.mozilla.universalchardet.UniversalDetector;

import com.indizen.cursoSpring.web.util.Constants;

public final class CommonImportUtils {
	
	private CommonImportUtils(){};
	
	private static final Logger LOGGER = Logger.getLogger(CommonImportUtils.class);
	
	public static String getEncodingFile(String rutaFile) throws IOException {
		byte[] buf = new byte[Constants.BYTES_FILES];
		FileInputStream fis = null;
		try {
			fis = Constants.readFile(rutaFile);
		} catch (FileNotFoundException e) {
			LOGGER.error("No se encuentra el fichero " + rutaFile);
		}

	    // (1)
	    UniversalDetector detector = new UniversalDetector(null);

	    // (2)
	    int nread = fis.read(buf);
	    while (nread > 0 && !detector.isDone()) {
	      detector.handleData(buf, 0, nread);
	      nread = fis.read(buf);
	    }
	    // (3)
	    detector.dataEnd();

	    // (4)
	    String encoding = detector.getDetectedCharset();
	    if (encoding != null) {
	    	LOGGER.info("Formato de codificación = " + encoding);
	    } else {
	    	LOGGER.info("Formato de codificación no detectado. Se asigna UTF8 por defecto");
	    	encoding = Constants.UTF_8;
	    }

	    // (5)
	    fis.close();
	    detector.reset();
		return encoding;
	}
	
	public static String getEncodingFile(InputStream stream) throws IOException {
		byte[] buf = new byte[Constants.BYTES_FILES];

	    // (1)
	    UniversalDetector detector = new UniversalDetector(null);

	    // (2)
	    int nread = stream.read(buf);
	    while (nread > 0 && !detector.isDone()) {
	      detector.handleData(buf, 0, nread);
	      nread = stream.read(buf);
	    }
	    // (3)
	    detector.dataEnd();

	    // (4)
	    String encoding = detector.getDetectedCharset();
	    if (encoding != null) {
	    	LOGGER.info("Formato de codificación = " + encoding);
	    } else {
	    	LOGGER.info("Formato de codificación no detectado. Se asigna UTF8 por defecto");
	    	encoding = Constants.UTF_8;
	    }

	    // (5)
	    detector.reset();
		return encoding;
	}
	
	public static String removeUTF8BOM(String s) {
		String result = s;
        if (s.startsWith(Constants.UTF_8_BOM_BEGINING)) {
            result = s.substring(1);
        }
        return result;
    }
	
	public static String getNameFileCsv(String rutaFile) {
		Pattern pattern = Pattern.compile(Constants.PATTERN_FILE_NAME_CSV);
		Matcher m = pattern.matcher(rutaFile);
		
		String nameFile = "";
		if(m.find()){
			nameFile = m.group(0);
		}
		
		return nameFile;
	}
	
}
