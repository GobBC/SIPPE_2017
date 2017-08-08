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
public class Articulo {

    private String articuloId;
    private String articulo;
    private int partida;
    private double costo;
    private int gpogto;
    private int subgpo;
    private String sistema;

    public String getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(String articuloId) {
        this.articuloId = articuloId;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public int getPartida() {
        return partida;
    }

    public void setPartida(int partida) {
        this.partida = partida;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getGpogto() {
        return gpogto;
    }

    public void setGpogto(int gpogto) {
        this.gpogto = gpogto;
    }

    public int getSubgpo() {
        return subgpo;
    }

    public void setSubgpo(int subgpo) {
        this.subgpo = subgpo;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }
}
