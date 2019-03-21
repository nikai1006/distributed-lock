package com.nikai.distributed.lock.order;

/**
 * distributed-lock com.nikai.distributed.lock.order
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 9:47 2019/3/21
 * @Modified By:
 */
public class Pay {

    public void pay() {
        System.out.println(Thread.currentThread().getName() + "支付成功");
    }

}
