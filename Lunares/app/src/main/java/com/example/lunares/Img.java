package com.example.lunares;

import android.graphics.Bitmap;

public class Img {
    private String id;
    private String password;
    private Bitmap bitmap;

    public Img( String id,String password){
        this.setId(id);
        this.setPassword(password);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
