package com.indizen.cursoSpring.web.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * Useful file system level utilities.
 * 
 * @author BalusC
 * @link http://balusc.blogspot.com/2007/09/FileUtil.html
 */
public final class FileUtil {
	private static final Logger LOGGER = Logger.getLogger(FileUtil.class);
    // Init ---------------------------------------------------------------------------------------

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    // Constructors -------------------------------------------------------------------------------

    private FileUtil() {
        // Utility class, hide constructor.
    }

    // Writers ------------------------------------------------------------------------------------

    /**
     * Write byte array to file. If file already exists, it will be overwritten.
     * @param file The file where the given byte array have to be written to.
     * @param bytes The byte array which have to be written to the given file.
     * @throws IOException If writing file fails.
     */
    public static void write(File file, byte[] bytes) throws IOException {
        write(file, new ByteArrayInputStream(bytes), false);
    }

    /**
     * Write byte array to file with option to append to file or not. If not, then any existing
     * file will be overwritten.
     * @param file The file where the given byte array have to be written to.
     * @param bytes The byte array which have to be written to the given file.
     * @param append Append to file?
     * @throws IOException If writing file fails.
     */
    public static void write(File file, byte[] bytes, boolean append) throws IOException {
        write(file, new ByteArrayInputStream(bytes), append);
    }

    /**
     * Write byte inputstream to file. If file already exists, it will be overwritten.It's highly
     * recommended to feed the inputstream as BufferedInputStream or ByteArrayInputStream as those
     * are been automatically buffered.
     * @param file The file where the given byte inputstream have to be written to.
     * @param input The byte inputstream which have to be written to the given file.
     * @throws IOException If writing file fails.
     */
    public static void write(File file, InputStream input) throws IOException {
        write(file, input, false);
    }

    /**
     * Write byte inputstream to file with option to append to file or not. If not, then any
     * existing file will be overwritten. It's highly recommended to feed the inputstream as
     * BufferedInputStream or ByteArrayInputStream as those are been automatically buffered.
     * @param file The file where the given byte inputstream have to be written to.
     * @param input The byte inputstream which have to be written to the given file.
     * @param append Append to file?
     * @throws IOException If writing file fails.
     */
    public static void write(File file, InputStream input, boolean append) throws IOException {
        mkdirs(file);
        BufferedOutputStream output = null;

        try {
            output = new BufferedOutputStream(new FileOutputStream(file, append));
            int data = -1;
            while ((data = input.read()) != -1) {
                output.write(data);
            }
        } finally {
            close(input, file);
            close(output, file);
        }
    }

    /**
     * Write character array to file. If file already exists, it will be overwritten.
     * @param file The file where the given character array have to be written to.
     * @param chars The character array which have to be written to the given file.
     * @throws IOException If writing file fails.
     */
    public static void write(File file, char[] chars) throws IOException {
        write(file, new CharArrayReader(chars), false);
    }

    /**
     * Write character array to file with option to append to file or not. If not, then any
     * existing file will be overwritten.
     * @param file The file where the given character array have to be written to.
     * @param chars The character array which have to be written to the given file.
     * @param append Append to file?
     * @throws IOException If writing file fails.
     */
    public static void write(File file, char[] chars, boolean append) throws IOException {
        write(file, new CharArrayReader(chars), append);
    }

    /**
     * Write string value to file. If file already exists, it will be overwritten.
     * @param file The file where the given string value have to be written to.
     * @param string The string value which have to be written to the given file.
     * @throws IOException If writing file fails.
     */
    public static void write(File file, String string) throws IOException {
        write(file, new CharArrayReader(string.toCharArray()), false);
    }

    /**
     * Write string value to file with option to append to file or not. If not, then any existing
     * file will be overwritten.
     * @param file The file where the given string value have to be written to.
     * @param string The string value which have to be written to the given file.
     * @param append Append to file?
     * @throws IOException If writing file fails.
     */
    public static void write(File file, String string, boolean append) throws IOException {
        write(file, new CharArrayReader(string.toCharArray()), append);
    }

    /**
     * Write character reader to file. If file already exists, it will be overwritten. It's highly
     * recommended to feed the reader as BufferedReader or CharArrayReader as those are been
     * automatically buffered.
     * @param file The file where the given character reader have to be written to.
     * @param reader The character reader which have to be written to the given file.
     * @throws IOException If writing file fails.
     */
    public static void write(File file, Reader reader) throws IOException {
        write(file, reader, false);
    }

    /**
     * Write character reader to file with option to append to file or not. If not, then any
     * existing file will be overwritten. It's highly recommended to feed the reader as
     * BufferedReader or CharArrayReader as those are been automatically buffered.
     * @param file The file where the given character reader have to be written to.
     * @param reader The character reader which have to be written to the given file.
     * @param append Append to file?
     * @throws IOException If writing file fails.
     */
    public static void write(File file, Reader reader, boolean append) throws IOException {
        mkdirs(file);
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(file, append));
            int data = -1;
            while ((data = reader.read()) != -1) {
                writer.write(data);
            }
        } finally {
            close(reader, file);
            close(writer, file);
        }
    }

    /**
     * Write list of String records to file. If file already exists, it will be overwritten.
     * @param file The file where the given character reader have to be written to.
     * @param records The list of String records which have to be written to the given file.
     * @throws IOException If writing file fails.
     */
    public static void write(File file, List<String> records) throws IOException {
        write(file, records, false);
    }

    /**
     * Write list of String records to file with option to append to file or not. If not, then any
     * existing file will be overwritten.
     * @param file The file where the given character reader have to be written to.
     * @param records The list of String records which have to be written to the given file.
     * @param append Append to file?
     * @throws IOException If writing file fails.
     */
    public static void write(File file, List<String> records, boolean append) throws IOException {
        mkdirs(file);
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(file, append));
            for (String record : records) {
                writer.write(record);
                writer.write(LINE_SEPARATOR);
            }
        } finally {
            close(writer, file);
        }
    }

    // Readers ------------------------------------------------------------------------------------

    /**
     * Read byte array from file. Take care with big files, this would be memory hogging, rather
     * use readStream() instead.
     * @param file The file to read the byte array from.
     * @return The byte array with the file contents.
     * @throws IOException If reading file fails.
     */
    public static byte[] readBytes(File file) throws IOException {
        BufferedInputStream stream = (BufferedInputStream) readStream(file);
        byte[] bytes = new byte[stream.available()];
        int value = stream.read(bytes);
        if(value == -1)
        {
        	LOGGER.debug("Error al leer los bytes");
        }
        return bytes;
    }

    /**
     * Read byte stream from file.
     * @param file The file to read the byte stream from.
     * @return The byte stream with the file contents (actually: BufferedInputStream).
     * @throws IOException If reading file fails.
     */
    public static InputStream readStream(File file) throws IOException {
        return new BufferedInputStream(new FileInputStream(file));
    }

    /**
     * Read character array from file. Take care with big files, this would be memory hogging,
     * rather use readReader() instead.
     * @param file The file to read the character array from.
     * @return The character array with the file contents.
     * @throws IOException If reading file fails.
     */
    public static char[] readChars(File file) throws IOException {
        BufferedReader reader = (BufferedReader) readReader(file);
        char[] chars = new char[(int) file.length()];
        int value = reader.read(chars);
        if(value == -1)
        {
        	LOGGER.debug("Error al leer los bytes");
        }
        return chars;
    }

    /**
     * Read string value from file. Take care with big files, this would be memory hogging, rather
     * use readReader() instead.
     * @param file The file to read the string value from.
     * @return The string value with the file contents.
     * @throws IOException If reading file fails.
     */
    public static String readString(File file) throws IOException {
        return new String(readChars(file));
    }

    /**
     * Read character reader from file.
     * @param file The file to read the character reader from.
     * @return The character reader with the file contents (actually: BufferedReader).
     * @throws IOException If reading file fails.
     */
    public static Reader readReader(File file) throws IOException {
        return new BufferedReader(new FileReader(file));
    }

    /**
     * Read list of String records from file.
     * @param file The file to read the character writer from.
     * @return A list of String records which represents lines of the file contents.
     * @throws IOException If reading file fails.
     */
    public static List<String> readRecords(File file) throws IOException {
        BufferedReader reader = (BufferedReader) readReader(file);
        List<String> records = new ArrayList<String>();
        String record = null;

        try {
            while ((record = reader.readLine()) != null) {
                records.add(record);
            }
        } finally {
            close(reader, file);
        }

        return records;
    }

    // Copiers ------------------------------------------------------------------------------------

    /**
     * Copy file. Any existing file at the destination will be overwritten.
     * @param source The file to read the contents from.
     * @param destination The file to write the contents to.
     * @throws IOException If copying file fails.
     */
    public static void copy(File source, File destination) throws IOException {
        copy(source, destination, true);
    }

    /**
     * Copy file with the option to overwrite any existing file at the destination.
     * @param source The file to read the contents from.
     * @param destination The file to write the contents to.
     * @param overwrite Set whether to overwrite any existing file at the destination.
     * @throws IOException If the destination file already exists while <tt>overwrite</tt> is set
     * to false, or if copying file fails.
     */
    public static void copy(File source, File destination, boolean overwrite) throws IOException {
        if (destination.exists() && !overwrite) {
            throw new IOException(
                "Copying file " + source.getPath() + " to " + destination.getPath() + " error."
                    + " The destination file already exists.");
        }

        mkdirs(destination);
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            input = new BufferedInputStream(new FileInputStream(source));
            output = new BufferedOutputStream(new FileOutputStream(destination));
            int data = -1;
            while ((data = input.read()) != -1) {
                output.write(data);
            }
        } finally {
            close(input, source);
            close(output, destination);
        }
    }

    // Movers -------------------------------------------------------------------------------------

    /**
     * Move (rename) file. Any existing file at the destination will be overwritten.
     * @param source The file to be moved.
     * @param destination The new destination of the file.
     * @throws IOException If moving file fails.
     */
    public static void move(File source, File destination) throws IOException {
        move(source, destination, true);
    }

    /**
     * Move (rename) file with the option to overwrite any existing file at the destination.
     * @param source The file to be moved.
     * @param destination The new destination of the file.
     * @param overwrite Set whether to overwrite any existing file at the destination.
     * @throws IOException If the destination file already exists while <tt>overwrite</tt> is set
     * to false, or if moving file fails.
     */
    public static void move(File source, File destination, boolean overwrite) throws IOException {
        if (destination.exists()) {
            if (overwrite) {
                delete(destination);
            } else {
                throw new IOException(
                    "Moving file " + source.getPath() + " to " + destination.getPath() + " error."
                        + " The destination file already exists.");
            }
        }

        mkdirs(destination);

        if (!source.renameTo(destination)) {
            throw new IOException(
                "Moving file " + source.getPath() + " to " + destination.getPath() + " failed.");
        }
    }

    // Filenames ----------------------------------------------------------------------------------

    /**
     * Trim the eventual file path from the given file name. Anything before the last occurred "/"
     * and "\" will be trimmed, including the slash.
     * @param fileName The file name to trim the file path from.
     * @return The file name with the file path trimmed.
     */
    public static String trimFilePath(String fileName) {
        return fileName
            .substring(fileName.lastIndexOf("/") + 1)
            .substring(fileName.lastIndexOf("\\") + 1);
    }

    /**
     * Generate unique file based on the given path and name. If the file exists, then it will
     * add "[i]" to the file name as long as the file exists. The value of i can be between
     * 0 and 2147483647 (the value of Integer.MAX_VALUE).
     * @param filePath The path of the unique file.
     * @param fileName The name of the unique file.
     * @return The unique file.
     * @throws IOException If unique file cannot be generated, this can be caused if all file
     * names are already in use. You may consider another filename instead.
     */
    public static File uniqueFile(File filePath, String fileName) throws IOException {
        File file = new File(filePath, fileName);
        
        if (file.exists()) {

            // Split filename and add braces, e.g. "name.ext" --> "name[", "].ext".
            String prefix;
            String suffix;
            int dotIndex = fileName.lastIndexOf(".");

            if (dotIndex > -1) {
                prefix = fileName.substring(0, dotIndex) + "[";
                suffix = "]" + fileName.substring(dotIndex);
            } else {
                prefix = fileName + "[";
                suffix = "]";
            }

            int count = 0;

            // Add counter to filename as long as file exists.
            while (file.exists()) {
                if (count < 0) { // int++ restarts at -2147483648 after 2147483647.
                    throw new IOException("No unique filename available for " + fileName 
                        + " in path " + filePath.getPath() + ".");
                }

                // Glue counter between prefix and suffix, e.g. "name[" + count + "].ext".
                file = new File(filePath, prefix + (count++) + suffix);
            }
        }

        return file;
    }

    // Helpers ------------------------------------------------------------------------------------

    /**
     * Check and create missing parent directories for the given file.
     * @param file The file to check and create the missing parent directories for.
     * @throws IOException If the given file is actually not a file or if creating parent 
     * directories fails.
     */
    private static void mkdirs(File file) throws IOException {
        if (file.exists() && !file.isFile()) {
            throw new IOException("File " + file.getPath() + " is actually not a file.");
        }
        File parentFile = file.getParentFile();
        if (!parentFile.exists() && !parentFile.mkdirs()) {
            throw new IOException("Creating directories " + parentFile.getPath() + " failed.");
        }
    }

    /**
     * Close the given I/O resource of the given file.
     * @param resource The I/O resource to be closed.
     * @param file The I/O resource's subject.
     */
    private static void close(Closeable resource, File file) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
            	LOGGER.error("Closing file " + file.getPath() + " failed.");
                LOGGER.error(e.getMessage());
            }
        }
    }
    
    private static void delete(File file) throws IOException{
    	if(file != null)
    	{
    		boolean result = file.delete();
    		if(!result)
    		{
    			LOGGER.error("Error to delete file : "+file.getName());
    		}
    	}
    }
    
}