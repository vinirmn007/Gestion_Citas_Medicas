package com.gestionCitas.models;

public class SignosVitales {
    private Integer id;
    private Float altura;
    private Float peso;
    private Float temperatura;
    private Float presionSistolica;
    private Float presionDiastolica;
    private CitaMedica citaMedica;

    public SignosVitales() {
        this.id = 0;
        this.altura = 0.0F;
        this.peso = 0.0F;
        this.temperatura = 0.0F;
        this.presionSistolica = 0.0F;
        this.presionDiastolica = 0.0F;
        this.citaMedica = null;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getAltura() {
        return this.altura;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

    public Float getPeso() {
        return this.peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getTemperatura() {
        return this.temperatura;
    }

    public void setTemperatura(Float temperatura) {
        this.temperatura = temperatura;
    }

    public Float getPresionSistolica() {
        return this.presionSistolica;
    }

    public void setPresionSistolica(Float presionSistolica) {
        this.presionSistolica = presionSistolica;
    }

    public Float getPresionDiastolica() {
        return this.presionDiastolica;
    }

    public void setPresionDiastolica(Float presionDiastolica) {
        this.presionDiastolica = presionDiastolica;
    }

    public CitaMedica getCitaMedica() {
        return this.citaMedica;
    }

    public void setCitaMedica(CitaMedica citaMedica) {
        this.citaMedica = citaMedica;
    }
}
