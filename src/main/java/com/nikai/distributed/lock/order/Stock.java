package com.nikai.distributed.lock.order;

/**
 * distributed-lock com.nikai.distributed.lock.order
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 9:31 2019/3/21
 * @Modified By:
 */
public class Stock {

    public static int GOODS_COUNT = 1;

    public Boolean reduceGoods() {
        if (GOODS_COUNT > 0) {
            GOODS_COUNT--;
            return true;
        }
        return false;
    }

}
