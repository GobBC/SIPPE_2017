<%-- 
    Document   : ajaxGetPlantillaPersonal
    Created on : Jul 9, 2015, 2:44:33 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.PlantillaPersonal"%>
<%@page import="gob.gbc.aplicacion.PlantillaPersonalBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    PlantillaPersonalBean plantillaPersonalBean = null;
    List<PlantillaPersonal> plantillaList = new ArrayList<PlantillaPersonal>();
    String tipoDependencia = new String();
    String ramo = new String();
    String strResultado = new String();
    double cantidad = 0.0; 
    int year = 0;
    int opcion = 0;
    String relLaboral = new String();
    String relLaboralAnt = new String();
    String rol = new String();
    int contRow = 0;
    boolean resultado = false;
    boolean cierre = false;
    try{
        if ((String) session.getAttribute("strRol") != null) {
            rol = (String) session.getAttribute("strRol");
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("yearPres") != null && request.getParameter("yearPres") != "") {
            year = Integer.parseInt(request.getParameter("yearPres"));
        }
        if (request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")) {
            ramo = request.getParameter("ramo");
        }
        if (request.getParameter("opcion") != null && !request.getParameter("opcion").equals("")) {
            opcion = Integer.parseInt(request.getParameter("opcion"));
        }
        if (request.getParameter("relLaboral") != null && !request.getParameter("relLaboral").equals("")) {
            relLaboral = request.getParameter("relLaboral");
        }
        if (request.getParameter("relLaboralAnt") != null && !request.getParameter("relLaboralAnt").equals("")) {
            relLaboralAnt = request.getParameter("relLaboralAnt");
        }
        if (request.getParameter("cantidad") != null && !request.getParameter("cantidad").equals("")) {
            cantidad = Double.parseDouble(request.getParameter("cantidad"));
        }
        plantillaPersonalBean = new PlantillaPersonalBean(tipoDependencia);
        plantillaPersonalBean.setStrServer(((String) request.getHeader("Host")));
        plantillaPersonalBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        plantillaPersonalBean.resultSQLConecta(tipoDependencia);
        if(opcion == 1){
            resultado = plantillaPersonalBean.insertPlantillaPersonal(year, ramo, relLaboral, cantidad);
            if(!resultado){
                strResultado = "limite-" + strResultado;
            }
        }
        if(opcion == 2){
            resultado = plantillaPersonalBean.updatePlantillaPersonal(year, ramo, relLaboral, cantidad, relLaboralAnt);
            if(!resultado){
                strResultado = "limite-" + strResultado;
            }
        }
        if(opcion == 3){
            resultado = plantillaPersonalBean.deletePlantillaPersonal(year, ramo, relLaboral);
            if(!resultado){
                strResultado = "limite-" + strResultado;
            }
        }
        cierre = plantillaPersonalBean.getResultSQLValidaRamoCierre(ramo, year);
        if(!cierre || plantillaPersonalBean.getResultSQLGetRolesPrg().equals(rol)){
            plantillaList = plantillaPersonalBean.getPlantillaPestonal(year, ramo);
            strResultado += "<center> <table id='tblPlantillaPer'> <thead>";
            strResultado += "<tr> <th> Relaci&oacute;n laboral </th> <th> Cantidad </th> </tr> </thead>" ;
            strResultado += "<tbody>";
            for(PlantillaPersonal plantilla : plantillaList){
                contRow ++;
                if(contRow%2 == 0)
                    strResultado += "<tr class='rowPar'>  <td> "+plantilla.getRelLaboral()+"-"+plantilla.getRelLaboralDescr()+"</td>";
                else
                    strResultado += "<tr> <td> "+plantilla.getRelLaboral()+"-"+plantilla.getRelLaboralDescr()+"</td>";
                strResultado += "<td>"+((int) plantilla.getCantidad())+"</td> </tr>";
            }
            strResultado += "</tbody> </table> </center>";
            strResultado += "<script type=\"text/javascript\">";
            strResultado += "  $(\"#tblPlantillaPer tbody tr\").click(function(){";
            strResultado += "  $(this).addClass('selected').siblings().removeClass('selected');";
            strResultado += "});";
            strResultado += "</script>";
        }else{
            strResultado = "0";
        }
    }catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (plantillaPersonalBean != null) {
            plantillaPersonalBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
