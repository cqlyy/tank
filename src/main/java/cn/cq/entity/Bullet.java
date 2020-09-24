package cn.cq.entity;

import cn.cq.enums.Dir;
import cn.cq.enums.Group;
import cn.cq.frame.TankFrame;
import cn.cq.util.PropertyMgr;
import cn.cq.util.ResourceMgr;

import java.awt.*;

public class Bullet {
    private static final int SPEED = PropertyMgr.getInt("bulletSpeed");
    public static final int WIDTH = ResourceMgr.bulletD.getWidth();
    public static final int HEIGHT = ResourceMgr.bulletD.getHeight();
    private int x,y;
    public Dir dir;

    private boolean living = true;
    private TankFrame tankFrame;
    public Group group = Group.BAD;

    Rectangle rect = new Rectangle();

    public Bullet(int x, int y, Dir dir, TankFrame tankFrame,Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
        this.group = group;
        rect.x = this.x;
        rect.y = this.y;
        rect.width = this.WIDTH;
        rect.height = this.HEIGHT;

        tankFrame.bullets.add(this);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void paint(Graphics g){
        if (!living){
            tankFrame.bullets.remove(this);
        }

        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bulletL,x,y,null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU,x,y,null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR,x,y,null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD,x,y,null);
                break;
        }


        move();
    }

    private void move() {
        switch (dir){
            case LEFT:
                x-=SPEED;
                break;
            case UP:
                y-=SPEED;
                break;
            case RIGHT:
                x+=SPEED;
                break;
            case DOWN:
                y+=SPEED;
                break;
        }
        rect.x = this.x;
        rect.y = this.y;

        if (x<0 || y<0 || x> TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT)
            living = false;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void collideWith(Tank tank) {
        if (this.group == tank.getGroup()) return;

        if (rect.intersects(tank.rect)){
            tank.die();
            this.die();

            int ex = tank.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
            int ey = tank.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
            tankFrame.explodes.add(new Explode(ex,ey,tankFrame));
        }
    }

    private void die() {
        living = false;
    }
}
