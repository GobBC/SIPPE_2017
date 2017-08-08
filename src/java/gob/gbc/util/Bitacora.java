/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.util;

/**
 *
 * @author gvillasenor
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Bitacora {
    public static final int TIPO_ERROR = 1;
    public static final int TIPO_TRANSACCION = 2;
    public static final int TIPO_DEBUG = 3;
    
    private String strUbicacion = "";
    private String strInformacion = "";
    private String strServer = "";
    private DatosBitConfig datosConfig;
    private int iTipoBitacora = 0;
    private String strSeparador = System.getProperty("file.separator");
    private String strSaltoLinea = System.getProperty("line.separator") ;

    /** Creates a new instance of Bitacora */
    public Bitacora()  throws IOException {
        datosConfig = new DatosBitConfig("Bitacora.properties");
    }
    public String getStrInformacion() {
        return strInformacion;
    }

    public void setStrInformacion(String strInformacion) {
        this.strInformacion = strInformacion;
    }

    public String getStrUbicacion() {
        return strUbicacion;
    }

    public void setStrUbicacion(String strUbicacion) {
        this.strUbicacion = strUbicacion;
    }

    public String getStrServer() {
        return strServer;
    }

    public void setStrServer(String strServer) {
        this.strServer = strServer;
    }

    public int getITipoBitacora() {
        return iTipoBitacora;
    }

    public void setITipoBitacora(int iTipoBitacora) {
        this.iTipoBitacora = iTipoBitacora;
    }

    public String getStrSeparador() {
        return strSeparador;
    }

    public void setStrSeparador(String strSeparador) {
        this.strSeparador = strSeparador;
    }

    public String getStrSaltoLinea() {
        return strSaltoLinea;
    }

    public void setStrSaltoLinea(String strSaltoLinea) {
        this.strSaltoLinea = strSaltoLinea;
    }

    public boolean esProduccion(){
       if(getStrServer().equals(datosConfig.getServerDominio()))
          return true;
       return false;
    }
    public void setStrInformacion(Exception ex, Throwable thClase) {
      StackTraceElement[] rutaError = thClase.getStackTrace();
      String strError = "";
      if (rutaError.length >= 2) {
            strError = rutaError[1].getClassName() + "." + rutaError[1].getMethodName();
      }
      strError = strError + getStrSaltoLinea() + ex.toString();
      setStrInformacion(strError);
    }
    public void setStrInformacion(Exception ex, String strJSP) {
      String strError = "";
      if (!strJSP.equals("")) {
            strError = strJSP;
      }
      strError = strError + getStrSaltoLinea() + ex.toString();
      setStrInformacion(strError);
    }

    public String getNombreArchivo(int iTipoBitacora, boolean blProduccion){
        String strNombreArchivo = "";
        switch(iTipoBitacora){
            case 1: //error
                if(blProduccion)
                    strNombreArchivo = datosConfig.getStrArchivoErrorProd();
                else    
                    strNombreArchivo = datosConfig.getStrArchivoErrorDesar();
            break;    
            case 2: //transaccion
                if(blProduccion)
                    strNombreArchivo = datosConfig.getStrArchivoTransaccionProd();
                else    
                    strNombreArchivo = datosConfig.getStrArchivoTransaccionDesar();
            break;    
            case 3: //debug
                if(blProduccion)
                    strNombreArchivo = datosConfig.getStrArchivoDebugProd();
                else    
                    strNombreArchivo = datosConfig.getStrArchivoDebugDesar();
            break;    
        }
        return strNombreArchivo;
    }
    public String getUbicacionArchivo(boolean blProduccion){
        String strUbicacionArchivo = "";
            if(blProduccion)
                strUbicacionArchivo = datosConfig.getStrUbicacionArchivoProd();
            else
                strUbicacionArchivo = datosConfig.getStrUbicacionArchivoDesar();
        return strUbicacionArchivo;
    }

    public void grabaBitacora() {
        try{
            FileWriter fwArchivo;
            boolean blResultado = false;
            boolean blProduccion = esProduccion();
            String strNombreArchivo = getNombreArchivo(getITipoBitacora(), blProduccion);
            String strUbicacionArchivo = getUbicacionArchivo(blProduccion);

            if(strUbicacionArchivo.equals(""))
                strUbicacionArchivo = getStrUbicacion() + getStrSeparador() + "logs" + getStrSeparador();

            String strNombreArchivoFinal = strUbicacionArchivo;
            strNombreArchivoFinal = strNombreArchivoFinal + getDateTime("yyyy");
            blResultado = (new File(strNombreArchivoFinal)).mkdir();
            strNombreArchivoFinal = strNombreArchivoFinal + getStrSeparador() + getDateTime("MM");
            blResultado = (new File(strNombreArchivoFinal)).mkdir();
            strNombreArchivoFinal = strNombreArchivoFinal + getStrSeparador() + strNombreArchivo + getDateTime() +".log";
            blResultado = creaArchivo(strNombreArchivoFinal);
            
            fwArchivo = new FileWriter(strNombreArchivoFinal,true);
            BufferedWriter out = new BufferedWriter(fwArchivo);
            out.write(getStrSaltoLinea()+"*****"+getDateTime("hh:mm:ss")+"*****"+getStrSaltoLinea());
            out.write(getStrInformacion());
            out.close();
        }catch (Exception e){//Catch exception if any
          System.err.println("Error: " + e.getMessage());
        }
    }
    
    private String getDateTime() {
        DateFormat dfFormatoFecha = new SimpleDateFormat("yyMMdd");
        Date dtFecha = new Date();
        
        return dfFormatoFecha.format(dtFecha);
    }
    
    private String getDateTime(String strFormato) {
        //hh:mm:ss aaa -> 12:00:00 PM
        //yyyy         -> 2007 
        //MM          -> 01
        DateFormat dfFormatoFecha = new SimpleDateFormat(strFormato);
        
        Date dtFecha = new Date();
        
        return dfFormatoFecha.format(dtFecha);
    }
    
    private boolean creaArchivo(String strNombreArchivo) {
        boolean blResultado = false;
        
        try {
            File fArchivo = new File(strNombreArchivo);
            String strPathAbsoluto = fArchivo.getAbsolutePath();
            // Crea el strNombreArchivo en caso de no existir
            blResultado = fArchivo.createNewFile();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        
        return blResultado;
    }



}
