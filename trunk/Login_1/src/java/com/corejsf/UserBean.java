package com.corejsf;

import java.io.Serializable;
import javax.inject.Named; 
   // lub import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped; 
   // lub import javax.faces.bean.SessionScoped;

@Named("user") // lub @ManagedBean(name="user")
@SessionScoped
public class UserBean implements Serializable {
   private String name;
   private String password;

   public String getName() { return name; }   
   public void setName(String newValue) { name = newValue; }

   public String getPassword() { return password; }
   public void setPassword(String newValue) { password = newValue; }   
}
