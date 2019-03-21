package com.nikai.distributed.lock.order;

/**
 * distributed-lock com.nikai.distributed.lock.order
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 9:31 2019/3/21
 * @Modified By:
 */
public class Order {

    public void createOrder() {
        System.out.println(Thread.currentThread().getName() + "创建订单");
    }

}
