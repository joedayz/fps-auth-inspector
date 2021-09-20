package com.fps.inspector.dto;

public class BaseResponse<T> {

    private Integer codigo;
    private String estado;
    private String mensaje;
    private T response;

    public BaseResponse(Integer codigo, String estado, String mensaje, T response) {
        super();
        this.codigo = codigo;
        this.estado = estado;
        this.mensaje = mensaje;
        this.response = response;
    }

    public BaseResponse(Integer codigo, String estado, String mensaje) {
        super();
        this.codigo = codigo;
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public BaseResponse(String estado, String mensaje, T response) {
        super();
        this.estado = estado;
        this.mensaje = mensaje;
        this.response = response;
    }

    public BaseResponse(String estado, String mensaje) {
        super();
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public BaseResponse(String mensaje) {
        super();
        this.mensaje = mensaje;
    }

    public BaseResponse() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
}
