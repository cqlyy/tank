package cn.cq.entity;

import cn.cq.enums.Dir;
import cn.cq.enums.Group;
import cn.cq.frame.TankFrame;
import cn.cq.strategy.FireStrategy;
import cn.cq.strategy.impl.DefaultFireStrategy;
import cn.cq.strategy.impl.FourDirFireStrategy;
import cn.cq.util.PropertyMgr;
import cn.cq.util.ResourceMgr;

import java.awt.*;
import java.util.Random;

public class Tank {
    public int x = 200, y = 200;
    public static final int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static final int HEIGHT = ResourceMgr.goodTankU.getHeight();
    private static final int SPEED = PropertyMgr.getInt("tankSpeed");
    private Random random = new Random();
    public Dir dir = Dir.DOWN;
    private boolean moving = true;
    private boolean living = true;
    public Group group = Group.BAD;

    public TankFrame tankFrame;
    public Rectangle rect = new Rectangle();
    FireStrategy fs = new DefaultFireStrategy();

    public Tank(int x, int y, Dir dir, TankFrame tankFrame, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
        this.group = group;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = this.WIDTH;
        rect.height = this.HEIGHT;

        if (this.group == Group.GOOD) {
            try {
                fs = (FireStrategy) Class.forName(PropertyMgr.getString("goodFS")).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                fs = (FireStrategy) Class.forName(PropertyMgr.getString("badFS")).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void paint(Graphics g) {
        if (!living) {
            tankFrame.tanks.remove(this);
        }

        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
                break;
        }

        move();
    }

    private void move() {
        if (!moving) return;
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
        }

        if (Group.BAD == this.group && random.nextInt(100) > 95) {
            this.fire();
        }
        if (Group.BAD == this.group && random.nextInt(100) > 95) {
            randomDir();
        }
        boundsCheck();

        rect.x = this.x;
        rect.y = this.y;
    }

    private void boundsCheck() {
        if (this.x < 2) x = 2;
        if (this.y < 28) y = 28;
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 2) x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2;
    }

    public void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void fire() {
        fs.fire(this);
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

    public void die() {
        this.living = false;
    }
}
