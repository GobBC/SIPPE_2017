/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.util;

/**
 *
 * @author ugarcia
 */
public class Resultado {
    private String mensaje;
    private boolean resultado;

    public Resultado(String mensaje, boolean resultado) {
        this.mensaje = mensaje;
        this.resultado = resultado;
    }

    public Resultado() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isResultado() {
        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }
    
    
}
