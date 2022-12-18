package com.jt.tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class GamePanel extends JPanel implements KeyListener,Runnable {
    HeroTank hTank = null;
    Vector<EnemyTank> enemyTanks = new Vector<>(); //敌方坦克Vector数组
    int numOfEnemyTanks = 5; //5辆敌方坦克

    Vector<Bomb> bombs = new Vector<>();
    int numOfBombs = 5;

    Image imageOne;
    Image imageTwo;
    Image imageThree;


    public GamePanel() {
        //一部我方坦克，绘制出生位置
        hTank = new HeroTank(460, 640,8,0);
        //敌方坦克，用Vector作为容器
        for(int i = 0; i<numOfEnemyTanks;i++){
            EnemyTank et = new EnemyTank(150*(i+1),0,6,1);
            //每颗子弹的位置
            Bullet bt = new Bullet(et.getX()+20,et.getY()+60,2,et.getDirection(),1);
            //把5颗子弹添加到EnemyTank类里面的bullets数组里
            et.bullets.add(bt);
            //启动子弹线程
            new Thread(bt).start();
            enemyTanks.add(et);
        }
        //初始化图片对象
        imageOne = Toolkit.getDefaultToolkit().getImage("/bomb_1.gif");
        imageTwo = Toolkit.getDefaultToolkit().getImage("/bomb_2.gif");
        imageThree = Toolkit.getDefaultToolkit().getImage("/bomb_3.gif");
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形 - 作为游戏背景
        //绘制我方坦克
        drawTank(hTank.getX(), hTank.getY(), g, hTank.getDirection(), 0);
        //绘制子弹
        if (hTank.b != null && hTank.b.isAlive() == true) {//判断子弹线程的有效性
            //绘制坦克
            drawBullet(hTank.b.getX(),hTank.b.getY(),g,hTank.b.getDirection(),hTank.b.getBulletType());
        }
        //绘制敌方坦克和子弹
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isAlive()) {
                drawTank(enemyTank.getX(),enemyTank.getY(),g,enemyTank.getDirection(),1);
            }else {
                enemyTanks.remove(enemyTank);
            }

            //每一辆敌方坦克有一个保存5枚子弹的数组
            for (int j = 0; j < enemyTank.bullets.size(); j++) {//遍历数组取出子弹
                Bullet bullet = enemyTank.bullets.get(j);
                if (bullet.isAlive()) { //判断子弹线程有效性
                    //绘制子弹
                    drawBullet(bullet.getX(),bullet.getY(),g,enemyTank.getDirection(),1);
                }else { //如果子弹线程失效，从数组移除该子弹
                    enemyTank.bullets.remove(bullet);
                }
            }
        }
    }

    /**
     *
     * @param x          坦克的左上角x坐标  ->由坦克初始坐标计算炮筒位置 ->子弹从炮筒位置射出
     * @param y          坦克的左上角y坐标
     * @param g          画笔
     * @param direction  坦克的初始方向->从方向确定子弹飞行方向
     * @param tankType   坦克的类型->由坦克类型区分子弹类型
     */
    public void drawBullet(int x, int y, Graphics g, int direction, int tankType){
        switch (tankType) {
            case 0: //我方坦克
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.orange);
                break;
        }
        switch (direction) {
            //向前
            case 0:
                g.fillOval(x + 17, y,5,10);
                break;
            //向后
            case 1:
                g.fillOval(x + 17, y+55,5,10);
                break;
            //向右
            case 2:
                g.fillOval(x + 60, y+17,10,5);
                break;
            //向左
            case 3:
                g.fillOval(x, y+17,10,5);
                break;
        }
    }

    /**
     * @param bullet
     * @param enemyTank0
     *
     * 判断子弹集中敌方坦克
     */
    public static void hitCheck(Bullet bullet, EnemyTank enemyTank){
        switch (enemyTank.getDirection()){
            //判断
            case 0:
            case 1:
                if (bullet.getX() > enemyTank.getX() && bullet.getX() < enemyTank.getX()+40 &&
                        bullet.getY() > enemyTank.getY() && bullet.getY() < enemyTank.getY()+60) {
                    bullet.setAlive(false);
                    enemyTank.setAlive(false);
                }
                break;
            case 2:
            case 3:
                if (bullet.getX() > enemyTank.getX()
                        && bullet.getX() < enemyTank.getX()+60
                        && bullet.getY() > enemyTank.getY()
                        && bullet.getY() < enemyTank.getY()+40) {
                    bullet.setAlive(false);
                    enemyTank.setAlive(false);
                }
                break;
        }
    }

    /**
     * @param x         坦克的左上角x坐标
     * @param y         坦克的左上角y坐标
     * @param g         画笔
     * @param direction 坦克的初始方向
     * @param tankType  坦克的类型
     */
    public void drawTank(int x, int y, Graphics g, int direction, int tankType) {
        //根据坦克的类型设置颜色
        switch (tankType) {
            case 0: //我方坦克
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.orange);
                break;
        }
        //判断方向
        switch (direction) {
            //向前
            case 0:
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y);
                break;
            //向后
            case 1:
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y+60);
                break;
            //向右
            case 2:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y+30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x + 60, y+20);
                break;
            //向左
            case 3:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y+30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x, y+20);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        //向前 0 ，向后 1 ，向右 2 ，向左 3
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            hTank.setDirection(1);
            hTank.moveDown();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            hTank.setDirection(0);
            hTank.moveUp();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            hTank.setDirection(2);
            hTank.moveRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            hTank.setDirection(3);
            hTank.moveLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_J) {
            //b = new Bullet(hTank.getX(), hTank.getY(),2,hTank.getDirection(),0);
            //b.start();
            hTank.shot();
        }
        if (e.getKeyCode() == KeyEvent.VK_HOME) {
            hTank.setDirection(0);
            hTank.setX(460);
            hTank.setY(640);
        }
        this.repaint();
        System.out.println("我方坦克坐标: " + hTank.getX() + " " + hTank.getY());
    }
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            if (hTank.b != null && hTank.b.isAlive()) {
                for (EnemyTank et:enemyTanks) {
                    hitCheck(hTank.b,et);
                }
            }
            this.repaint();
        }
    }
}