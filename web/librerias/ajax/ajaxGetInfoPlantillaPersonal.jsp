<%-- 
    Document   : ajaxGetInfoPlantillaPersonal
    Created on : Jul 9, 2015, 3:59:43 PM
    Author     : ugarcia
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.RelacionLaboral"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.PlantillaPersonal"%>
<%@page import="gob.gbc.aplicacion.PlantillaPersonalBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    PlantillaPersonalBean plantillaPersonalBean = null;
    PlantillaPersonal plantillaPersonal = null;
    List<RelacionLaboral> relacionList = new ArrayList<RelacionLaboral>();
    String tipoDependencia = new String();
    String ramo = new String();
    String strResultado = new String();
    String selected = new String();
    int year = 0;
    int opcion = 0;
    String relacionLaboral = new String();
    try {
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
        if (request.getParameter("relacionLaboral") != null && !request.getParameter("relacionLaboral").equals("")) {
            relacionLaboral = request.getParameter("relacionLaboral");
        }
        plantillaPersonalBean = new PlantillaPersonalBean(tipoDependencia);
        plantillaPersonalBean.setStrServer(((String) request.getHeader("Host")));
        plantillaPersonalBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        plantillaPersonalBean.resultSQLConecta(tipoDependencia);
        relacionList = plantillaPersonalBean.getRelacionLaboralList(year);
        if (opcion == 1) {
            plantillaPersonal = new PlantillaPersonal();
            plantillaPersonal.setRelLaboral("-1");
        } else if (opcion == 2) {
            plantillaPersonal = plantillaPersonalBean.getPlantillaPersonalById(year, ramo, relacionLaboral);
        }
        strResultado += "<center>";
        strResultado += "<table> <tr> <td> <div> Relaci&oacute;n laboral </div>";
        strResultado += "<select id='selRelLaboral'> ";
        strResultado += "<option value='-1'> --Seleccione una relaci&oacute;n laboral-- </option>";
        for (RelacionLaboral relacion : relacionList) {
            if (plantillaPersonal.getRelLaboral().equals(relacion.getRelacionLabId())) {
                selected = "selected";
            }
            strResultado += "<option value='" + relacion.getRelacionLabId() + "' " + selected + ">"
                    + relacion.getRelacionLabId() + "-" + relacion.getRelacionLab() + "</option>";
            selected = new String();
        }


        strResultado += "</select> </td> ";
        strResultado += " <td> <div> Cantidad </div> <input type='text' "
                + "id='inpTxtCantidad' value='" + ((int) plantillaPersonal.getCantidad()) + "' /> </td> </tr> </table>";
        strResultado += "<script type='text/javascript'>";
        //strResultado += "  $('#inpTxtCantidad').on('change keyup', function() {";
        //strResultado += "  var sanitized = $(this).val().replace(/[^0-9]/g, '');";
        //strResultado += "  $(this).val(sanitized);";
        //strResultado += "  });";
        strResultado += "              $('#inpTxtCantidad').keydown(function (e) {";
        strResultado += "                   if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 190]) !== -1 ||";
        strResultado += "                      (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || ";
        strResultado += "                      (e.keyCode >= 35 && e.keyCode <= 40)) {";
        strResultado += "                            return;";
        strResultado += "                  }";
        strResultado += " if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {";
        strResultado += " e.preventDefault();";
        strResultado += "                   }";
        strResultado += "                });";
        strResultado += "</script> <br/>";
        if (opcion == 2) {
            strResultado += " <input type='button' value='Aceptar' onclick='editarPlantillaPer(\"" + plantillaPersonal.getRelLaboral() + "\")'/>";
        } else {
            strResultado += " <input type='button' value='Aceptar' onclick='nuevoPlantillaPer()'/>";
        }
        strResultado += " <input type='button' value='Cancelar' onclick='cerrarPlantilla()'/> </div> </center> ";
    } catch (Exception ex) {
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
