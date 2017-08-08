/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.estandar;

/**
 *
 * @author vhernandez
 */
public class Proceso {
    private short shProceso=0;
    private String strDescripcion="";
    private String strUrl="";
    private String strIcono="";
    private String strEtiqueta="";
    private String strApoyo="";
    private short shTipo=0;
    private short shCveEstatus=0;   
    private String strUbicacion="";    
    private String strServer="";
    private String error="";
    /*private String strPagina="";*/
    /*private String strPassword="";*/
    private String strIconoMenu="";
    private int inCveMenu;   
    private String strDescMenu;   
    private int inOrden;   
    
       // <editor-fold defaultstate="collapsed" desc="GETS/SETS DE OBJETO">
    public short getShProceso() {return shProceso;}
    
    public void setshProceso(short shProceso) {this.shProceso = shProceso;}
    
    public String getStrDescripcion() {return strDescripcion;}
    
    public void setStrDescripcion(String strDescripcion) {this.strDescripcion = strDescripcion;}
    
    public String getStrIcono() {return strIcono;}
    
    public void setStrIcono(String strIcono) {this.strIcono = strIcono;}
    
    public String getStrEtiqueta() {return strEtiqueta;}
    
    public void setStrEtiqueta(String strEtiqueta) {this.strEtiqueta = strEtiqueta;}
    
    public String getStrApoyo() {return strApoyo;}
    
    public void setStrApoyo(String strApoyo) {this.strApoyo = strApoyo;}
    
    public short getShTipo() {return shTipo;}
    
    public void setShTipo(short shTipo) {this.shTipo = shTipo;}
    
    public short getShCveEstatus() {return shCveEstatus;}
    
    public void setShCveEstatus(short shCveEstatus) {this.shCveEstatus = shCveEstatus;}                 
    
    public String getError() {return error;}
       
/*    public String getStrPagina() {return strPagina;}
    
    public void setStrPagina(String strPagina) {this.strPagina = strPagina;}*/
           
    /*public String getStrQuery() {return query;}
    
    public void setStrQuery(String strQuery) {this.query = strQuery;}         */
    
    public String getStrUbicacion() {return strUbicacion;}
    
    public void setStrUbicacion(String strUbicacion) {this.strUbicacion = strUbicacion;}
    
    public String getStrServer() {return strServer;}
    
    public void setStrServer(String strServer) {this.strServer = strServer;}
    
     public String getStrUrl() {return strUrl;}
    
    public void setStrUrl(String strUrl) {this.strUrl = strUrl;}

    public int getInCveMenu() {
        return inCveMenu;
    }

    public void setInCveMenu(int inCveMenu) {
        this.inCveMenu = inCveMenu;
    }

    public String getStrDescMenu() {
        return strDescMenu;
    }
    
    public void setStrDescMenu(String strDescMenu) {
        this.strDescMenu = strDescMenu;
    }

    public int getInOrden() {
        return inOrden;
    }

    public void setInOrden(int inOrden) {
        this.inOrden = inOrden;
    }
    //</editor-fold>
    public Proceso(String strServer,String strUbicacion)
    {     
        setStrServer(strServer);
        setStrUbicacion(strUbicacion);

    }
    public Proceso()
    {     
        setStrServer(strServer);
        setStrUbicacion(strUbicacion);

    }

    /**
     * @return the strIconoMenu
     */
    public String getStrIconoMenu() {
        return strIconoMenu;
    }

    /**
     * @param strIconoMenu the strIconoMenu to set
     */
    public void setStrIconoMenu(String strIconoMenu) {
        this.strIconoMenu = strIconoMenu;
    }
   
  }