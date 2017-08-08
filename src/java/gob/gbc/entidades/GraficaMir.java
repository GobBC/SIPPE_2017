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
public class GraficaMir {
    private int cancelada;
    private int borrador;
    private int enviada;
    private int rechazada;
    private int validada;
    private int enviadaPosterior;
    private int rechazadaPosterior;
    private int validadaPosterior;
    private int totalInicial;
    private int totalPosterior;
    
    public int getCancelada() {
        return cancelada;
    }

    public void setCancelada(int cancelada) {
        this.cancelada = cancelada;
    }

    public int getBorrador() {
        return borrador;
    }

    public void setBorrador(int borrador) {
        this.borrador = borrador;
    }

    public int getEnviada() {
        return enviada;
    }

    public void setEnviada(int enviada) {
        this.enviada = enviada;
    }

    public int getRechazada() {
        return rechazada;
    }

    public void setRechazada(int rechazada) {
        this.rechazada = rechazada;
    }

    public int getValidada() {
        return validada;
    }

    public void setValidada(int validada) {
        this.validada = validada;
    }

    public int getEnviadaPosterior() {
        return enviadaPosterior;
    }

    public void setEnviadaPosterior(int enviadaPosterior) {
        this.enviadaPosterior = enviadaPosterior;
    }

    public int getRechazadaPosterior() {
        return rechazadaPosterior;
    }

    public void setRechazadaPosterior(int rechazadaPosterior) {
        this.rechazadaPosterior = rechazadaPosterior;
    }

    public int getValidadaPosterior() {
        return validadaPosterior;
    }

    public void setValidadaPosterior(int validadaPosterior) {
        this.validadaPosterior = validadaPosterior;
    }

    public int getTotalInicial() {
        setTotalInicial(cancelada + borrador + enviada + rechazada + validada);
        return totalInicial;
    }

    private void setTotalInicial(int totalInicial) {
        this.totalInicial = totalInicial;
    }

    public int getTotalPosterior() {
        setTotalPosterior(enviadaPosterior + rechazadaPosterior + validadaPosterior);
        return totalPosterior;
    }

    private void setTotalPosterior(int totalPosterior) {
        this.totalPosterior = totalPosterior;
    }
    
    public double getPorcentaje(int totalEstatus, int etapa){
        int total = etapa  == 1 ? getTotalInicial() : getTotalPosterior();
        return total > 0 ? (double)(totalEstatus * 100) / (etapa  == 1 ? getTotalInicial() : getTotalPosterior()) : 0;
    }

}
