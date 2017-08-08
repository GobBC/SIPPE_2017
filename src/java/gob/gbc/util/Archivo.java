/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.util;

/**
 *
 * @author jarguelles
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Archivo {

    public static final int TIPO_ERROR = 1;
    public static final int TIPO_TRANSACCION = 2;
    public static final int TIPO_DEBUG = 3;
    private String strNombreArchivo = "";
    private String strUbicacion = "";
    private String strInformacion = "";
    private String strServer = "";
    private String strSeparador = System.getProperty("file.separator");
    private String strSaltoLinea = System.getProperty("line.separator");

    public Archivo() throws IOException {
    }

    public String getStrNombreArchivo() {
        return strNombreArchivo;
    }

    public void setStrNombreArchivo(String strNombreArchivo) {
        this.strNombreArchivo = strNombreArchivo;
    }

    public String getStrUbicacion() {
        return strUbicacion;
    }

    public void setStrUbicacion(String strUbicacion) {
        this.strUbicacion = strUbicacion;
    }

    public String getStrInformacion() {
        return strInformacion;
    }

    public void setStrInformacion(String strInformacion) {
        this.strInformacion = strInformacion;
    }

    public void grabaArchivo() {
        try {
            FileOutputStream fos = new FileOutputStream(getStrUbicacion() + getStrNombreArchivo());
            Writer out = new OutputStreamWriter(fos, "ISO-8859-1");

            out.write(getStrInformacion());
            out.flush();
            out.close();
            out = null;//libero out

        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void addToZipFile(String sRuta,String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {

        File file = new File(sRuta +fileName);
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }
    public static void addToZipFile(String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {

        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }

    public static void delete(File file) throws IOException {

        if (file.isDirectory()) {

            //directory is empty, then delete it
            if (file.list().length == 0) {

                file.delete();

            } else {

                //list all the directory contents
                String files[] = file.list();

                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    delete(fileDelete);
                }

                //check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
                }
            }

        } else {
            //if file, then delete it
            file.delete();

        }
    }
}
