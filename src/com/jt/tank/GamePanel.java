package com.jt.tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class GamePanel extends JPanel implements KeyListener,Runnable {
    HeroTank hTank = null;
    Vector<EnemyTank> enemyTanks = new Vector<>(); //敌方坦克Vector数组
    int numOfEnemyTanks = 5; //3辆敌方坦克
    Vector<Bomb> bombs = new Vector<>();
    //int numOfBombs = 5;

    private final Image imageOne = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
    private final Image imageTwo = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
    private final Image imageThree = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));


    public GamePanel() {
        //一部我方坦克，绘制出生位置
        hTank = new HeroTank(500, 100,10,0,true);
        //敌方坦克，用Vector作为容器
        for(int i = 0; i<numOfEnemyTanks;i++){
            EnemyTank et = new EnemyTank(150*(i+1),0,2,1,true);
            Thread thread = new Thread(et);
            thread.start();
            //每颗子弹的位置
            //Bullet bt = new Bullet(et.getX()+20,et.getY()+60,2,et.getDirection(),1);
            //Bullet bt = new Bullet(et.getX(),et.getY()+60,2,et.getDirection());
            //把5颗子弹添加到EnemyTank类里面的bullets数组里
            //et.bullets.add(bt);
            //启动子弹线程
            //new Thread(bt).start();
            enemyTanks.add(et);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形 - 作为游戏背景
        //绘制我方坦克
        if (hTank.isAlive()) {
            drawTank(hTank.getX(), hTank.getY(), g, hTank.getDirection(), 0);
            //绘制可以连续发射的子弹
            for (int i = 0; i < hTank.bullets.size(); i++) {//遍历数组取出子弹
                Bullet b = hTank.bullets.get(i);
                if (b != null && b.isAlive()) { //判断子弹线程有效性
                    ////绘制地方坦克的子弹'
                    g.setColor(Color.cyan);
                    g.draw3DRect(b.getX(),b.getY(),4,4,false);
                }else { //如果子弹线程失效，从数组移除该子弹
                    hTank.bullets.remove(b);
                }
            }
        }

        for (int i = 0; i < bombs.size(); i++) {
            Bomb b = bombs.get(i);
            if (b.getLife() > 6) {
                System.out.println("image一爆了");
                g.drawImage(imageOne,b.getX(),b.getY(),60,60,this);
                System.out.println("x轴坐标为"+b.getX()+" y轴坐标为"+b.getY());
            } else if (b.getLife() > 3) {
                System.out.println("image二爆了");
                g.drawImage(imageTwo,b.getX(),b.getY(),60,60,this);
                System.out.println("x轴坐标为"+b.getX()+" y轴坐标为"+b.getY());

            } else {
                System.out.println("image三爆了");
                g.drawImage(imageThree,b.getX(),b.getY(),60,60,this);
                System.out.println("x轴坐标为"+b.getX()+" y轴坐标为"+b.getY());
            }
            b.lifeDown();
            if (b.getLife() == 0) {
                bombs.remove(b);
            }
        }
        //绘制敌方坦克和子弹
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);

            if (enemyTank.isAlive()) {
                drawTank(enemyTank.getX(),enemyTank.getY(),g,enemyTank.getDirection(),1);
                g.setColor(Color.cyan);
                //每一辆敌方坦克有一个保存5枚子弹的数组
                for (int j = 0; j < enemyTank.bullets.size(); j++) {//遍历数组取出子弹
                    Bullet bullet = enemyTank.bullets.get(j);
                    if (bullet.isAlive() ) { //判断子弹线程有效性
                        ////绘制地方坦克的子弹
                        g.setColor(Color.orange);
                        g.draw3DRect(bullet.getX(),bullet.getY(),4,4,false);
                    }else { //如果子弹线程失效，从数组移除该子弹
                        enemyTank.bullets.remove(bullet);
                    }
                }
            }else {
                enemyTanks.remove(enemyTank);
            }
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
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y);//画出炮筒
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
            //检测子弹线程已经销毁后，再发射第二颗子弹
            //if (hTank.b == null || hTank.b.isAlive()) {
            //hTank.shot();
            //}
            //使用定义好的方法shot()方法，连续按键，连续发射子弹，延迟为50毫秒
            hTank.shot();
            delay(100);
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

    /**
     * @param bullet
     * @param tank
     * 判断子弹击中坦克的情况
     */
    public void hitCheck(Bullet bullet, Tank tank){
        switch (tank.getDirection()){
            //判断
            //向前 0 ，向后 1 ，向右 2 ，向左 3
            case 0:
            case 1:
                if (bullet.getX() > tank.getX()
                        && bullet.getX() < tank.getX()+40
                        && bullet.getY() > tank.getY()
                        && bullet.getY() < tank.getY()+60) {
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                    bullet.setAlive(false);
                    tank.setAlive(false);
                    //enemyTanks.remove(enemyTank);
                }
                break;
            case 2:
            case 3:
                if (bullet.getX() > tank.getX()
                        && bullet.getX() < tank.getX()+60
                        && bullet.getY() > tank.getY()
                        && bullet.getY() < tank.getY()+40) {
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                    bullet.setAlive(false);
                    tank.setAlive(false);
                }
                break;
        }
    }
    public void hitCheckEnemyTank(){
        for (int i = 0; i < hTank.bullets.size(); i++) {
            Bullet b  = hTank.bullets.get(i);
            for (int j = 0; j < enemyTanks.size(); j++) {
                EnemyTank et = enemyTanks.get(j);
                if (b != null && b.isAlive()) {
                    hitCheck(b,et);
                }else {
                    hTank.bullets.remove(b);
                }
            }
        }
    }
    public void hitCheckHeroTank(){
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.bullets.size(); j++) {//遍历数组取出子弹
                Bullet bullet = enemyTank.bullets.get(j);
                if (bullet.isAlive()) { //判断子弹线程有效性
                    hitCheck(bullet,hTank);
                }else { //如果子弹线程失效，从数组移除该子弹
                    enemyTank.bullets.remove(bullet);
                }
            }
        }
    }

    public void delay(int millis){
        try {
            Thread.sleep(millis);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while (true){
            delay(100);
            hitCheckEnemyTank();
            hitCheckHeroTank();
            this.repaint();
        }
    }
}