package com.icloud.ganlensystems.pumitaschool;

/**
 * Created by Raul on 29/12/17.
 */

public class Registro_Alumno {
    private String uid, Nombre_Completo, Carrera, Facultad, Edad, Genero;

    public Registro_Alumno() {

    }

    public Registro_Alumno(String uid, String nombre_Completo, String carrera, String facultad, String edad, String genero) {

        this.uid = uid;
        this.Nombre_Completo = nombre_Completo;
        this.Carrera = carrera;
        this.Facultad = facultad;
        this.Edad = edad;
        this.Genero = genero;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre_Completo() {
        return Nombre_Completo;
    }

    public void setNombre_Completo(String nombre_Completo) {
        Nombre_Completo = nombre_Completo;
    }

    public String getCarrera() {
        return Carrera;
    }

    public void setCarrera(String carrera) {
        Carrera = carrera;
    }

    public String getFacultad() {
        return Facultad;
    }

    public void setFacultad(String facultad) {
        Facultad = facultad;
    }

    public String getEdad() {
        return Edad;
    }

    public void setEdad(String edad) {
        Edad = edad;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }
}

