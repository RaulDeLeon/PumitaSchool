package com.icloud.ganlensystems.pumitaschool;

public class User {

    private String nombre;
    private String carrera;
    private double edad;
    private String genero;


    public User(){}
    public User(String n, String c, double w, String e){
        this.nombre=n;
        this.carrera=c;
        this.edad=w;
        this.genero=e;

    }
    public String getName() {
        return nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public double getEdad() {
        return edad;
    }

    public String getGenero() {
        return genero;
    }



    @Override
    public String toString() {
        return nombre +" "+ carrera +" " +edad+" " +genero;
    }
}
