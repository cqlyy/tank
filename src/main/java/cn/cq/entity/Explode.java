package cn.cq.entity;

import cn.cq.enums.Dir;
import cn.cq.enums.Group;
import cn.cq.frame.Audio;
import cn.cq.frame.TankFrame;
import cn.cq.util.ResourceMgr;

import java.awt.*;

public class Explode {
    public static final int WIDTH = ResourceMgr.bulletD.getWidth();
    public static final int HEIGHT = ResourceMgr.bulletD.getHeight();
    private int x,y;

    private TankFrame tankFrame;
    private int step = 0;

    public Explode(int x, int y,  TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
        new Audio("audio/explode.wav").start();
    }


    public void paint(Graphics g){
      g.drawImage(ResourceMgr.explodes[step++],x,y,null);
      if (step>=ResourceMgr.explodes.length){
          tankFrame.explodes.remove(this);
      }
    }
}
