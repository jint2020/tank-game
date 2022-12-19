package com.jt.tank;

import java.util.Vector;

public class HeroTank extends Tank{

    Bullet b = null;

    Vector<Bullet> bullets = new Vector<>();

    public HeroTank(int x, int y, int speed, int direction, boolean isAlive) {
        super(x, y, speed, direction, isAlive);
    }

    public void shot(){
        //创建 Shot 对象, 根据当前Hero对象的位置和方向来创建Shot
        switch (getDirection()) {//得到Hero对象方向
            //向前 0 ，向后 1 ，向右 2 ，向左 3
            case 0:
                b = new Bullet(getX()+18,getY(),2,0);
                break;
            case 1:
                b = new Bullet(getX() + 18, getY() + 60, 2,1);
                break;
            case 2:
                b = new Bullet(getX() + 60, getY() + 18, 2,2);
                break;
            case 3:
                b = new Bullet(getX(), getY() + 18, 2,3);
                break;
        }
        //把新创建的bullet放入到bullets
        if (bullets.size() < 5) {
            bullets.add(b);
        }
        //启动Bullet线程
        new Thread(b).start();
    }

}
