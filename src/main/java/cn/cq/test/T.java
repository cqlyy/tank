package cn.cq.test;

import cn.cq.entity.Tank;
import cn.cq.enums.Dir;
import cn.cq.enums.Group;
import cn.cq.frame.TankFrame;
import cn.cq.util.PropertyMgr;

public class T {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();

        int imitTankCount = PropertyMgr.getInt("imitTankCount");

        for (int i = 0; i < imitTankCount; i++) {
            tf.tanks.add(new Tank(50 + i * 80, 200, Dir.DOWN, tf, Group.BAD));
        }

        while (true) {
            tf.repaint();
            Thread.sleep(50);
        }
    }
}
