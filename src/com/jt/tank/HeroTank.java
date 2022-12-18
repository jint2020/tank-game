package com.jt.tank;

public class HeroTank extends Tank{

    Bullet b = null;


    public HeroTank(int x, int y, int speed, int direction) {
        super(x, y, speed, direction);
    }

    public void shot(){
        b = new Bullet(getX(), getY(),2,getDirection(),0);
        new Thread(b).start();
    }

}
