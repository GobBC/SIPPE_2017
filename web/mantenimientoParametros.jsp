<%-- 
    Document   : mantenimientoParametros
    Created on : 25/10/2016, 11:30:20 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Parametros"%>
<%@page import="gob.gbc.aplicacion.ParametrosBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Pragma" content="no-cache"> 
        <title>Mantenimiento Par&aacute;metros</title>    
    </head>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label>Mantenimiento Par&aacute;metros<label/></div>
    <%
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
        request.setCharacterEncoding("UTF-8");

        ParametrosBean parametrosBean = null;
        Parametros objParametros = new Parametros();
        String rol = new String();
        String appLogin = new String();
        String fechaSession = new String();
        String numeroSession = new String();
        String tipoDependencia = new String();
        int year = 0;
        boolean isNormativo = false;

        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }
        if (session.getAttribute("strUsuario") == null || session.getAttribute("strUsuario").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            appLogin = (String) session.getAttribute("strUsuario");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("tipoDependencia") == null || session.getAttribute("tipoDependencia").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        parametrosBean = new ParametrosBean(tipoDependencia);
        parametrosBean.setStrServer((request.getHeader("Host")));
        parametrosBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        parametrosBean.resultSQLConecta(tipoDependencia);

        isNormativo = rol.equals(parametrosBean.getRolNormativo());

        if (!isNormativo) {
            out.print("<script> if(confirm('Solo un usuario normativo puede modificar los parámetros del sistema.')){ Home(); } else { Home(); }  </script>");
        }

        objParametros = parametrosBean.getParametros();

    %>

    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-guardar" onclick="saveParametros()">
                <br/> <small> Guardar </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div>

        <% if (isNormativo) { %>                      
        <table width="350px">
            <tr>
                <td>
                    Impresi&oacute;n de firmas en reportes
                </td>
                <td>
                    <% if (objParametros.getRepWebImpFirma().equals("S")) { %>
                    <input type="checkbox" name="chkFirmas" id="chkFirmas" onchange="cambiarValorChkParametros('chkFirmas')" value="S" checked>
                    <% } else { %>
                    <input type="checkbox" name="chkFirmas" id="chkFirmas" onchange="cambiarValorChkParametros('chkFirmas')" value="N">
                    <% } %>
                </td>
            </tr>
            <tr>
                <td>
                    Cierre de impresi&oacute;n del avance POA
                </td>
                <td>
                    <% if (objParametros.getReporteCierre().equals("S")) { %>
                    <input type="checkbox" name="chkAvance" id="chkAvance" onchange="cambiarValorChkParametros('chkAvance')" value="S" checked>
                    <% } else { %>
                    <input type="checkbox" name="chkAvance" id="chkAvance" onchange="cambiarValorChkParametros('chkAvance')" value="N">
                    <% } %>
                </td>
            </tr>
            <tr>
                <td>
                    Activar etiqueta en Impresi&oacute;n del avance POA
                </td>
                <td>
                    <% if (objParametros.getRepValidaInfoCim().equals("S")) { %>
                    <input type="checkbox" name="chkEtiqueta" id="chkEtiqueta" onchange="cambiarValorChkParametros('chkEtiqueta')" value="S" checked>
                    <% } else { %>
                    <input type="checkbox" name="chkEtiqueta" id="chkEtiqueta" onchange="cambiarValorChkParametros('chkEtiqueta')" value="N">
                    <% } %>
                </td>
            </tr>
            <tr>
                <td>
                    Captura para cualquier trimestre
                </td>
                <td>
                    <% if (objParametros.getValidaTodosTrimestre().equals("S")) { %>
                    <input type="checkbox" name="chkTodosTrimestre" id="chkTodosTrimestre" onchange="cambiarValorChkParametros('chkTodosTrimestre')" value="S" checked>
                    <% } else { %>
                    <input type="checkbox" name="chkTodosTrimestre" id="chkTodosTrimestre" onchange="cambiarValorChkParametros('chkTodosTrimestre')" value="N">
                    <% } %>
                </td>
            </tr>
            <tr>
                <td>
                    Cierre de captura del trimestre anterior
                </td>
                <td>
                    <% if (objParametros.getValidaTrimestre().equals("S")) { %>
                    <input type="checkbox" name="chkTrimestre" id="chkTrimestre" onchange="cambiarValorChkParametros('chkTrimestre')" value="S" checked>
                    <% } else { %>
                    <input type="checkbox" name="chkTrimestre" id="chkTrimestre" onchange="cambiarValorChkParametros('chkTrimestre')" value="N">
                    <% } %>
                </td>
            </tr>
        </table>
        <%
            }
        %>  
        <div id="mensaje" style="position: relative"></div>
    </div>
    <jsp:include page="template/piePagina.jsp" />
    <script>
    </script>
    <script type="text/javascript">
        $(document).ready(function() {
            $.ajaxSetup({cache: false});
        });
    </script>
    <%
        if (parametrosBean != null) {
            parametrosBean.resultSQLDesconecta();
        }
    %>
</html>
