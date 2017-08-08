/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.util;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author joquintero
 */
public final class Paginacion {
    
    private int iRegsxPagina = 15;
    
    private int iPaginaActual;
    
    private int iTotalPaginas;
    
    private int iTotalRegistros;
    
    private String strCadenaDatos;
    
    private int iOrdenar;
    
    private String strOrientacion;
    
    private int iRegistroInicio;
    
    private int iRegistroFinal;

    public Paginacion() {
    }

    public int getiRegsxPagina() {
        return iRegsxPagina;
    }

    public void setiRegsxPagina(int iRegsxPagina) {
        this.iRegsxPagina = iRegsxPagina;
    }

    public int getiPaginaActual() {
        return iPaginaActual;
    }

    public void setiPaginaActual(int iPaginaActual) {
        this.iPaginaActual = iPaginaActual;
    }

    public int getiTotalPaginas() {
        return iTotalPaginas;
    }

    public void setiTotalPaginas(int iTotalPaginas) {
        this.iTotalPaginas = iTotalPaginas;
    }

    public int getiTotalRegistros() {
        return iTotalRegistros;
    }

    public void setiTotalRegistros(int iTotalRegistros) {
        this.iTotalRegistros = iTotalRegistros;
    }

    public String getStrCadenaDatos() {
        return strCadenaDatos;
    }

    public void setStrCadenaDatos(String strCadenaDatos) {
        this.strCadenaDatos = strCadenaDatos;
    }

    public int getiOrdenar() {
        return iOrdenar;
    }

    public void setiOrdenar(int iOrdenar) {
        this.iOrdenar = iOrdenar;
    }

    public String getStrOrientacion() {
        return strOrientacion;
    }

    public void setStrOrientacion(String strOrientacion) {
        this.strOrientacion = strOrientacion;
    }

    public int getiRegistroInicio() {
        return iRegistroInicio;
    }

    public void setiRegistroInicio(int iRegistroInicio) {
        this.iRegistroInicio = iRegistroInicio;
    }

    public int getiRegistroFinal() {  
        return iRegistroFinal;
    }

    public void setiRegistroFinal(int iRegistroFinal) {
        this.iRegistroFinal = iRegistroFinal;
    }
    
    
    /**
     * Se encargar de preparara todas las variables necesarias para realizar una 
     * paginacion de una consulta, obtiene los valores por el request de los datos
     * enviados por el formularios.
     * 
     * @param request           
     * 
     * @return boolean      TRUE - Configuro todas las variables correctamente.
     *                      FALSE - Fallo en la configuracion.
     * 
     * @throws Exception 
     */
    public boolean configurar(HttpServletRequest request, int iTotalRegistros) throws Exception{
        boolean bResultado = false;
        
        if (request.getParameter("pagina") != null
                && !request.getParameter("pagina").equals("")
                && !request.getParameter("pagina").equals("undefined")) {
             setiPaginaActual(Integer.parseInt(request.getParameter("pagina")));
        }else{
            throw new Exception("Error al obtener el numero de la pagina a consultar del request  para paginar la tabla.");
        }
        
        if(request.getParameter("ordenar")!= null
                && !request.getParameter("ordenar").equals("")
                && !request.getParameter("ordenar").equals("undefined")){
            setiOrdenar(Integer.parseInt(request.getParameter("ordenar")));
        }else{
            throw new Exception("Error al obtener la opcion de ordenar a consultar del request para paginar la tabla.");
        }
        
        if (request.getParameter("orientacion")!= null
                && !request.getParameter("orientacion").equals("undefined")){
            setStrOrientacion(request.getParameter("orientacion"));
        }else{
            throw new Exception("Error al obtener la opcion de orientacion del request para paginar la tabla.");
        }
        
        if(request.getParameter("regsXpagina") != null
                && !request.getParameter("regsXpagina").equals("undefined")){
            setiRegsxPagina(Integer.parseInt(request.getParameter("regsXpagina")));
        }
        
        setiTotalRegistros(iTotalRegistros);
        
        /* Realizamos las operaciones necesarias para obtener la cantidad de paginas
         */
        int iTotalPaginas = 0;
        
        iTotalPaginas = Math.round(iTotalRegistros / getiRegsxPagina());

        if((iTotalRegistros % getiRegsxPagina())>0){
            iTotalPaginas++;
        }
        
        setiTotalPaginas(iTotalPaginas);
        
        
        /*Se realizan las operaciones para obtener los limites de los registros a
         consultar*/
        int tmpInicio = ((getiPaginaActual() - 1) * getiRegsxPagina()) + 1;
        int tmpFin = (tmpInicio + getiRegsxPagina()) - 1;
        
        setiRegistroInicio(tmpInicio);
        setiRegistroFinal(tmpFin);
    
        /*Validamos que tengamos todas las variables necesarias para paginar*/
        bResultado = isConfiguracionValida();
        
        return bResultado;
    }
    
    
    
    /**
     * Devuelve una estructura JSON con los datos necesarios para mostrar la informacion
     * en tabla utilizando el script de jquery.tabla.js, contiene los datos necesarios para
     * realizar la navegacion entre paginas, y la informacion a mostrar al usuario.
     * 
     * @param datos                 Informacion a mostrar en pantalla con estructura de matriz.
     * 
     * @return String               Estructura JSON con la informacion necesaria para generar una tabla.
     */
    public String getContenidoPaginado(String datos){
        String strContenido = "{totalRegistros:'"+getiTotalRegistros()+"',iPagina:'"+getiPaginaActual()+"',iTotalPaginas:'"+getiTotalPaginas()+"'," +
                              "arrDatos:"+datos+"}";
        
        return strContenido;
    }
    
    
    /**
     * Verifica que se cuente con los datos necesarios para realizar la paginacion.
     * 
     * @return boolean      TRUE - Si todas las variables tienen valor.
     *                      FALSE - Si no se establecieron algun valor a las variables.
     */
    private boolean isConfiguracionValida(){
        boolean bResultado = true;
        
        if(getiPaginaActual() == 0 || getiTotalPaginas() == 0 
                || getiTotalPaginas() == 0 || getiTotalRegistros() == 0
                || getiRegistroFinal() == 0 || getiRegistroInicio() == 0){
            bResultado = false;
        }
        
        return bResultado;
    }
}
