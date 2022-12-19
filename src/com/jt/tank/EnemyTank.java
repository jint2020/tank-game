package com.jt.tank;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{

    Bullet b = null;

    Vector<Bullet> bullets = new Vector<>();

    public EnemyTank(int x, int y, int speed, int direction, boolean isAlive) {
        super(x, y, speed, direction, isAlive);
    }

    public void shot(){

        //创建 Shot 对象, 根据当前Hero对象的位置和方向来创建Shot
        switch (getDirection()) {//得到方向
            //向前 0 ，向后 1 ，向右 2 ，向左 3
            case 0:
                b = new Bullet(getX()+18,getY(),3,0);
                break;
            case 1:
                b = new Bullet(getX() + 18, getY() + 60, 3,1);
                break;
            case 2:
                b = new Bullet(getX() + 60, getY() + 18, 3,2);
                break;
            case 3:
                b = new Bullet(getX(), getY() + 18, 3,3);
                break;
        }
        //把新创建的bullet放入到bullets
        if (bullets.size() < 100) {
            bullets.add(b);
        }
        //启动Bullet线程
        new Thread(b).start();
    }

    public void moveDelay(){
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
    @Override
    public void run() {
        while (isAlive()){
            switch (getDirection()) {//得到方向
                //向前 0 ，向后 1 ，向右 2 ，向左 3
                case 0:
                    for (int i = 0; i < 30; i++) {
                        moveUp();
                        moveDelay();
                    }
                    break;
                case 1:
                    for (int i = 0; i < 30; i++) {
                        moveDown();
                        moveDelay();
                    }
                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        moveRight();
                        moveDelay();
                    }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        moveLeft();
                        moveDelay();
                    }
                    break;
            }
            moveDelay();
            setDirection((int)(Math.random()*4));
            shot();
        }
    }
}
