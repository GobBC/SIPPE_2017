/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.util;

import gob.gbc.entidades.Estimacion;
import gob.gbc.entidades.MovOficiosAccionReq;
import gob.gbc.entidades.Requerimiento;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author muribe
 */
public class Utileria {

    public static String homologarPalabra(String palabra) {
        palabra = palabra.toUpperCase();
        palabra = palabra.replace('Á', 'A');
        palabra = palabra.replace('É', 'E');
        palabra = palabra.replace('Í', 'I');
        palabra = palabra.replace('Ó', 'O');
        palabra = palabra.replace('Ú', 'U');
        return (palabra);
    }

    public static String getStringMes(int mes) {
        String mesPalabra = new String();
        switch (mes) {
            case 1:
                mesPalabra = "Ene";
                break;
            case 2:
                mesPalabra = "Feb";
                break;
            case 3:
                mesPalabra = "Mar";
                break;
            case 4:
                mesPalabra = "Abr";
                break;
            case 5:
                mesPalabra = "May";
                break;
            case 6:
                mesPalabra = "Jun";
                break;
            case 7:
                mesPalabra = "Jul";
                break;
            case 8:
                mesPalabra = "Ago";
                break;
            case 9:
                mesPalabra = "Sep";
                break;
            case 10:
                mesPalabra = "Oct";
                break;
            case 11:
                mesPalabra = "Nov";
                break;
            case 12:
                mesPalabra = "Dic";
                break;
            default:
                mesPalabra = "Ene";
                break;
        }
        return mesPalabra;
    }

    public static int getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
        String string = bigDecimal.stripTrailingZeros().toPlainString();
        int index = string.indexOf(".");
        return index < 0 ? 0 : string.length() - index - 1;
    }

    public static boolean isTipoOficioRequerido(String estatus) {
        boolean result = false;
        if (estatus.equals("X") || estatus.equals("Y") || estatus.equals("Z") || estatus.equals("V") || estatus.equals("R")) {
            result = false;
        }
        if (estatus.equals("T") || estatus.equals("A") || estatus.equals("C") || estatus.equals("K")) {
            result = true;
        }
        return result;
    }

    public static String getCalendarizacionAccionReq(Requerimiento req, String disabledConsulta, boolean isActual, int mes, boolean enero, int[] meses, boolean enableAll) {

        NumberFormat numberF = NumberFormat.getInstance(Locale.US);
        String strResultado = new String();
        String sufijo = new String();
        String clase = new String();
        if (!isActual) {
            mes = 11;
        }
        if (disabledConsulta.isEmpty()) {
            sufijo = "recal";
            clase = "capt-mes";
        }
        strResultado += "<div id='divMese'>";

        if (enableAll) {

            strResultado += "<div> Ene <input type='text' id='" + sufijo + "inpTxtEne' class='" + clase + "'  value='" + numberF.format(req.getCantEne()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtEne\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtEne\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div> Feb <input type='text' id='" + sufijo + "inpTxtFeb' class='" + clase + "'  value='" + numberF.format(req.getCantFeb()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtFeb\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtFeb\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div> Mar <input type='text' id='" + sufijo + "inpTxtMar' class='" + clase + "'  value='" + numberF.format(req.getCantMar()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtMar\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtMar\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div> Abr <input type='text' id='" + sufijo + "inpTxtAbr' class='" + clase + "'  value='" + numberF.format(req.getCantAbr()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtAbr\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtAbr\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div> May <input type='text' id='" + sufijo + "inpTxtMay' class='" + clase + "'  value='" + numberF.format(req.getCantMay()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtMay\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtMay\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div> Jun <input type='text' id='" + sufijo + "inpTxtJun' class='" + clase + "'  value='" + numberF.format(req.getCantJun()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtJun\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtJun\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div> Jul <input type='text' id='" + sufijo + "inpTxtJul' class='" + clase + "'  value='" + numberF.format(req.getCantJul()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtJul\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtJul\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div> Ago <input type='text' id='" + sufijo + "inpTxtAgo' class='" + clase + "'  value='" + numberF.format(req.getCantAgo()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtAgo\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtAgo\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div> Sep <input type='text' id='" + sufijo + "inpTxtSep' class='" + clase + "'  value='" + numberF.format(req.getCantSep()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtSep\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtSep\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div> Oct <input type='text' id='" + sufijo + "inpTxtOct' class='" + clase + "'  value='" + numberF.format(req.getCantOct()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtOct\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtOct\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div> Nov <input type='text' id='" + sufijo + "inpTxtNov' class='" + clase + "'  value='" + numberF.format(req.getCantNov()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtNov\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtNov\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div> Dic <input type='text' id='" + sufijo + "inpTxtDic' class='" + clase + "'  value='" + numberF.format(req.getCantDic()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtDic\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtDic\")'   " + disabledConsulta + "  /> </div>";

        } else {

            strResultado += "<div> Ene <input type='text' id='" + sufijo + "inpTxtEne' class='" + clase + "'  value='" + numberF.format(req.getCantEne()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtEne\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtEne\")'   " + disabledConsulta + " " + getDisabledMonth(1, mes, meses) + " /> </div>";
            strResultado += "<div> Feb <input type='text' id='" + sufijo + "inpTxtFeb' class='" + clase + "'  value='" + numberF.format(req.getCantFeb()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtFeb\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtFeb\")'   " + disabledConsulta + " " + getDisabledMonth(2, mes, meses) + " /> </div>";
            strResultado += "<div> Mar <input type='text' id='" + sufijo + "inpTxtMar' class='" + clase + "'  value='" + numberF.format(req.getCantMar()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtMar\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtMar\")'   " + disabledConsulta + " " + getDisabledMonth(3, mes, meses) + " /> </div>";
            strResultado += "<div> Abr <input type='text' id='" + sufijo + "inpTxtAbr' class='" + clase + "'  value='" + numberF.format(req.getCantAbr()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtAbr\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtAbr\")'   " + disabledConsulta + " " + getDisabledMonth(4, mes, meses) + " /> </div>";
            strResultado += "<div> May <input type='text' id='" + sufijo + "inpTxtMay' class='" + clase + "'  value='" + numberF.format(req.getCantMay()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtMay\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtMay\")'   " + disabledConsulta + " " + getDisabledMonth(5, mes, meses) + " /> </div>";
            strResultado += "<div> Jun <input type='text' id='" + sufijo + "inpTxtJun' class='" + clase + "'  value='" + numberF.format(req.getCantJun()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtJun\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtJun\")'   " + disabledConsulta + " " + getDisabledMonth(6, mes, meses) + " /> </div>";
            strResultado += "<div> Jul <input type='text' id='" + sufijo + "inpTxtJul' class='" + clase + "'  value='" + numberF.format(req.getCantJul()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtJul\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtJul\")'   " + disabledConsulta + " " + getDisabledMonth(7, mes, meses) + " /> </div>";
            strResultado += "<div> Ago <input type='text' id='" + sufijo + "inpTxtAgo' class='" + clase + "'  value='" + numberF.format(req.getCantAgo()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtAgo\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtAgo\")'   " + disabledConsulta + " " + getDisabledMonth(8, mes, meses) + " /> </div>";
            strResultado += "<div> Sep <input type='text' id='" + sufijo + "inpTxtSep' class='" + clase + "'  value='" + numberF.format(req.getCantSep()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtSep\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtSep\")'   " + disabledConsulta + " " + getDisabledMonth(9, mes, meses) + " /> </div>";
            strResultado += "<div> Oct <input type='text' id='" + sufijo + "inpTxtOct' class='" + clase + "'  value='" + numberF.format(req.getCantOct()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtOct\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtOct\")'   " + disabledConsulta + " " + getDisabledMonth(10, mes, meses) + " /> </div>";
            strResultado += "<div> Nov <input type='text' id='" + sufijo + "inpTxtNov' class='" + clase + "'  value='" + numberF.format(req.getCantNov()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtNov\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtNov\")'   " + disabledConsulta + " " + getDisabledMonth(11, mes, meses) + " /> </div>";
            strResultado += "<div> Dic <input type='text' id='" + sufijo + "inpTxtDic' class='" + clase + "'  value='" + numberF.format(req.getCantDic()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtDic\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtDic\")'   " + disabledConsulta + " " + getDisabledMonth(12, mes, meses) + " /> </div>";

        }

        strResultado += "</div>";

        return strResultado;
    }

    public static String getCalendarizacionAccionReqEspecial(Requerimiento req, String disabledConsulta, boolean isActual, int mes, boolean enero, int[] meses, int mesConta, boolean enableAll) {

        NumberFormat numberF = NumberFormat.getInstance(Locale.US);
        String strResultado = new String();
        String sufijo = new String();
        String clase = new String();
        if (!isActual) {
            mes = 11;
        }
        if (disabledConsulta.isEmpty()) {
            sufijo = "recal";
            clase = "capt-mes";
        }
        strResultado += "<div id='divMese'>";

        if (enableAll) {

            strResultado += "<div " + getColorRoljo(mesConta, 1) + "> Ene <input type='text' id='" + sufijo + "inpTxtEne' class='" + clase + "'  value='" + numberF.format(req.getCantEne()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtEne\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtEne\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 2) + "> Feb <input type='text' id='" + sufijo + "inpTxtFeb' class='" + clase + "'  value='" + numberF.format(req.getCantFeb()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtFeb\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtFeb\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 3) + "> Mar <input type='text' id='" + sufijo + "inpTxtMar' class='" + clase + "'  value='" + numberF.format(req.getCantMar()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtMar\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtMar\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 4) + "> Abr <input type='text' id='" + sufijo + "inpTxtAbr' class='" + clase + "'  value='" + numberF.format(req.getCantAbr()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtAbr\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtAbr\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 5) + "> May <input type='text' id='" + sufijo + "inpTxtMay' class='" + clase + "'  value='" + numberF.format(req.getCantMay()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtMay\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtMay\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 6) + "> Jun <input type='text' id='" + sufijo + "inpTxtJun' class='" + clase + "'  value='" + numberF.format(req.getCantJun()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtJun\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtJun\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 7) + "> Jul <input type='text' id='" + sufijo + "inpTxtJul' class='" + clase + "'  value='" + numberF.format(req.getCantJul()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtJul\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtJul\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 8) + "> Ago <input type='text' id='" + sufijo + "inpTxtAgo' class='" + clase + "'  value='" + numberF.format(req.getCantAgo()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtAgo\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtAgo\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 9) + "> Sep <input type='text' id='" + sufijo + "inpTxtSep' class='" + clase + "'  value='" + numberF.format(req.getCantSep()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtSep\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtSep\")'   " + disabledConsulta + "  /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 10) + "> Oct <input type='text' id='" + sufijo + "inpTxtOct' class='" + clase + "'  value='" + numberF.format(req.getCantOct()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtOct\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtOct\")'   " + disabledConsulta + " /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 11) + "> Nov <input type='text' id='" + sufijo + "inpTxtNov' class='" + clase + "'  value='" + numberF.format(req.getCantNov()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtNov\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtNov\")'   " + disabledConsulta + " /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 12) + "> Dic <input type='text' id='" + sufijo + "inpTxtDic' class='" + clase + "'  value='" + numberF.format(req.getCantDic()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtDic\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtDic\")'   " + disabledConsulta + " /> </div>";

        } else {

            strResultado += "<div " + getColorRoljo(mesConta, 1) + "> Ene <input type='text' id='" + sufijo + "inpTxtEne' class='" + clase + "'  value='" + numberF.format(req.getCantEne()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtEne\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtEne\")'   " + disabledConsulta + " " + getDisabledMonthConta(1, mes, meses, mesConta) + " /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 2) + "> Feb <input type='text' id='" + sufijo + "inpTxtFeb' class='" + clase + "'  value='" + numberF.format(req.getCantFeb()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtFeb\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtFeb\")'   " + disabledConsulta + " " + getDisabledMonthConta(2, mes, meses, mesConta) + " /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 3) + "> Mar <input type='text' id='" + sufijo + "inpTxtMar' class='" + clase + "'  value='" + numberF.format(req.getCantMar()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtMar\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtMar\")'   " + disabledConsulta + " " + getDisabledMonthConta(3, mes, meses, mesConta) + " /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 4) + "> Abr <input type='text' id='" + sufijo + "inpTxtAbr' class='" + clase + "'  value='" + numberF.format(req.getCantAbr()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtAbr\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtAbr\")'   " + disabledConsulta + " " + getDisabledMonthConta(4, mes, meses, mesConta) + " /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 5) + "> May <input type='text' id='" + sufijo + "inpTxtMay' class='" + clase + "'  value='" + numberF.format(req.getCantMay()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtMay\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtMay\")'   " + disabledConsulta + " " + getDisabledMonthConta(5, mes, meses, mesConta) + " /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 6) + "> Jun <input type='text' id='" + sufijo + "inpTxtJun' class='" + clase + "'  value='" + numberF.format(req.getCantJun()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtJun\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtJun\")'   " + disabledConsulta + " " + getDisabledMonthConta(6, mes, meses, mesConta) + " /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 7) + "> Jul <input type='text' id='" + sufijo + "inpTxtJul' class='" + clase + "'  value='" + numberF.format(req.getCantJul()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtJul\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtJul\")'   " + disabledConsulta + " " + getDisabledMonthConta(7, mes, meses, mesConta) + " /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 8) + "> Ago <input type='text' id='" + sufijo + "inpTxtAgo' class='" + clase + "'  value='" + numberF.format(req.getCantAgo()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtAgo\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtAgo\")'   " + disabledConsulta + " " + getDisabledMonthConta(8, mes, meses, mesConta) + " /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 9) + "> Sep <input type='text' id='" + sufijo + "inpTxtSep' class='" + clase + "'  value='" + numberF.format(req.getCantSep()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtSep\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtSep\")'   " + disabledConsulta + " " + getDisabledMonthConta(9, mes, meses, mesConta) + " /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 10) + "> Oct <input type='text' id='" + sufijo + "inpTxtOct' class='" + clase + "'  value='" + numberF.format(req.getCantOct()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtOct\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtOct\")'   " + disabledConsulta + " " + getDisabledMonthConta(10, mes, meses, mesConta) + " /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 11) + "> Nov <input type='text' id='" + sufijo + "inpTxtNov' class='" + clase + "'  value='" + numberF.format(req.getCantNov()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtNov\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtNov\")'   " + disabledConsulta + " " + getDisabledMonthConta(11, mes, meses, mesConta) + " /> </div>";
            strResultado += "<div " + getColorRoljo(mesConta, 12) + "> Dic <input type='text' id='" + sufijo + "inpTxtDic' class='" + clase + "'  value='" + numberF.format(req.getCantDic()) + "' tabindex='0' maxlength='16' onblur='agregarFormato(\"" + sufijo + "inpTxtDic\");calculaCosto()' onkeyup='validaMascara(\"" + sufijo + "inpTxtDic\")'   " + disabledConsulta + " " + getDisabledMonthConta(12, mes, meses, mesConta) + " /> </div>";

        }

        strResultado += "</div>";

        return strResultado;
    }
    

    public static String getColorRoljo(int mesConta, int mes) {
        if (mesConta == mes) {
            return "style='color : red'";
        } else {
            return "";
        }
    }

    public static Requerimiento getRequerimiento(MovOficiosAccionReq movAccionReq) {
        Requerimiento requerimiento = new Requerimiento();
        requerimiento.setCantEne(movAccionReq.getEne());
        requerimiento.setCantFeb(movAccionReq.getFeb());
        requerimiento.setCantMar(movAccionReq.getMar());
        requerimiento.setCantAbr(movAccionReq.getAbr());
        requerimiento.setCantMay(movAccionReq.getMay());
        requerimiento.setCantJun(movAccionReq.getJun());
        requerimiento.setCantJul(movAccionReq.getJul());
        requerimiento.setCantAgo(movAccionReq.getAgo());
        requerimiento.setCantSep(movAccionReq.getSep());
        requerimiento.setCantOct(movAccionReq.getOct());
        requerimiento.setCantNov(movAccionReq.getNov());
        requerimiento.setCantDic(movAccionReq.getDic());
        requerimiento.setCantidad(movAccionReq.getCantidad());
        requerimiento.setCostoUnitario(movAccionReq.getCostoUnitario());
        requerimiento.setCostoAnual(movAccionReq.getCostoAnual());
        return requerimiento;
    }

    public static String getDisabledMonth(int mes, int numero, int[] meses) {
        if (mes < numero) {
            if (mes == meses[0] || mes == meses[1] || mes == meses[2]) {
                return "";
            } else {
                return "disabled";
            }
        } else {
            return "";
        }
    }

    public static String getDisabledMonthConta(int mes, int numero, int[] meses, int mesConta) {
        if (mesConta > 0) {
            if (mes == mesConta) {
                return "";
            } else {
                return "disabled";
            }
        } else {
            if (mes < numero) {
                if (mes == meses[0] || mes == meses[1] || mes == meses[2]) {
                    return "";
                } else {
                    return "disabled";
                }
            } else {
                return "";
            }
        }

    }

    public static double sumaRequerimiento(Requerimiento requerimiento) {
        double suma = 0.0;
        suma = requerimiento.getCantEne()
                + requerimiento.getCantFeb()
                + requerimiento.getCantMar()
                + requerimiento.getCantAbr()
                + requerimiento.getCantMay()
                + requerimiento.getCantJun()
                + requerimiento.getCantJul()
                + requerimiento.getCantAgo()
                + requerimiento.getCantSep()
                + requerimiento.getCantOct()
                + requerimiento.getCantNov()
                + requerimiento.getCantDic();
        return suma;
    }

    public static double getCostoAnaul(BigDecimal costoU, BigDecimal ene, BigDecimal feb, BigDecimal mar,
            BigDecimal abr, BigDecimal may, BigDecimal jun, BigDecimal jul, BigDecimal ago, BigDecimal sept,
            BigDecimal oct, BigDecimal nov, BigDecimal dic) {
        BigDecimal costoAnual = new BigDecimal(0.0);
        costoAnual = costoAnual.add(costoU.multiply(ene).setScale(2, RoundingMode.HALF_UP));
        costoAnual = costoAnual.add(costoU.multiply(feb).setScale(2, RoundingMode.HALF_UP));
        costoAnual = costoAnual.add(costoU.multiply(mar).setScale(2, RoundingMode.HALF_UP));
        costoAnual = costoAnual.add(costoU.multiply(abr).setScale(2, RoundingMode.HALF_UP));
        costoAnual = costoAnual.add(costoU.multiply(may).setScale(2, RoundingMode.HALF_UP));
        costoAnual = costoAnual.add(costoU.multiply(jun).setScale(2, RoundingMode.HALF_UP));
        costoAnual = costoAnual.add(costoU.multiply(jul).setScale(2, RoundingMode.HALF_UP));
        costoAnual = costoAnual.add(costoU.multiply(ago).setScale(2, RoundingMode.HALF_UP));
        costoAnual = costoAnual.add(costoU.multiply(sept).setScale(2, RoundingMode.HALF_UP));
        costoAnual = costoAnual.add(costoU.multiply(oct).setScale(2, RoundingMode.HALF_UP));
        costoAnual = costoAnual.add(costoU.multiply(nov).setScale(2, RoundingMode.HALF_UP));
        costoAnual = costoAnual.add(costoU.multiply(dic).setScale(2, RoundingMode.HALF_UP));
        return costoAnual.doubleValue();
    }

    public static double getCostoAnual(MovOficiosAccionReq req) {
        BigDecimal total = new BigDecimal(0.0);
        BigDecimal ene = new BigDecimal(req.getEne());
        BigDecimal feb = new BigDecimal(req.getFeb());
        BigDecimal mar = new BigDecimal(req.getMar());
        BigDecimal abr = new BigDecimal(req.getAbr());
        BigDecimal may = new BigDecimal(req.getMay());
        BigDecimal jun = new BigDecimal(req.getJun());
        BigDecimal jul = new BigDecimal(req.getJul());
        BigDecimal ago = new BigDecimal(req.getAgo());
        BigDecimal sep = new BigDecimal(req.getSep());
        BigDecimal oct = new BigDecimal(req.getOct());
        BigDecimal nov = new BigDecimal(req.getNov());
        BigDecimal dic = new BigDecimal(req.getDic());
        BigDecimal costoUnitario = new BigDecimal(req.getCostoUnitario());
        total = total.add(ene);
        total = total.add(feb);
        total = total.add(mar);
        total = total.add(abr);
        total = total.add(may);
        total = total.add(jun);
        total = total.add(jul);
        total = total.add(ago);
        total = total.add(sep);
        total = total.add(oct);
        total = total.add(nov);
        total = total.add(dic);
        double costoAnual = 0.0;
        /*costoAnual += new Double(df.format(req.getEne() * req.getCostoUnitario()));
         costoAnual += new Double(df.format(req.getFeb() * req.getCostoUnitario()));
         costoAnual += new Double(df.format(req.getMar() * req.getCostoUnitario()));
         costoAnual += new Double(df.format(req.getAbr() * req.getCostoUnitario()));
         costoAnual += new Double(df.format(req.getMay() * req.getCostoUnitario()));
         costoAnual += new Double(df.format(req.getJun() * req.getCostoUnitario()));
         costoAnual += new Double(df.format(req.getJul() * req.getCostoUnitario()));
         costoAnual += new Double(df.format(req.getAgo() * req.getCostoUnitario()));
         costoAnual += new Double(df.format(req.getSep() * req.getCostoUnitario()));
         costoAnual += new Double(df.format(req.getOct() * req.getCostoUnitario()));
         costoAnual += new Double(df.format(req.getNov() * req.getCostoUnitario()));
         costoAnual += new Double(df.format(req.getDic() * req.getCostoUnitario()));*/
        total = total.multiply(costoUnitario);
        total = total.setScale(2, RoundingMode.HALF_UP);
        return total.doubleValue();
    }

    public static double getCantidad(MovOficiosAccionReq req) {
        BigDecimal total = new BigDecimal(0.0);
        BigDecimal ene = new BigDecimal(req.getEne());
        BigDecimal feb = new BigDecimal(req.getFeb());
        BigDecimal mar = new BigDecimal(req.getMar());
        BigDecimal abr = new BigDecimal(req.getAbr());
        BigDecimal may = new BigDecimal(req.getMay());
        BigDecimal jun = new BigDecimal(req.getJun());
        BigDecimal jul = new BigDecimal(req.getJul());
        BigDecimal ago = new BigDecimal(req.getAgo());
        BigDecimal sep = new BigDecimal(req.getSep());
        BigDecimal oct = new BigDecimal(req.getOct());
        BigDecimal nov = new BigDecimal(req.getNov());
        BigDecimal dic = new BigDecimal(req.getDic());
        total = total.add(ene);
        total = total.add(feb);
        total = total.add(mar);
        total = total.add(abr);
        total = total.add(may);
        total = total.add(jun);
        total = total.add(jul);
        total = total.add(ago);
        total = total.add(sep);
        total = total.add(oct);
        total = total.add(nov);
        total = total.add(dic);
        return total.doubleValue();

    }

    public static String getStringMesCompleto(int mes) {
        String mesPalabra = new String();
        switch (mes) {
            case 1:
                mesPalabra = "Enero";
                break;
            case 2:
                mesPalabra = "Febrero";
                break;
            case 3:
                mesPalabra = "Marzo";
                break;
            case 4:
                mesPalabra = "Abril";
                break;
            case 5:
                mesPalabra = "Mayo";
                break;
            case 6:
                mesPalabra = "Junio";
                break;
            case 7:
                mesPalabra = "Julio";
                break;
            case 8:
                mesPalabra = "Agosto";
                break;
            case 9:
                mesPalabra = "Septiembre";
                break;
            case 10:
                mesPalabra = "Octubre";
                break;
            case 11:
                mesPalabra = "Noviembre";
                break;
            case 12:
                mesPalabra = "Diciembre";
                break;
            default:
                mesPalabra = "Enero";
                break;
        }
        return mesPalabra;
    }
    
    public static String calculaEstimacion(List<Estimacion> estimacionList, String tipoCalculo){
        NumberFormat numberF = NumberFormat.getInstance(Locale.US);
        BigDecimal cantidad = new BigDecimal(0);
        double valor;
        if(!tipoCalculo.isEmpty()){
            if(tipoCalculo.equals("AC")){
                for(Estimacion estimacion : estimacionList){
                    cantidad = cantidad.add(new BigDecimal(estimacion.getValor()));
                }
            }else if(tipoCalculo.equals("MI")){
                valor = estimacionList.get(0).getValor();
                for(Estimacion estimacion : estimacionList){
                    if(estimacion.getValor() < valor){
                        valor = estimacion.getValor();
                    }
                }
                cantidad = new BigDecimal(valor);
            }else if(tipoCalculo.equals("MA")){                
                valor = estimacionList.get(0).getValor();
                for(Estimacion estimacion : estimacionList){
                    if(estimacion.getValor() > valor){
                        valor = estimacion.getValor();
                    }
                }
                cantidad = new BigDecimal(valor);
            }
        }
        return numberF.format(cantidad.doubleValue());
    }
}
