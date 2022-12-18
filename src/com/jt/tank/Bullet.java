package com.jt.tank;

public class Bullet implements Runnable{

    private int x;//
    private int y;//
    private int speed;
    private int direction;
    private boolean isAlive = true;

    public Bullet(int x, int y, int speed, int direction) {
        this.x = x;
        this.y = y;
        this.speed = speed;
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


    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public void run() {
        while (true){
            //判断方向
            switch (direction) {
                //向前
                case 0:
                    y -= speed;
                    break;
                //向后
                case 1:
                    y += speed;
                    break;
                //向右
                case 2:
                    x += speed;
                    break;
                //向左
                case 3:
                    x -= speed;
                    break;
            }
            //System.out.println("子弹坐标 "+getX()+" 子弹坐标 "+getY());
            if (!(x>=0 && x<=1000 && y>=0 && y<=750 && isAlive)) {
                isAlive = false;
                break;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}
