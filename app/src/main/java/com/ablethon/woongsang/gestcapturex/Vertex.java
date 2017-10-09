package com.ablethon.woongsang.gestcapturex;

class Vertex {
    private float x;
    private float y;
    //private float radian;

    public Vertex(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }
}
