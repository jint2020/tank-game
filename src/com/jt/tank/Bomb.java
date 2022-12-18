package com.jt.tank;

public class Bomb {
    int x,y;
    int life = 9;
    boolean isAlive;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void lifeDown(){
        if (life > 0) {
            life--;
        }else {
            isAlive = false;
        }
    }

    public int getLife() {
        return life;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
