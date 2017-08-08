/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.util;

import gob.gbc.entidades.Archivo;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author joquintero
 */
public final class UtilUploads {

    private String strUbicacion;
    private String strServer;
    private String strDirectorioTemp;

    public String getStrUbicacion() {
        return strUbicacion;
    }

    public void setStrUbicacion(String strUbicacion) {
        this.strUbicacion = strUbicacion;
    }

    public String getStrServer() {
        return strServer;
    }

    public void setStrServer(String strServer) {
        this.strServer = strServer;
    }

    public String getStrDirectorioTemp() {
        return strDirectorioTemp;
    }

    public void setStrDirectorioTemp(String strDirectorioTemp) {
        this.strDirectorioTemp = strDirectorioTemp;
    }

    public DatosUpload getDatos(HttpServletRequest request) throws FileUploadException, Exception {
        DatosUpload datos = null;
        ArrayList archivos = null;
        Hashtable parametros = null;
        String valorUTF8 = "";
        List items = null;
        request.setCharacterEncoding("UTF-8");

        File tmpDir = new File(getStrDirectorioTemp());
        
        if (!tmpDir.isDirectory()) {
            tmpDir.mkdirs();
            //throw new Exception(getStrDirectorioTemp() + " no es un directorio");
        }

        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();

        fileItemFactory.setSizeThreshold(8 * 1024 * 1024); //8 MB

        fileItemFactory.setRepository(tmpDir);

        ServletFileUpload uploadHandler = new ServletFileUpload();
        uploadHandler.setHeaderEncoding("UTF-8");
        uploadHandler.setFileItemFactory(fileItemFactory);
        boolean bUpload = false;
        try {
            items = uploadHandler.parseRequest(request);
            bUpload = true;
        } catch (Throwable ex) {
        }
        if (items != null && bUpload) {
            datos = new DatosUpload();
            archivos = new ArrayList();
            parametros = new Hashtable(); 

            Iterator itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();

                if (item.isFormField()) {
                    valorUTF8 = new String(item.getString().getBytes("iso-8859-1"), "UTF-8");
                    parametros.put(item.getFieldName(), valorUTF8);
                } else {
                    archivos.add(new Archivo(item));
                }
            }

            if (parametros.size() > 0) {
                datos.setParametros(parametros);
            }

            if (archivos.size() > 0) {
                datos.setArchivos(archivos);
            }
        } else {
            datos = new DatosUpload();
            parametros = new Hashtable();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                if (request.getParameter(paramName) != null) {
                    parametros.put(paramName, request.getParameter(paramName));
                } else {
                    parametros.put(paramName, null);
                }
            }
            if (parametros.size() > 0) {
                datos.setParametros(parametros);
            }
        }
        return datos;
    }
}
