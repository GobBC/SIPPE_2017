<%@page import="gob.gbc.util.Propiedades"%>
</div>
<div class="footer">
    <!--
    <div style="width:15%;display:inline-block;"><img src="template/imagenes/escudobn.jpg" alt="logo" width="72" height="89" /></div>
    -->
    <%
    Propiedades prop = new Propiedades("General.properties");
    String version = prop.getVersion();
    %>
    <div style="width:74%;display:inline-block">
        <table>
            <tr>
                <td width='78' valign="top" rowspan="2"><img src="template/imagenes/escudobn.jpg" alt="logo" width="72" height="89" /></td>
                <td width='782' height='18'></td>
            </tr>
            <tr>
                <td>
                    <table>
                        <tr>
                            <td class='derechosR'>
                                <div>Gobierno del Estado de Baja California - Algunos Derechos Reservados.  © 2015</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span class='derechosR'>Secretar&iacute;a de Planeaci&oacute;n y Finanzas</span>
                                <div class='derechosR'>
                                    <div align='left'></div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class='derechosR' height='12'>
                                Dudas sobre el portal:
                                <a class="linkpie" href="mailto:postmaster@baja.gob.mx ">
                                    postmaster@baja.gob.mx
                                </a>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>    
    <div class="col-lg-12 text-right" style="color:white">
        <label>Versi&oacute;n <%=version%></label>
    </div>
</div>

</div>
</body>

</html>                