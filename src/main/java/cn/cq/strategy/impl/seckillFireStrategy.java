package cn.cq.strategy.impl;

import cn.cq.entity.Tank;
import cn.cq.enums.Group;
import cn.cq.strategy.FireStrategy;

import java.util.Iterator;

public class seckillFireStrategy implements FireStrategy {
    // 秒杀
    @Override
    public void fire(Tank t) {
        Iterator<Tank> iterator = t.tankFrame.tanks.iterator();
        while (iterator.hasNext()){
            Tank next = iterator.next();
            if (next.group == Group.BAD){
                iterator.remove();
            }
        }
    }
}
