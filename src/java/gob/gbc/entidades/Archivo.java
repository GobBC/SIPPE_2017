/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.entidades;

import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author joquintero
 */
public final class Archivo {

    private byte[] contenido;
    private long tamanioBytes;
    private String nombreArchivo;
    String contentType;

    public Archivo() {
    }

    public Archivo(FileItem fileItem){
        if(fileItem != null){
            this.contenido = fileItem.get();
            this.contentType = fileItem.getContentType();
            this.nombreArchivo = fileItem.getName();
            this.tamanioBytes = fileItem.getSize();
        }
    }
    
    public Archivo(byte[] contenido, long tamanioBytes, String nombreArchivo, String contentType) {
        this.contenido = contenido;
        this.tamanioBytes = tamanioBytes;
        this.nombreArchivo = nombreArchivo;
        this.contentType = contentType;
    }
    
    

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    public long getTamanioBytes() {
        return tamanioBytes;
    }

    public void setTamanioBytes(long tamanioBytes) {
        this.tamanioBytes = tamanioBytes;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
