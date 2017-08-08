<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="../error.jsp" %>
<%@ page import="gob.gbc.sql.ResultSQL"  %>
<%@ page import="java.util.Vector"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%if(session.getAttribute("strUsuario")==null) 	{ %>

	<html>
		<body>
			<form name="form1" method="post" action="../inicioSesion.jsp"></form>
				<script language="javascript">
					alert("Su Sesión ha Expirado. Por Favor, Introduzca Nuevamente su Usuario y Contraseña");
					document.form1.submit();
				</script>
		</body>
	</html>
<% } else { 
	String  strEstatus="";
    Vector vecResultado = new Vector();
    int ban=0;
    String tipoDependencia = new String();
    if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
        tipoDependencia = (String) session.getAttribute("tipoDependencia");
    }
    ResultSQL resultSql = new ResultSQL(tipoDependencia);
    resultSql.setStrServer(((String) request.getHeader("Host")));
    resultSql.setStrUbicacion(getServletContext().getRealPath("").toString());
    resultSql.resultSQLConecta(tipoDependencia);
    vecResultado = resultSql.getResultSQLConsultaUsuarios();
        resultSql.resultSQLDesconecta();
%>
<html>
    <head>
        <title>Sistema de Captura de Indices del Periódico Oficial</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link href="../librerias/site.css" rel="stylesheet" type="text/css" />
        <link href="../librerias/portal.css" rel="stylesheet" type="text/css" />
        <link href="../librerias/oficial.css" rel="stylesheet" type="text/css" />
        <script src="../librerias/funciones.js"></script>
    </head>
	<body>
        <jsp:include page="../template/encabezadoSub.jsp" />
		  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td height="19" class="estilo"><div align="center" class="submheadaz">CONSULTA DE USUARIOS PLAN OPERATIVO ANUAL<br />
              </div></td>
            </tr>
          </table>
	<br/> 
		<p align="right"><a href="altaUsuario.jsp" class="submheadaz">NUEVO USUARIO</a>&nbsp;&nbsp;</p>
		<br /><br />
      <table width="100%" border="0" align="center">
        <tr bgcolor="#003366">
          <th scope="col" bgcolor="#CDE99E" class="submhead" >&nbsp;</th>
          <th scope="col" bgcolor="#CDE99E" class="submhead" align="center">USUARIO </th>
          <th scope="col" bgcolor="#CDE99E" class="submhead" align="center">NOMBRE </th>
          <th scope="col" bgcolor="#CDE99E" class="submhead" align="center">CORREO</th>
          <th scope="col" bgcolor="#CDE99E" class="submhead">&nbsp;</th>
        </tr>
	<%
        for(int i=0; i<vecResultado.size(); i++) {
            Vector vecInterno = (Vector)vecResultado.get(i);
            if(ban==0) {
                ban=1;
	%>
        <tr bgcolor="#CCCCCC" class="estilo" style="CURSOR: HAND" onMouseOver="uno(this,'#FFCC33')" onMouseOut="dos(this,this.bgcolor)" >
	<%	  }   else   {		ban=0; %>
		<tr bgcolor="#FFFFFF" class="estilo" style="CURSOR: HAND" onMouseOver="uno(this,'#FFCC33')" onMouseOut="dos(this,this.bgcolor)" >
	<%    }  %>
		  <td align="center" onClick="javascript:window.location='datosUsuario.jsp?strUsuario=<%=(vecInterno.get(1))%>'">
		  	<%
				if(vecInterno.get(2)!=null) strEstatus=(String)vecInterno.get(2);
			 if(strEstatus.equals("1")) { /*activo*/ 
			%>
		  		<img src="../imagenes/activo.gif" />
			<%} else { /*inactivo*/ %>
		  		<img src="../imagenes/inactivo.gif" />
			<% } %>				
				</td>
		  <td align="center" onClick="javascript:window.location='datosUsuario.jsp?strUsuario=<%=(vecInterno.get(1))%>'">
		  		<%=(vecInterno.get(1))%>
			</td>
		  <td align="center" onClick="javascript:window.location='datosUsuario.jsp?strUsuario=<%=(vecInterno.get(1))%>'">
		  		<%=(vecInterno.get(0))%>
			</td>
		  <td align="center" onClick="javascript:window.location='datosUsuario.jsp?strUsuario=<%=(vecInterno.get(1))%>'">
		  		<%=(vecInterno.get(3))%>
			</td>
		  <td align="center"><a href="actualizaUsuario.jsp?strUsuario=<%=(vecInterno.get(1))%>" class="submheadaz">
		  		MODIFICAR</a> 
			</td>
		</tr>	
		<% } %>
      </table>
	  <br />
		<p align="right"  class="submheadaz"><a href="cambiaContrasena.jsp">CAMBIAR CONTRASEÑA</a>&nbsp;&nbsp;</p>
		<p align="right"  class="submheadaz"><a href="../inicioSesion.jsp">SALIR DEL SISTEMA</a>&nbsp;&nbsp;</p>
        <jsp:include page="../template/piePaginaSub.jsp" />
</body>
</html>
	<% 
	} 
	%>
