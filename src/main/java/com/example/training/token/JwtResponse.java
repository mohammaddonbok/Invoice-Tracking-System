package com.example.training.token;

public class JwtResponse {
    private String token;

    public JwtResponse(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }
    public void setToken(){
        this.token = token;
    }
}
