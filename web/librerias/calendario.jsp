<LINK REL=StyleSheet HREF="oficial.css" TYPE="text/css" MEDIA="screen">
<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="error.jsp" %>
<%@page import = "java.util.GregorianCalendar"%>
<%@page import = "java.util.*"%>
<%
	String strFecha="";
	if(request.getParameter("strFecha")!=null)
		strFecha=(String)request.getParameter("strFecha");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script src="funciones.js"></script>
<script language="javascript">
miFecha = new String(); 
miPlaca = new String(); 

function strFecha(a){ 
miFecha = a 
mandaValor(); 
} 
  
function mandaValor(){ 
	
if(document.form1.fecha_regresa.value=="uno")
	window.opener.document.forms[0].strFecha.value=miFecha	
if(document.form1.fecha_regresa.value=="dos") {
	window.opener.document.forms[0].strFechaInicio.value=verificaFechaAlerta(miFecha,'1');
	}
if(document.form1.fecha_regresa.value=="tres") {
	window.opener.document.forms[0].strFechaFin.value=verificaFechaAlerta(miFecha	,'2');
	}
window.close()
}
</script>

	<html>
		<head>
			<title>Calendario</title>			
		</head>

<body><center>
<form name="form1"><input type="hidden" name="fecha_regresa" value="<%=(strFecha)%>"/></form>

<%
int index = 0;
Calendar calendar = new GregorianCalendar();
java.util.Date Calendario = new java.util.Date();//out.print(Calendario);
calendar.set(Integer.parseInt(request.getParameter("strAnio")),Integer.parseInt(request.getParameter("mes")),1);

Calendario.setDate(1);
Calendario.setMonth(Integer.parseInt(request.getParameter("mes")));
Calendario.setYear(Integer.parseInt(request.getParameter("strAnio"))-1900);

// ARREGLOS
String day_of_week[] = {"Dom","Lun","Mar","Mie","Jue","Vie","Sab"};
String month_of_year[] = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
int week_day [] = {1,2,3,4,5,6,7};

//  DECLARACIÓN DE VARIABLES
int day = 0;

int DAYS_OF_WEEK = 7;    
int year=0, month=0, today=0, weekday=0, minimo=0, todayasd = 0, horalimite=0;
year = calendar.get(Calendar.YEAR);
month = calendar.get(Calendar.MONTH);
today = calendar.get(Calendar.DATE);
weekday = calendar.get(Calendar.DAY_OF_WEEK);
minimo = calendar.getMinimum(Calendar.DAY_OF_MONTH);
int mes_mas = 0, mes_menos=0;
mes_mas = month+1;
mes_menos = month-1;
int anio_mas=0, anio_menos=0;
anio_mas=year+1;
anio_menos=year-1;

String mes_calendario = null;
mes_calendario = Integer.toString(mes_mas);
if(mes_calendario.length() == 1){
mes_calendario = "0"+mes_calendario;
}
String cal;    
String TR_start ="<tr>";
String TR_end = "</tr>";
String TD_start = "<td width=\"30\"><center>";
String TD_end = "</center></td>";
String href_end = "</a>";
	cal =  "<table border=1 cellspacing=0 cellpadding=0 bordercolor=AAAAAA><tr><td>";
	cal += "<table border=0 cellspacing=0 cellpadding=2><tr>";
	cal += "<td bgcolor=\"#CCCCCC\"><a href=\"calendario.jsp?strAnio="+ anio_menos +"&mes="+ month +"&strFecha="+ request.getParameter("strFecha") +"\"><font size=\"-2\" color=\"#989523\">"+anio_menos+"</font></a></td>";
	cal += "<td bgcolor=\"#CCCCCC\"><a href=\"calendario.jsp?strAnio="+ year +"&mes="+ mes_menos +"&strFecha="+ request.getParameter("strFecha") +"\"><font size=\"-2\" color=\"#003366\"><<</font></a></td><td colspan=\"" + 3 + "\" bgcolor=\"#CCCCCC\"><center><b><font size=\"-2\">";
	cal += month_of_year[month]  + "   " + year + "</font></b>" + TD_end + "<td bgcolor=\"#CCCCCC\"><a href=\"calendario.jsp?strAnio="+ year +"&mes="+ mes_mas +"&strFecha="+ request.getParameter("strFecha") +"\"><font size=\"-2\" color=\"#003366\">>></font></a></td>";
	cal += "<td bgcolor=\"#CCCCCC\"><a href=\"calendario.jsp?strAnio="+ anio_mas +"&mes="+ month +"&strFecha="+ request.getParameter("strFecha") +"\"><font size=\"-2\" color=\"#989523\">" + anio_mas+"</font></a></td>" + TR_end;
	cal += TR_start;

for(index=0; index < DAYS_OF_WEEK; index++)
	{
		cal += TD_start + "<font size=\"-2\">"+day_of_week[index] + "</font>"+TD_end;
	}

	cal += TR_end;
	cal += TR_start;

for(index=0; index < Calendario.getDay(); index++)
	{
		cal += TD_start + "  " + TD_end;
	}

index = 0;
int mescal = Calendario.getMonth()+1;
String fecha_creacion = month+1+"/"+today+"/"+year;
do
	{
		Calendario.setDate(minimo);
		if( minimo > index )
		{
			todayasd = Calendario.getDay();
			if(todayasd == 0)
			cal += TR_start;
		
			if(todayasd != DAYS_OF_WEEK)
			{		
				int minimomasuno = today+1;
				int todaymasuno = today+1;
				String dia_minimo = Integer.toString(minimo);
				if(dia_minimo.length() == 1){
				dia_minimo = "0"+minimo;
				}
				if(todayasd == 0){
				cal += TD_start + "<input type='button'  onClick=\"strFecha('"+dia_minimo+"/"+mes_calendario+"/"+year+"')\" value='" + minimo + "' class='boton_calendario'>" + TD_end;
				}else if ( minimo == 1 && mescal == 1 || minimo == 16 && mescal == 9 || minimo == 1 && mescal == 5 || minimo==5 && mescal == 5 || minimo == 20 && mescal == 11 || minimo == 25 && mescal == 12)
					cal += TD_start + "<input type='button' onClick=\"strFecha('"+ dia_minimo+"/"+mes_calendario+"/"+year +"')\" value='"+ minimo + "' class='boton_calendario'>" + TD_end;
				else if( today==minimo) 
					cal += TD_start + "<input type='button' onClick=\"strFecha('"+ dia_minimo+"/"+mes_calendario+"/"+year +"')\" value='"+ minimo + "' class='boton_calendario'>" + TD_end;
				else if (today>minimo)
					cal += TD_start + "<input type='button' onClick=\"strFecha('"+ dia_minimo+"/"+mes_calendario+"/"+year +"')\" value='"+ minimo + "' class='boton_calendario'>" + TD_end;
				else if (today<minimo)
					cal += TD_start + "<input type='button' onClick=\"strFecha('"+ dia_minimo+"/"+mes_calendario+"/"+year +"')\" value='"+ minimo + "' class='boton_calendario'>" + TD_end;
			}
			if(todayasd == DAYS_OF_WEEK-1)
				cal += TR_end;
		}
		minimo = minimo + 1;
		index ++;
	}while (minimo <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	
	cal += "</table></td></tr></table>";

	out.print(cal);
%>
</center>
</body>
</html>
