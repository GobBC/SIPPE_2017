<%-- 
    Document   : ajaxSaveExcelInTable
    Created on : Aug 21, 2015, 9:31:48 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.CargaProyeccionBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Proyeccion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String tipoDependencia = new String();
    List<String> yearList = new ArrayList<String>();
    boolean result = false;
    String strResultado = new String();
    List<Proyeccion> proyeccionList = new ArrayList<Proyeccion>();
    CargaProyeccionBean cargaBean = null;
    try {
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("resultadoCarga") != null) {
            proyeccionList = (List<Proyeccion>) session.getAttribute("resultadoCarga");
            session.removeAttribute("resultadoCarga");
            result = true;
        }
        cargaBean = new CargaProyeccionBean(tipoDependencia);
        cargaBean.setStrServer(request.getHeader("host"));
        cargaBean.setStrUbicacion(getServletContext().getRealPath(""));
        cargaBean.resultSQLConecta(tipoDependencia);
        yearList = cargaBean.getYears();
        if(result){
            result = false;
            if(cargaBean.getCountProyeccion(Integer.parseInt(yearList.get(2))-1, proyeccionList.get(0).getRamo()) > 0){
                result = cargaBean.deleteProyeccion(proyeccionList.get(0).getRamo(), Integer.parseInt(yearList.get(2))-1);
            }else{
                result = true;
            }                
            if(result){
                result = cargaBean.insertProyeccion(proyeccionList,Integer.parseInt(yearList.get(2))-1);
                if(result){
                    strResultado += "exito";
                }else{
                    strResultado += "noInsert";
                    if(!cargaBean.errorBD.isEmpty()){
                        strResultado = strResultado + cargaBean.errorBD ;
                    }
                }
            }else{
                strResultado += "noBorrado";
            }
        }else{
            strResultado += "noCarga";
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
        if (cargaBean != null) {
            cargaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
