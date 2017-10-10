package com.ablethon.woongsang.gestcapturex;

class Vertex {
    private float x;
    private float y;



    private float radian;
    private float length;
    private float section;


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

    public float getRadian() {
        return radian;
    }

    public void setRadian(float radian) {
        this.radian = radian;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getSection() {
        return section;
    }

    public void setSection(float section) {
        this.section = section;
    }

    public void addLength(float length) {
        this.length += length;
    }
}
