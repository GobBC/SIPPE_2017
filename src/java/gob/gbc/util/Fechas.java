package gob.gbc.util;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 *
 * @author joquintero
 */
public final class Fechas {
    
    public static final String FORMATO_CORTO = "dd/MM/yyyy";
    public static final String FORMATO_CORTO_MES_COMPLETO = "dd/MMMM/yyyy";
    public static final String FORMATO_LARGO = "dd 'de' MMMM 'de' yyyy";
    public static final String FORMATO_FECHA_HORA = "dd/MM/yyyy H:mm:ss a";
    public static final String FORMATO_YYMMDD = "yyMMdd";
    
    
    public String getFechaFormato(String strFecha,String formatoAnterior,String formatoNuevo) throws ParseException, Exception{
        String strResultado = null;
        
        if(formatoAnterior == null || formatoAnterior.equals("")){
            throw new Exception("Formato anterior no contiene valor.");
        }
        
        if(formatoNuevo == null || formatoNuevo.equals("")){
            throw new Exception("Formato nuevo no contiene valor.");
        }
        
        SimpleDateFormat sdfAnterior = new SimpleDateFormat(formatoAnterior,new Locale("es","MX"));
        SimpleDateFormat sdfNuevo = new SimpleDateFormat(formatoNuevo,new Locale("es","MX"));
        
        java.util.Date fecha = sdfAnterior.parse(strFecha);
        
        if(sdfNuevo.format(fecha) != null){
            strResultado = sdfNuevo.format(fecha);
        }
        
        
        return strResultado;
    }
    
    public String getFechaFormato(java.sql.Date dateFecha, String formatoFecha) throws ParseException, Exception{
        String strResultado = null;
        
        if(formatoFecha == null || formatoFecha.equals("")){
            throw new Exception("Formato fecha no contiene valor.");
        }
        
        strResultado = DateFormatUtils.format(dateFecha, formatoFecha);
        
        return strResultado;
    }
    
    public String getFechaFormato(Calendar calFecha, String formatoFecha) throws ParseException, Exception{
        String strResultado = null;
        
        if(formatoFecha == null || formatoFecha.equals("")){
            throw new Exception("Formato fecha no contiene valor.");
        }
        
        strResultado = DateFormatUtils.format(calFecha, formatoFecha);
        
        return strResultado;
    }
}
