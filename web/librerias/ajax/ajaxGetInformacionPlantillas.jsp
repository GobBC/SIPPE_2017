<%-- 
    Document   : getInformacionPlantillas
    Created on : May 13, 2015, 2:47:35 PM
    Author     : jarguelles
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Plantilla"%>
<%@page import="gob.gbc.aplicacion.PlantillaBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.ProyectoBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
    <head>
        <script src="librerias/funciones-poa.js" type="text/javascript"></script>  
    </head>
    <%
        response.setCharacterEncoding("UTF-8");
        PlantillaBean plantillaBean = null;
        List<Plantilla> plantillaListPendientes = new ArrayList<Plantilla>();
        List<Plantilla> asignadosAuto = new ArrayList<Plantilla>();
        List<Plantilla> asignadosList = new ArrayList<Plantilla>();
        String strResult = new String();
        String strClassRow = new String();
        String newFFR = new String();
        String lastFFR = new String();
        String nomMetaGrup = new String();
        String nomAccionGrup = new String();
        String renglones = new String();
        String ramo = new String();
        String programa = new String();
        String departamento = new String();
        String tipoDependencia = new String();
        String strPentientes = new String();
        int year = 0;

        try {

            if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
                tipoDependencia = (String) session.getAttribute("tipoDependencia");
            }

            if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
                ramo = (String) request.getParameter("ramoId");
            }

            if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
                year = Integer.parseInt((String) session.getAttribute("year"));
            }

            plantillaBean = new PlantillaBean(tipoDependencia);
            plantillaBean.setStrServer(request.getHeader("host"));
            plantillaBean.setStrUbicacion(getServletContext().getRealPath(""));
            plantillaBean.resultSQLConecta(tipoDependencia);

            plantillaListPendientes = plantillaBean.getPlantillasByRamoYear(ramo, year);

            // asignadosAuto = plantillaBean.getPlantillaAsignadosAuto(year, ramo);

            asignadosList = plantillaBean.getPlantillaAsignados(year, ramo);

            plantillaBean.resultSQLDesconecta();
            String opcionesMenu = new String();
            String opcionDepartamento = new String();
            String renglonesTablaDepartamento = new String();
            String idDeptoAnt = new String();
            String idDepto = new String();
            String descrDeptoAnt = new String();
            String descrDepto = new String();
            String idPrograma = new String();
            String descrPrograma = new String();
            int contOpciones = 0;
            int conReg = 0;
            int conRegGrup = 0;

            if (plantillaListPendientes.size() > 0) {
                for (Plantilla plantilla : plantillaListPendientes) {

                    conReg++;
                    idDepto = plantilla.getDepto();
                    descrDepto = plantilla.getDescrDepto();
                    if (!idDeptoAnt.equals(idDepto)) {
                        conRegGrup = 0;
                    }
                    conRegGrup++;
                    if (conReg == 1) {
                        idDeptoAnt = idDepto;
                        descrDeptoAnt = descrDepto;
                        opcionDepartamento += "<li class='menuAcordeon' > + " + idDeptoAnt + "-" + descrDeptoAnt + "";
                        opcionDepartamento += "<ul>";
                        opcionDepartamento += "<li>";
                        opcionDepartamento += "<table class='tabla-plantilla' >";
                        opcionDepartamento += "<thead>";
                        opcionDepartamento += "<th  >" + "Programa" + "</th>";
                        opcionDepartamento += "<th  >" + "Meta" + "</th>";
                        opcionDepartamento += "<th  >" + "Acción" + "</th>";
                        opcionDepartamento += "<th hidden >" + "Fuente de Financiamiento" + "</th>";
                        opcionDepartamento += "<th hidden >" + "Tipo Gasto" + "</th>";
                        opcionDepartamento += "<th  >" + "Plantilla" + "</th>";
                        opcionDepartamento += "<th hidden  >" + "IdPrograma" + "</th>";
                        opcionDepartamento += "<th hidden  >" + "IdDepartamento" + "</th>";
                        opcionDepartamento += "<th hidden  >" + "IdMeta" + "</th>";
                        opcionDepartamento += "<th hidden  >" + "IdAcci&oacute;n" + "</th>";
                        opcionDepartamento += "<th hidden  >" + "IdFuente" + "";
                        opcionDepartamento += "<th hidden  >" + "IdFondo" + "</th>";
                        opcionDepartamento += "<th hidden  >" + "IdRecurso" + "</th>";
                        opcionDepartamento += "<th hidden  >" + "Estado Anterior" + "</th>";
                        opcionDepartamento += "<th hidden  >" + "Tipo Gasto" + "</th>";
                        opcionDepartamento += "</thead>";
                        opcionDepartamento += "<tbody>";
                    }

                    if (!idDeptoAnt.equals(idDepto)) {

                        if (conRegGrup < 1) {
                            //   opcionDepartamento += opcionDepartamento.replace("menuAcordeon", "noMenuAcordeon");
                        }

                        opcionDepartamento += renglonesTablaDepartamento;
                        opcionDepartamento += "</tbody>";
                        opcionDepartamento += "</table>";
                        opcionDepartamento += "</li>";
                        opcionDepartamento += "</ul>";
                        opcionDepartamento += "</li>";
                        opcionesMenu += opcionDepartamento;
                        opcionDepartamento = "";
                        renglonesTablaDepartamento = "";
                        opcionDepartamento += "<li class='menuAcordeon' > + " + plantilla.getDepto() + "-" + plantilla.getDescrDepto() + "";
                        opcionDepartamento += "<ul>";
                        opcionDepartamento += "<li>";
                        opcionDepartamento += "<table class='tabla-plantilla' >";
                        opcionDepartamento += "<thead>";
                        opcionDepartamento += "<th  >" + "Programa" + "</th>";
                        opcionDepartamento += "<th  >" + "Meta" + "</th>";
                        opcionDepartamento += "<th  >" + "Acción" + "</th>";
                        opcionDepartamento += "<th hidden >" + "Fuente de Financiamiento" + "</th>";
                        opcionDepartamento += "<th hidden >" + "Tipo Gasto" + "</th>";
                        opcionDepartamento += "<th  >" + "Plantilla" + "</th>";
                        opcionDepartamento += "<th hidden  >" + "IdPrograma" + "</th>";
                        opcionDepartamento += "<th hidden  >" + "IdDepartamento" + "</th>";
                        opcionDepartamento += "<th hidden  >" + "IdMeta" + "</th>";
                        opcionDepartamento += "<th hidden  >" + "IdAcción" + "</th>";
                        opcionDepartamento += "<th hidden  >" + "IdFuente" + "</th>";
                        opcionDepartamento += "<th hidden  >" + "IdFondo" + "</th>";
                        opcionDepartamento += "<th hidden  >" + "IdRecurso" + "</th>";
                        opcionDepartamento += "<th hidden  >" + "Estado Anterior" + "</th>";
                        opcionDepartamento += "<th hidden  >" + "Tipo Gasto" + "</th>";
                        opcionDepartamento += "</thead>";
                        opcionDepartamento += "<tbody>";
                        idDeptoAnt = idDepto;
                        descrDeptoAnt = descrDepto;

                    }


                    if (conReg % 2 == 0) {
                        strClassRow = "class='rowPar'";
                    } else {
                        strClassRow = "";
                    }

                    renglonesTablaDepartamento += "<tr " + strClassRow + " >";
                    renglonesTablaDepartamento += "<td  >";
                    renglonesTablaDepartamento += "" + plantilla.getPrgId() + "-" + plantilla.getDescePrg();
                    renglonesTablaDepartamento += "</td>";
                    renglonesTablaDepartamento += "<td >";
                    renglonesTablaDepartamento += "" + plantilla.getMeta() + "-" + plantilla.getDescrMeta();
                    renglonesTablaDepartamento += "</td>";
                    renglonesTablaDepartamento += "<td >";
                    renglonesTablaDepartamento += "" + plantilla.getAccion() + "-" + plantilla.getDescrAccion();
                    renglonesTablaDepartamento += "</td >";
                    renglonesTablaDepartamento += "<td hidden >";
                    renglonesTablaDepartamento += "" + plantilla.getFuente() + "." + plantilla.getFondo() + "." + plantilla.getRecurso() + "-" + plantilla.getFuenteFinanciamiento();
                    renglonesTablaDepartamento += "</td>";
                    renglonesTablaDepartamento += "<td hidden >";
                    renglonesTablaDepartamento += "" + plantilla.getTipoGasto();
                    renglonesTablaDepartamento += "</td>";
                    renglonesTablaDepartamento += "<td align='center'>";
                    if (plantilla.getSeleccionado() == 1) {
                        renglonesTablaDepartamento += "<input disabled id='plantillaId" + conReg + "'  type='radio' name='FFR" + plantilla.getDepto() + "'class='asigCheck'  />";
                    } else if (conRegGrup == 1) {
                        renglonesTablaDepartamento += "<input disabled id='plantillaId" + conReg + "'  type='radio' name='FFR" + plantilla.getDepto() + "' class='asigCheck'  />";
                    } else {
                        renglonesTablaDepartamento += "<input disabled id='plantillaId" + conReg + "'  type='radio' name='FFR" + plantilla.getDepto() + "' class='asigCheck'  />";
                    }
                    renglonesTablaDepartamento += "</td>";
                    renglonesTablaDepartamento += "<td   hidden   >";
                    renglonesTablaDepartamento += "<input type='hidden' id='programaId" + conReg + "' value ='" + plantilla.getPrgId() + "' " + "/> ";
                    renglonesTablaDepartamento += "</td>";
                    renglonesTablaDepartamento += "<td   hidden   >";
                    renglonesTablaDepartamento += "<input type='hidden' id='departamentoId" + conReg + "' value ='" + plantilla.getDepto() + "' " + "/> ";
                    renglonesTablaDepartamento += "</td>";
                    renglonesTablaDepartamento += "<td   hidden   >";
                    renglonesTablaDepartamento += "<input type='hidden' id='metaId" + conReg + "' value ='" + plantilla.getMeta() + "' " + "/> ";
                    renglonesTablaDepartamento += "</td>";
                    renglonesTablaDepartamento += "<td   hidden  >";
                    renglonesTablaDepartamento += "<input type='hidden' id='accionId" + conReg + "' value ='" + plantilla.getAccion() + "' " + "/> ";
                    renglonesTablaDepartamento += "</td>";
                    renglonesTablaDepartamento += "<td  hidden   >";
                    renglonesTablaDepartamento += "<input type='hidden' id='fuenteId" + conReg + "' value ='" + plantilla.getFuente() + "' " + "/> ";
                    renglonesTablaDepartamento += "</td>";
                    renglonesTablaDepartamento += "<td hidden    >";
                    renglonesTablaDepartamento += "<input type='hidden' id='fondoId" + conReg + "' value ='" + plantilla.getFondo() + "' " + "/> ";
                    renglonesTablaDepartamento += "<td  hidden   >";
                    renglonesTablaDepartamento += "<input type='hidden' id='recursoId" + conReg + "' value ='" + plantilla.getRecurso() + "' " + "/> ";
                    renglonesTablaDepartamento += "</td>";
                    renglonesTablaDepartamento += "<td   hidden  >";
                    renglonesTablaDepartamento += "<input type='hidden' id='estadoAnterior" + conReg + "' value ='" + plantilla.getSeleccionado() + "' " + "/> ";
                    renglonesTablaDepartamento += "</td>";
                    renglonesTablaDepartamento += "<td   hidden  >";
                    renglonesTablaDepartamento += "<input type='hidden' id='tipoGasto" + conReg + "' value ='" + plantilla.getTipoGasto() + "' " + "/> ";
                    renglonesTablaDepartamento += "</td>";
                    renglonesTablaDepartamento += "</tr>";


                    if (plantillaListPendientes.size() == conReg) {

                        if (conRegGrup < 2) {
                            //  opcionDepartamento = opcionDepartamento.replace("menuAcordeon", "noMenuAcordeon");
                        }

                        opcionDepartamento += renglonesTablaDepartamento;
                        opcionDepartamento += "</tbody>";
                        opcionDepartamento += "</table>";
                        opcionDepartamento += "</li>";
                        opcionDepartamento += "</ul>";
                        opcionDepartamento += "</li>";
                        opcionesMenu += opcionDepartamento;
                        opcionDepartamento = "";
                        renglonesTablaDepartamento = "";
                        idDeptoAnt = idDepto;
                        descrDeptoAnt = descrDepto;
                        conRegGrup = 0;
                    }

                }

            }

            strPentientes += "<ul class='menuAcordeon2' >";
            strPentientes += opcionesMenu;
            strPentientes += "</ul>";
            strResult += "<div id='tabContent-1'>";
            strResult += "   " + strPentientes;



            strResult += "	<input id='contadorRegistros' type='hidden' value='" + conReg + "' />";
            strResult += "</div>";
            strResult += "<div id='tabContent-2'>";
            strResult += "<table id='plantAsigAuto'>";
            strResult += "<thead>";
            strResult += "<tr> <th> Departamento </th> <th> Programa </th> <th> Meta "
                    + "</th> <th> Acci&oacute;n </th> <th hidden > Fuente de financiamiento </th>";
            strResult += "</thead>";
            strResult += "<tbody>";
            asignadosAuto.addAll(asignadosList);
            for (Plantilla asignadoA : asignadosAuto) {
                strResult += "<tr>";
                strResult += "<td>" + asignadoA.getDepto() + "-" + asignadoA.getDescrDepto() + "</td>";
                strResult += "<td>" + asignadoA.getPrgId() + "-" + asignadoA.getDescrMeta() + "</td>";
                strResult += "<td>" + asignadoA.getMeta() + "-" + asignadoA.getDescrMeta() + "</td>";
                strResult += "<td>" + asignadoA.getAccion() + "-" + asignadoA.getDescrAccion() + "</td>";
                strResult += "<td>" + "" + "</td>";
                if (asignadoA.getAuto() == 1) {
                    strResult += "<td> <input disabled id='checkAsignado' class='asigCheck' type='checkbox' name='plantAsig' value='" + asignadoA.getDepto() + "' checked /> </td> ";
                } else {
                    strResult += "<td> <input disabled id='checkAsignado' class='asigCheck' type='checkbox' name='plantAsig' value='" + asignadoA.getDepto() + "' checked /> </td> ";
                }
                strResult += "</tr>";
            }
            strResult += "</tbody>";
            strResult += "</table>";
            strResult += "</div>";

            strResult += "<script type='text/javascript'>";
            strResult += " var altoTemp = $(\".menuAcordeon2\").height();"
                    + "  var altoTemp2 = $(\"#plantAsigAuto\").height(); "
                    + "if ( altoTemp >= altoTemp2 )              "
                    + "  $(\"#tabContent\").height(altoTemp + 10);"
                    + "else  "
                    + "  $(\"#tabContent\").height(altoTemp2 + 10);";
            strResult += "      $(\"#tabContent\").height($(\".menuAcordeon2\").height() + 10);";
            strResult += "$(\".menuAcordeon\").click(function handler( event ){"
                    + "var target = $( event.target );var alto = 0; var alto2 = 0;"
                    + "if ( target.is( \".menuAcordeon\" ) ) {"
                    + "      target.children().toggle();"
                    + "      alto = $(\".menuAcordeon2\").height();"
                    + "      alto2 = $(\"#plantAsigAuto\").height(); "
                    + "if ( alto >= alto2 )              "
                    + "  $(\"#tabContent\").height(alto + 10);"
                    + "  else  "
                    + "  $(\"#tabContent\").height(alto2 + 10);"
                    + "}"
                    + "}).find(\".menuAcordeon ul\").hide();"
                    + "$(\".menuAcordeon\").click();";


            strResult += "var largo = $(\".menuAcordeon table:last-child tbody\"); \n"
                    + "var menu = ''; \n"
                    + " $(\"#autoAcomodo\").click(function handler( event ){  \n "
                    + "var target = $( event.target );var alto = 0; var alto2 = 0; \n "
                    + "if ( target.is( \"#autoAcomodo\" ) ) { \n "
                    + "      target.children().toggle(); \n "
                    + "      alto = $(\".menuAcordeon2\").height(); \n "
                    + "      alto2 = $(\"#plantAsigAuto\").height(); \n "
                    + "if ( alto >= alto2 )         \n "
                    + "  $(\"#tabContent\").height(alto + 10); \n "
                    + "  else  \n "
                    + "  $(\"#tabContent\").height(alto2 + 10); \n "
                    + "} \n "
                    + "}); \n "
                    + "$(\"#autoAcomodo\").click(); \n "
                    + " ";
            strResult += "</script>";


        } catch (Exception ex) {
            strResult = "-1";
            Bitacora bitacora = new Bitacora();
            bitacora.setStrServer(request.getHeader("host"));
            bitacora.setStrUbicacion(getServletContext().getRealPath(""));
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, request.getServletPath());
            bitacora.grabaBitacora();
        } finally {
            if (plantillaBean != null) {
                plantillaBean.resultSQLDesconecta();
            }
            out.print(strResult);
        }
    %> 
</html>