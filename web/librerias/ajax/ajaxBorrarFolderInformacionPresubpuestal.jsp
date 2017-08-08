<%-- 
    Document   : ajaxGetProgramasByRamo
    Created on : Mar 20, 2015, 4:08:07 PM
    Author     : jarguelles
--%>

<%@page import="java.util.Date"%>
<%@page import="org.apache.tools.ant.types.Path"%>
<%@page import="org.apache.tools.ant.types.Path"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.io.File"%>
<%@page import="gob.gbc.util.Archivo"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>


<%@page import="gob.gbc.aplicacion.EstructuraPresupuestalBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String strResultado = new String();
    String pathLogico = "";
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    Archivo archivo = new Archivo();

    try {

        pathLogico = application.getRealPath("/") + "temp";
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        File directorio = new File(pathLogico);
        String[] listaDirectorio = directorio.list();
        if (listaDirectorio.length > 0) {
            for (int x = 0; x < listaDirectorio.length; x++) {
                File fDatos = new File(pathLogico + File.separatorChar + listaDirectorio[x]);
                Date d = new Date(fDatos.lastModified());
                Date d2 = calendar.getTime();
                if (d.before(d2)) {
                    File directory = new File(pathLogico + File.separatorChar + listaDirectorio[x]);
                    archivo.delete(directory);
                }
            }
        }

    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        out.print(strResultado);
    }
%>