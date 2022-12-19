package com.jt.tank;

import java.util.Vector;

/**
 * 坦克父类
 */
public class Tank {
    private int x;//坦克的横坐标
    private int y;//坦克的纵坐标

    private int speed = 1;
    private int direction;
    private boolean isAlive = true;

    public Tank(int x, int y, int speed, int direction, boolean isAlive) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        this.isAlive = isAlive;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void moveUp(){
        //设置上方边缘数值控制,并向上移动
        if (y > 0) {
            y -= speed;
        }

    }
    public void moveDown(){
        //设置下方边缘数值控制,并向下移动
        if (y+60 < 750) {
            y+=speed;
        }
    }
    public void moveLeft(){
        //设置左方边缘数值控制,并向左移动
        if (x >= 0) {
            x-=speed;
        }
    }
    public void moveRight(){
        //设置右方边缘数值控制,并向右移动
        if (x+60 < 1000) {
            x+=speed;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void shot(){
    }
}
