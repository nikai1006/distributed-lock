package com.nikai.distributed.lock.zookeeper;

import org.apache.zookeeper.ZooKeeper;

/**
 * distributed-lock com.nikai.distributed.lock.zookeeper
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 1:49 2019/3/21
 * @Modified By:
 */
public class ZookeeperLock {

    private ZooKeeper zooKeeper = null;

    public void init() throws Exception {
        zooKeeper = new ZooKeeper("nikai.org:2181", 3000, (watchEvent) -> {
            System.out.println(watchEvent.getPath());
        });

//        zooKeeper.create()
    }

    public boolean tryLock() {

        return false;
    }

}
