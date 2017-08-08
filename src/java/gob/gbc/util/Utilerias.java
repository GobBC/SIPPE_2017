/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.util;

/**
 *
 * @author vhernandez
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
/*qr*/
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;

public class Utilerias {

    public static final int RECALENDARIZACION = 4;
    public static final int REPROGRAMACION = 3;
    public static final int AMPLIACIONES = 1;
    public static final int TRANSFERENCIAS = 2;

    public static boolean existeParametro(String strNombreParam, HttpServletRequest request) {
        if (request.getParameter(strNombreParam) != null
                && !request.getParameter(strNombreParam).equals("")
                && !request.getParameter(strNombreParam).equals("undefined")) {
            return true;
        }
        return false;
    }
    
    public static String getParametroString(String strNombreParam, HttpServletRequest request) {
        String cadena = null;
        if(existeParametro(strNombreParam, request)){
            try {
                cadena = request.getParameter(strNombreParam);
                byte[] bytes1252 = cadena.getBytes("ISO-8859-1");
                cadena = new String(bytes1252,"UTF-8");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Utilerias.class.getName()).log(Level.SEVERE, null, ex);
            } catch(Exception ex){
                Logger.getLogger(Utilerias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
        return cadena;
    }

    public static String getStringValueProyeccion(Cell relLaboral, boolean isString) {
        if (relLaboral.getCellType() == Cell.CELL_TYPE_BLANK) {
            return "0";
        } else if (relLaboral.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (isString) {
                return String.valueOf(new Double(relLaboral.getNumericCellValue()));
            } else {
                return String.valueOf(new Double(relLaboral.getNumericCellValue()).intValue());
            }
        } else if (relLaboral.getCellType() == Cell.CELL_TYPE_STRING) {
            return relLaboral.getStringCellValue();
        } else {
            return "0";
        }
    }

    public static boolean datoCapturado(String strDato) {
        if (strDato == null) {
            return false;
        }
        if (strDato.trim().compareTo("") == 0) {
            return false;
        }
        return true;

    }

    public static ArrayList leeArchivo(String strRuta) throws IOException, Exception {
        ArrayList arDatosArchivo = null;
        InputStream ips = null;
        InputStreamReader ipsr = null;
        BufferedReader br = null;
        try {
            ips = new FileInputStream(strRuta);
            ipsr = new InputStreamReader(ips);
            br = new BufferedReader(ipsr);
            String line;
            if (br != null) {
                arDatosArchivo = new ArrayList();
            }
            while ((line = br.readLine()) != null) {
                arDatosArchivo.add(line);
            }
            br.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (ips != null) {
                ips.close();
            }
            if (ipsr != null) {
                ipsr.close();
            }
            if (br != null) {
                br.close();
            }
        }
        return arDatosArchivo;
    }

    public static String retiraMascaraDinero(String strDato) {
        strDato = strDato.replace(',', ' ');
        strDato = strDato.replace('$', ' ');
        strDato = strDato.replaceAll("\\s", "");
        return strDato;
    }

    public static final String getDoubleFormato(double numero) {
        BigDecimal format = new BigDecimal(numero);
        format.setScale(2);
        return format.toString();
    }

    public static final String reemplazaEspeciales(String strCadena) {
        strCadena = strCadena.replace('ó', 'o');
        strCadena = strCadena.replace('Ó', 'O');
        strCadena = strCadena.replace('á', 'a');
        strCadena = strCadena.replace('Á', 'A');
        strCadena = strCadena.replace('é', 'e');
        strCadena = strCadena.replace('É', 'E');
        strCadena = strCadena.replace('í', 'i');
        strCadena = strCadena.replace('Í', 'I');
        strCadena = strCadena.replace('ú', 'u');
        strCadena = strCadena.replace('Ú', 'U');
        strCadena = strCadena.replace('\\', ' ');
        strCadena = strCadena.replace('/', ' ');
        strCadena = strCadena.toUpperCase();
        return strCadena;
    }
    private static final String[] UNIDADES = {"", "un ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve "};
    private static final String[] DECENAS = {"diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciseis ",
        "diecisiete ", "dieciocho ", "diecinueve", "veinte ", "treinta ", "cuarenta ",
        "cincuenta ", "sesenta ", "setenta ", "ochenta ", "noventa "};
    private static final String[] CENTENAS = {"", "ciento ", "doscientos ", "trecientos ", "cuatrocientos ", "quinientos ", "seiscientos ",
        "setecientos ", "ochocientos ", "novecientos "};

    public static String ConvertiraLetra(String numero, boolean mayusculas) {
        String literal = "";
        String parte_decimal;
        //si el numero utiliza (.) en lugar de (,) -> se reemplaza
        numero = numero.replace('.', ',');
        //si el numero no tiene parte decimal, se le agrega ,00
        if (numero.indexOf(",") == -1) {
            numero = numero + ",00";
        }
        //se valida formato de entrada -> 0,00 y 999 999 999,00
        if (Pattern.matches("\\d{1,9},\\d{1,2}", numero)) {
            //se divide el numero 0000000,00 -> entero y decimal
            String Num[] = numero.split(",");
            //de da formato al numero decimal
            parte_decimal = "Pesos " + Num[1] + "/100 M.N.";
            //se convierte el numero a literal
            if (Integer.parseInt(Num[0]) == 0) {//si el valor es cero
                literal = "cero ";
            } else if (Integer.parseInt(Num[0]) > 999999) {//si es millon
                literal = getMillones(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 999) {//si es miles
                literal = getMiles(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 99) {//si es centena
                literal = getCentenas(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 9) {//si es decena
                literal = getDecenas(Num[0]);
            } else {//sino unidades -> 9
                literal = getUnidades(Num[0]);
            }
            //devuelve el resultado en mayusculas o minusculas
            if (mayusculas) {
                return (literal + parte_decimal).toUpperCase();
            } else {
                return (literal + parte_decimal);
            }
        } else {//error, no se puede convertir
            return literal = null;
        }
    }

    /* funciones para convertir los numeros a literales */
    private static String getUnidades(String numero) {// 1 - 9
        //si tuviera algun 0 antes se lo quita -> 09 = 9 o 009=9
        String num = numero.substring(numero.length() - 1);
        return UNIDADES[Integer.parseInt(num)];
    }

    private static String getDecenas(String num) {// 99                        
        int n = Integer.parseInt(num);
        if (n < 10) {//para casos como -> 01 - 09
            return getUnidades(num);
        } else if (n > 19) {//para 20...99
            String u = getUnidades(num);
            if (u.equals("")) { //para 20,30,40,50,60,70,80,90
                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8];
            } else {
                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8] + "y " + u;
            }
        } else {//numeros entre 11 y 19
            return DECENAS[n - 10];
        }
    }

    private static String getCentenas(String num) {// 999 o 099
        if (Integer.parseInt(num) > 99) {//es centena
            if (Integer.parseInt(num) == 100) {//caso especial
                return " cien ";
            } else {
                return CENTENAS[Integer.parseInt(num.substring(0, 1))] + getDecenas(num.substring(1));
            }
        } else {//por Ej. 099 
            //se quita el 0 antes de convertir a decenas
            return getDecenas(Integer.parseInt(num) + "");
        }
    }

    private static String getMiles(String numero) {// 999 999
        //obtiene las centenas
        String c = numero.substring(numero.length() - 3);
        //obtiene los miles
        String m = numero.substring(0, numero.length() - 3);
        String n = "";
        //se comprueba que miles tenga valor entero
        if (Integer.parseInt(m) > 0) {
            n = getCentenas(m);
            return n + "mil " + getCentenas(c);
        } else {
            return "" + getCentenas(c);
        }

    }

    private static String getMillones(String numero) { //000 000 000        
        //se obtiene los miles
        String miles = numero.substring(numero.length() - 6);
        //se obtiene los millones
        String millon = numero.substring(0, numero.length() - 6);
        String n = "";
        if (millon.length() > 1) {
            n = getCentenas(millon) + "millones ";
        } else {
            n = getUnidades(millon) + "millon ";
        }
        return n + getMiles(miles);
    }

    public static void borraArchivos(String sRuta, int iDias) {
        File directory = new File(sRuta);

        if (directory.exists()) {

            File[] listFiles = directory.listFiles();
            long purgeTime = System.currentTimeMillis() - (iDias * 24 * 60 * 60 * 1000);
            for (File listFile : listFiles) {
                if (listFile.lastModified() < purgeTime) {
                    listFile.delete();
                }
            }
        }
    }

    public static void borrarArchivosTemp(String pathLogico) throws IOException {

        String strResultado = new String();
        //String pathLogico = "";
        Archivo archivo = new Archivo();


        //pathLogico = "/temp";
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        File directorio = new File(pathLogico);
        String[] listaDirectorio = directorio.list();
        if (listaDirectorio.length > 0) {
            for (int x = 0; x < listaDirectorio.length; x++) {
                File fDatos = new File(pathLogico + File.separatorChar + listaDirectorio[x]);
                Date d = new Date(fDatos.lastModified());
                Date d2 = calendar.getTime();
                if (d.before(d2)) {
                    File directory = new File(pathLogico + File.separatorChar + listaDirectorio[x]);
                    archivo.delete(directory);
                }
            }
        }

    }

    public static void escribeLinea(String strRuta, String strPaquete, String sExtension, String strCadena) throws Exception {
        PrintWriter outs = null;
        outs = new PrintWriter(new BufferedWriter(new FileWriter(strRuta + "/" + strPaquete + "." + sExtension, true)));
        outs.println(new String(strCadena.getBytes("ISO-8859-15")));
        outs.close();
        outs.flush();
        outs = null;//libero out
    }

    public static void escribeLinea2(String strRuta, String strPaquete, String sExtension, String strCadena) throws Exception {
        File file = new File(strRuta + "/" + strPaquete + "." + sExtension);

        strCadena = strCadena.replace("\n", " ").replace("\r", " ").replace("\t", " ");
        strCadena = strCadena.replace('|', '\t');

        if (file.exists()) {
            strCadena = "\r\n" + strCadena;
        }
        
        FileOutputStream fos = new FileOutputStream(file, true);
        Writer out = new OutputStreamWriter(fos, "ISO-8859-1");

        out.write(strCadena);
        out.flush();
        out.close();
        out = null;//libero out
    }
}
