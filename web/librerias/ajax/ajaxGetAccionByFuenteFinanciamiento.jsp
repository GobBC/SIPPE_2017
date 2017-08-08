<%-- 
    Document   : ajaxGetAccionByFuenteFinanciamiento
    Created on : Sep 12, 2016, 2:56:20 PM
    Author     : ugarcia
--%>

<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.AccionFuenteFinanciamiento"%>
<%@page import="gob.gbc.aplicacion.MigracionRequerimientoBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    NumberFormat dFormat = NumberFormat.getInstance(Locale.US);
    MigracionRequerimientoBean migracionBean = null;
    List<AccionFuenteFinanciamiento> accionFFList = new ArrayList<AccionFuenteFinanciamiento>();
    
    String strResultado = new String();
    String tipoDependencia = new String();
    String fuenteFinanciamiento = new String();
    String fuente = new String();
    String fondo = new String();
    String recurso = new String();
    String ramo = new String();
    
    int year = 0;
    
    
    try{
        
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }

        if (Utilerias.existeParametro("ramo", request)) {
            ramo = request.getParameter("ramo");
        }
        if (Utilerias.existeParametro("fuenteFin", request)) {
            fuenteFinanciamiento = request.getParameter("fuenteFin");
            fuente = fuenteFinanciamiento.split("\\.")[0];
            fondo = fuenteFinanciamiento.split("\\.")[1];
            recurso = fuenteFinanciamiento.split("\\.")[2];
        }
        
        migracionBean = new MigracionRequerimientoBean(tipoDependencia);
        migracionBean.setStrServer(request.getHeader("host"));
        migracionBean.setStrUbicacion(getServletContext().getRealPath(""));
        migracionBean.resultSQLConecta(tipoDependencia);
        
        accionFFList = migracionBean.getResultSQLGetAccionesByFuenteFinanciamiento(year, ramo, fuente, fondo, recurso);
        if(accionFFList.size() > 0){
            for(AccionFuenteFinanciamiento accionFF : accionFFList){ 
                strResultado += "<tr>";
                strResultado += "<td><input type='checkbox' name='chk-cambio' class='chk-metaAccion' onchange='calculaTodalMetaAccionSeleccionada()'"
                        + "value='"+accionFF.getMeta()+"|"+accionFF.getAccion()+"|"+accionFF.getTotal()+"' checked/></td>";
                strResultado += "<td>"+accionFF.getMeta()+"</td>";
                strResultado += "<td>"+accionFF.getDescrMeta().toUpperCase()+"</td>";
                strResultado += "<td>"+accionFF.getAccion()+"</td>";
                strResultado += "<td>"+accionFF.getDescrAccion().toUpperCase()+"</td>";
                strResultado += "<td style='text-align:right'>$ "+dFormat.format(accionFF.getTotal())+"</td>";
                strResultado += "</tr>";
            }
        }else{
            strResultado = "-2";
        }
    }catch (Exception ex) {
        strResultado = "No se pudieron obtener las acciones a modificar";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
        strResultado = "-2";
    } finally {
        if (migracionBean != null) {
            migracionBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
