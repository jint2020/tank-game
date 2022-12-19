package com.jt.tank;


import javax.swing.*;

public class Windows extends JFrame {

    //定义一个GamePanel
    GamePanel gp = null;
    public static void main(String[] args) {
        new Windows();
    }

    public Windows() {
        gp = new GamePanel();
        new Thread(gp).start();
        this.add(gp);
        this.addKeyListener(gp);
        this.setSize(1000,775);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
