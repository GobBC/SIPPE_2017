<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="../error.jsp" %>
<%@ page import="gob.gbc.sql.ResultSQL"  %>
<%@ page import="java.util.Vector"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<% if(session.getAttribute("strUsuario")==null) { %>
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

        String tipoDependencia = new String();
	String strUsuario="", strAccion="", strContrasena="", strContrasenaAnterior="", action="", strRol="", strTipoContrasena="";
	int iResultado=0;
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(session.getAttribute("strUsuario")!=null)        
            strUsuario=(String)session.getAttribute("strUsuario");
	if(request.getParameter("strContrasena")!=null)     
            strContrasenaAnterior=(String)request.getParameter("strContrasena");
    	if(request.getParameter("strEncrypt")!=null) 		
            strContrasena=(String)request.getParameter("strEncrypt");
	if(request.getParameter("strAccion")!=null)         
            strAccion=(String)request.getParameter("strAccion");
        if(session.getAttribute("strRol")!=null)            
            strRol=(String)session.getAttribute("strRol");
        if(session.getAttribute("strTipoContrasena")!=null)	
            strTipoContrasena=(String)session.getAttribute("strTipoContrasena");
        if(strRol.equals("1")) 
            action="usuarios.jsp";
        else                
            action="../inicio.jsp";



	if(strAccion.equals("cambiar")) {	
            ResultSQL resultSql = new ResultSQL(tipoDependencia);
            resultSql.setStrServer(((String) request.getHeader("Host")));
            resultSql.setStrUbicacion(getServletContext().getRealPath("").toString());
            resultSql.resultSQLConecta(tipoDependencia);

            iResultado = resultSql.getResultSQLCambiaContrasena(strUsuario, strContrasena, strContrasenaAnterior);

            resultSql.resultSQLDesconecta();

            if(iResultado == 2){
                    strAccion="mal_contrasena";	
            }
            if(iResultado == 1){
                    strAccion="mensaje";
            }
	}//if cambiar

%>
<html>
    <head>
        <title>Plan Operativo Anual</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link href="../librerias/site.css" rel="stylesheet" type="text/css" />
        <link href="../librerias/portal.css" rel="stylesheet" type="text/css" />
        <link href="../librerias/oficial.css" rel="stylesheet" type="text/css" />
        <script src="../librerias/funciones.js"></script>
    </head>
	<body>
        <jsp:include page="../template/encabezadoSub.jsp" />
		<center>
		  <table width="781" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td height="19" class="estilo"><div align="center" class="submheadaz"><br />
              </div></td>
            </tr>
          </table>
		  <br />	<% if(!strAccion.equals("mensaje")) { 

				if(!strTipoContrasena.equals("3"))
					{
	%>
		<br/><br/>
		<b>
			<span class="alerta">
				<br/>EL SISTEMA HA IDENTIFICADO QUE LA CONTRASE&Ntilde;A CON LA QUE HA ACCEDIDO 
							HA SIDO PROPORCIONADA AUTOM&Aacute;TICAMENTE.
			</span>			
			<br/>
			<font color="#000000">
				<br/>Por Favor Introduzca una Nueva Contrase&ntilde;a para Continuar.			
			</font>			
		</b>	
			<br/><br/>	
	<% 		 	}		%>
  <form name="form1" method="post">
	<input type="hidden" name="strAccion" value="<%=(strAccion)%>">
	<input type="hidden" name="strEncrypt">
	<input type="hidden" name="strContrasena">
      <table width="550" border="0" align="center">
        <tr bgcolor="#CDE99E">
          <th height="18" colspan="2" bgcolor="#CDE99E" class="submhead">DATOS DEL USUARIO</th>
        </tr>
        <tr  class="printfont"  >
          <td align="left" bgcolor="#E5E8EF" >CLAVE DE USUARIO: &nbsp;</td>
          <td align="left" ><%=(session.getAttribute("strUsuario"))%>
			</td>
        </tr>
        <tr  class="printfont"  >
          <td align="left" bgcolor="#E5E8EF" >CONTRASE&Ntilde;A ANTERIOR: &nbsp;</td>
          <td align="left" >
		  			<input type="password" name="cont_anterior"  style="text-transform:uppercase"  
					onKeyPress="if(!letraynum(event.keyCode)) event.returnValue=false;"  class="campo_01"
					onchange="this.value=mayuscula(this.value)" maxlength="20">
			</td>
        </tr>
        <tr  class="printfont"  >
          <td align="left" bgcolor="#E5E8EF" >NUEVA CONTRASE&Ntilde;A: &nbsp;</td>
          <td align="left" >
		  			<input type="password" name="cont_nueva"  style="text-transform:uppercase"  
					onKeyPress="if(!letraynum(event.keyCode)) event.returnValue=false;"  class="campo_01"
					onchange="this.value=mayuscula(this.value)" maxlength="20">
			</td>
        </tr>
        <tr  class="printfont"  >
          <td align="left" bgcolor="#E5E8EF" >CONFIRMACI&Oacute;N DE CONTRASE&Ntilde;A: &nbsp;</td>
          <td align="left" >
		  			<input type="password" name="cont_conf_nueva"  style="text-transform:uppercase"  
					onKeyPress="if(!letraynum(event.keyCode)) event.returnValue=false;"  class="campo_01"
					onchange="this.value=mayuscula(this.value)" maxlength="20">
			</td>
        </tr>
	</table>	
	<% if(strAccion.equals("mal_contrasena")) { %>
	<br/><br/>
		<span class="alerta"><b>LA CONTRASE&Ntilde;A PROPORCIONADA NO COINCIDE CON LA DE ACCESO DEL USUARIO. <br/>POR FAVOR VERIFIQUE.</b></span>
	<br/><br/>
	<% } %>
	<br/>
	<% 
			if(strTipoContrasena.equals("3"))
				{
	%>
				<input type="button" name="aceptar" value="REGRESAR" 
				onClick="document.forms[0].action='<%=(action)%>'; document.forms[0].submit()" class="estilo">
	<%		} 		%>			
			<input type="button" name="guardar" value="CAMBIAR CONTRASEÑA" onClick="valida_cambio_contrasena()" class="estilo">
			</form>
	<% } else { 
	%>
	<br/>
		<b>
			<span class="alerta">
                            <br/>LA CONTRASE&Ntilde;A HA SIDO MODIFICADA EXIT&Oacute;SAMENTE.  <br/> PRESIONE EL BOT&Oacute;N PARA CONTINUAR.
			</span>			
		</b>	
<br/><br/>
		<form name="form1" method="post">
				<input type="button" name="aceptar" value="CONTINUAR" 
				onClick="document.forms[0].action='<%=(action)%>'; document.forms[0].submit()" class="estilo">
</form>
	<% } %>		
		</center>	
        <jsp:include page="../template/piePaginaSub.jsp" />
	</body>	
</html>
	<% 
	}
	%>
