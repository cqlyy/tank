package cn.cq.strategy.impl;

import cn.cq.entity.Bullet;
import cn.cq.entity.Tank;
import cn.cq.enums.Dir;
import cn.cq.enums.Group;
import cn.cq.frame.Audio;
import cn.cq.strategy.FireStrategy;

public class FourDirFireStrategy implements FireStrategy {
    @Override
    public void fire(Tank t) {
        int bx = t.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int by = t.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        Dir[] dirs = Dir.values();
        for (Dir dir : dirs) {
            new Bullet(bx, by, dir, t.tankFrame, t.group);
        }

        if (t.group == Group.GOOD) {
            new Audio("audio/tank_fire.wav").start();
        }

    }
}
