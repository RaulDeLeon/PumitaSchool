package com.icloud.ganlensystems.pumitaschool;

/**
 * Created by Raul on 13/12/17.
 */

public class ImageUpload {
    public String nombre;
    public String url;

    public String getName() { return nombre;
    }

    public String getUrl() {
        return url;
    }

    public ImageUpload(String nombre, String url) {
        this.nombre = nombre;
        this.url = url;
    }

    public ImageUpload(){}


}