package com.jt.tank;

public class Bomb {
    private int x,y;
    private int life = 9;
    private boolean isAlive;

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
}
