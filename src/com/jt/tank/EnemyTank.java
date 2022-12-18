package com.jt.tank;

import java.util.Vector;

public class EnemyTank extends Tank{

    private boolean isAlive = true;


    Vector<Bullet> bullets = new Vector<>();

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public EnemyTank(int x, int y, int speed, int direction) {
        super(x, y, speed, direction);
    }

    public void shot(){
    }


}
