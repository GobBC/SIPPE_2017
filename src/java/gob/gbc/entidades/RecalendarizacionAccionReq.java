/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.entidades;

/**
 *
 * @author ugarcia
 */
public class RecalendarizacionAccionReq {
    private int identificador;
    private MovOficiosAccionReq movAccionReq;
    
    public RecalendarizacionAccionReq(){
        movAccionReq = new MovOficiosAccionReq();
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public MovOficiosAccionReq getMovAccionReq() {
        return movAccionReq;
    }

    public void setMovAccionReq(MovOficiosAccionReq movAccionReq) {
        this.movAccionReq = movAccionReq;
    }
    
    
}
