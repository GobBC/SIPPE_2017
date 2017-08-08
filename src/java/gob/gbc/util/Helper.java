/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.util;

/**
 *
 * @author azatarain
 */
public class Helper {

    public static String ObtieneColorEstatus(int estatus) {
        String color = "";
        if(estatus != -1){
        EnumEstatusMIR est = EnumEstatusMIR.values()[estatus];
        switch (est) {
            case BORRADOR:
                color = "#c1c1c1";
                break;
            case ENVIADA:
                color = "#3c9dff";
                break;
            case RECHAZADA:
                color = "#ffa851";
                break;
            case VALIDADA:
                color = "#72dc72";
                break;
            case CANCELADA:
                color = "#ff5353";
                break;
            case ENVIADA_POS:
                color = "#3c9dff";
                break;
            case RECHAZADA_POS:
                color = "#ffa851";
                break;
            case VALIDADA_POS:
                color = "#72dc72";
                break;
        }
        }
        return color;
    }

    public static String ObtieneDescrEstatus(int estatus) {
        if(estatus != -1){
        EnumEstatusMIR est = EnumEstatusMIR.values()[estatus];
        return est.toString().replace("_POS", "");
        }
        return "";
    }
}
