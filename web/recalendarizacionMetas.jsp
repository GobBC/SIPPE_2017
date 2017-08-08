<%-- 
    Document   : recalendarizacionMetas
    Created on : Mar 18, 2015, 2:00:52 PM
    Author     : jarguelles
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="librerias/site.css" rel="stylesheet" type="text/css">
        <link href="librerias/oficial.css" rel="stylesheet" type="text/css">
        <link href="librerias/css/bootstrap.css" rel="stylesheet" type="text/css">
        <link href="librerias/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="template/css/estilo.css" rel="stylesheet" type="text/css">
        <link href="librerias/portal.css" rel="stylesheet" type="text/css">
        <script src="librerias/funciones.js" type="text/javascript">
        </script><script src="librerias/funciones-poa.js" type="text/javascript"></script>
        <script src="librerias/js/modernizr.js" type="text/javascript"></script>
        <script src="librerias/js/jquery/jquery-1.9.1.js" type="text/javascript"></script>
        <script src="librerias/js/jquery.mask.js" type="text/javascript"></script>
    </head>
    <%
        if (session.getAttribute("strUsuario") == null
                || session.getAttribute("strUsuario").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("tipoDependencia") == null
                || session.getAttribute("tipoDependencia").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        List<Ramo> ramoList = new ArrayList<Ramo>();
        int year = 0;
        String usuario = new String();
        String tipoDependencia = new String();
        String tempMillis = new String();
        tempMillis = "" + System.currentTimeMillis();
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }

        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }

        ResultSQL resultSQL = new ResultSQL(tipoDependencia);
        resultSQL.setStrServer(((String) request.getHeader("Host")));
        resultSQL.setStrUbicacion(getServletContext().getRealPath("").toString());
        resultSQL.resultSQLConecta(tipoDependencia);
        
        ramoList = resultSQL.getResultRamoByYear(year, usuario);
        resultSQL.resultSQLDesconecta();


    %>
    <script>
        getInfoMetaEstimacion();
    </script>
    <body> 
        <div id="estimacionMetaRC" style="">
            <div>
                <table id="infoMetaRC" cellspacing="10">
                    <tbody>
                        <tr>       
                            <td>Ramo:</td> 
                            <td><label id="ramoDescr" >Male</label></td>
                        </tr>
                        <tr>       
                            <td>Programa:</td>       
                            <td>002-ORGANO DE FISCALIZACION SUPERIOR DEL ESTADO</td>   
                        </tr>   
                        <tr>        
                            <td> Proyecto/Actividad: </td>       
                            <td>P1-PY CLUSTER DEL TURISMO MÉDICO Y DE SALUD</td>    
                        </tr>   
                        <tr>        
                            <td>Meta:</td>       
                            <td>1-FORTALECIMIENTO DE LA PRESENCIA DE LA SAHOPE ANTE LAS DEPENDENCIAS DEL SECTOR Y LOS MPIOS. MEDIANTE LA CAPACITACION DE LOS SERVIDORES PUBLICOS ADSCRITOS AL SECTOR.</td>    
                        </tr>
                    </tbody>
                </table>
                <br>
                <div class="calenVistaRC"> 
                    <fieldset id="fieldsetMetaRC">
                        <legend><strong>Información Original</strong></legend>
                        <div> Enero <input value="98" disabled="" type="text"></div>
                        <div> Febrero <input value="8" disabled="" type="text"></div>
                        <div> Marzo <input value="5" disabled="" type="text"></div>
                        <div> Abril <input value="287" disabled="" type="text"></div>
                        <div> Mayo <input value="4" disabled="" type="text"></div>
                        <div> Junio <input value="0" disabled="" type="text"></div>
                        <div> Julio <input value="0" disabled="" type="text"></div>
                        <div> Agosto <input value="0" disabled="" type="text"></div>
                        <div> Septiembre <input value="0" disabled="" type="text"></div>
                        <div> Octubre <input value="0" disabled="" type="text"></div>
                        <div> Noviembre <input value="0" disabled="" type="text"></div>
                        <div> Diciembre <input value="0" disabled="" type="text"></div>
                    </fieldset>
                </div>
                <br> 
                <div>
                    <div class="calenVistaRC">
                        <fieldset id="fieldsetMetaRC">
                            <legend><strong>Reprogramación</strong></legend>
                            <div>Enero<input class="estimacion" id="estimacion0" value="" maxlength='14' type="text"></div>
                            <div>Febrero<input class="estimacion" id="estimacion1"  value="" maxlength='14' type="text"></div>
                            <div>Marzo<input class="estimacion" id="estimacion2"  value="" maxlength='14' type="text"></div>
                            <div>Abril<input class="estimacion" id="estimacion3"  value="" maxlength='14' type="text"></div>
                            <div>Mayo<input class="estimacion" id="estimacion4"  value="" maxlength='14' type="text"></div>
                            <div>Junio<input class="estimacion" id="estimacion5"  value="" maxlength='14' type="text"></div>
                            <div>Julio<input class="estimacion" id="estimacion6"  value="" maxlength='14' type="text"></div>
                            <div>Agosto<input class="estimacion" id="estimacion7"  value="" maxlength='14' type="text"></div>
                            <div>Septiembre<input class="estimacion" id="estimacion8"  value="" maxlength='14' type="text"></div>
                            <div>Octubre<input class="estimacion" id="estimacion9"  value="" maxlength='14' type="text"></div>
                            <div>Noviembre<input class="estimacion" id="estimacion10" value="" maxlength='14' type="text"></div>
                            <div>Diciembre<input class="estimacion" id="estimacion11" value="" maxlength='14' type="text"></div>
                        </fieldset>
                    </div>
                    <br>  
                    <script type="text/javascript">
                        $('.estimacion').keydown(function (e) {
                            if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 || (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || (e.keyCode >= 35 && e.keyCode <= 40)) {
                                return;
                            } 
                            if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) { 
                                e.preventDefault();                   
                            }                
                        });              
			
                        $('#inpTxtCantUnit').keydown(function (e) {
                            if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 || (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || (e.keyCode >= 35 && e.keyCode <= 40)) { 
                                return;                  
                            } 
				
                            if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) { 
                                e.preventDefault();                   
                            }                
                        });
                    </script>
                </div>
            </div>
            <br>
        </div>
    </body>
</html>
