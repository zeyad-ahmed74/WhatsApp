package com.example.whatsapp.data.model;

import java.io.Serializable;
import java.net.URL;

public class UserModel implements Serializable {
  private String key;
   private String img_URL;
   private String name;

    public UserModel() {
    }

    public UserModel(String key,String img_URL, String name) {
        this.key=key;
        this.img_URL = img_URL;
        this.name = name;
    }


    public String getKey() {
        return key;
    }

    public String getImg() {
        return img_URL;
    }

    public void setImg(String img_URL) {
        this.img_URL = img_URL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
