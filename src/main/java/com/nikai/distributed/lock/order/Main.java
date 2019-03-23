package com.nikai.distributed.lock.order;

import com.nikai.distributed.lock.redis.RedisLock;

/**
 * distributed-lock com.nikai.distributed.lock.order
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 9:31 2019/3/21
 * @Modified By:
 */
public class Main {

    public static void main(String[] args) {
        Thread t1 = new Thread(new UserThread());
        Thread t2 = new Thread(new UserThread());

        t1.start();
        t2.start();
    }

  static   private RedisLock lock = new RedisLock();
    static class UserThread implements Runnable {

        @Override
        public void run() {
            lock.lock("1","2",3);
            new Order().createOrder();
            Boolean result = new Stock().reduceGoods();
            lock.unlock("1","2");
            if (result) {
                System.out.println(Thread.currentThread().getName() + "减库存成功");
                new Pay().pay();

            } else {
                System.out.println(Thread.currentThread().getName() + "减库存失败");
            }
        }
    }
}
