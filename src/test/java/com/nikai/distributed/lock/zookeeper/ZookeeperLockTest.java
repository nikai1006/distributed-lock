package com.nikai.distributed.lock.zookeeper;

import java.util.List;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

/**
 * distributed-lock com.nikai.distributed.lock.zookeeper
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 13:23 2019/3/24
 * @Modified By:
 */
public class ZookeeperLockTest {

    @Test
    public void create() throws Exception {
        ZooKeeper zk = new ZooKeeper("nikai.org:2181", 3000, (watchEvent) -> {
            System.out.println(watchEvent.getPath());
        });
        List<String> children = zk.getChildren("/myserver", false);
        children.forEach(child -> System.out.println(child));

        zk.create("/MYLOCK","lock".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

}