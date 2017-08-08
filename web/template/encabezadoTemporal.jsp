
<%@page import="gob.gbc.util.Propiedades"%>
<%
Propiedades prop = new Propiedades("General.properties");
String funcionesPOA = "librerias/funciones-poa.js?" + prop.getVersion();
%>

<head>
    <title>SISTEMA DEL PROCESO INTEGRAL DE PROGRAMACIÓN Y PRESUPUESTACIÓN ESTATAL (SIPPE)</title>
    <link href="librerias/site.css" rel="stylesheet" type="text/css" />
    <link href="librerias/oficial.css" rel="stylesheet" type="text/css" />
    <link href="librerias/portal.css" rel="stylesheet" type="text/css" />
    <link href="librerias/css/bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="librerias/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="template/css/estilo.css" rel="stylesheet" type="text/css" />   
    <link href="librerias/js/jquery-ui/jquery-ui.css" rel="stylesheet" type="text/css" />  
    

    <%--
    <script src="librerias/jquery-ui.js" type="text/javascript"></script>  
    --%>
    <%
        int year = 0;
        if (request.getParameter("logout") != null
                && !request.getParameter("logout").equals("")) {
    %>
    <meta http-equiv="refresh" content="3; url=index.jsp">
    <%        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        request.setCharacterEncoding("UTF-8");
    %>
</head>
<body>
    <div class="main_content">  
        <div class="header">
        </div>
        <div class='banner-azul'>
            <%
                if (year > 0) {
                    out.print("SISTEMA DEL PROCESO INTEGRAL DE PROGRAMACIÓN Y PRESUPUESTACIÓN ESTATAL (SIPPE) " + year);
                } else {
                    out.print("SISTEMA DEL PROCESO INTEGRAL DE PROGRAMACIÓN Y PRESUPUESTACIÓN ESTATAL (SIPPE) ");
                }
            %> 
        </div>
        <div class="content">
