package com.nikai.distributed.lock.zookeeper;

import java.util.List;
import org.apache.zookeeper.Watcher.Event.EventType;
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

//        创建持久节点
//        zk.create("/MYLOCK", "lock".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        zk.delete("/");

        zk.exists("/MYLOCK", (watchEvent) -> {
            EventType eventType = watchEvent.getType();
            if (EventType.NodeDeleted.equals(eventType)) {
                System.out.println("节点被删掉了");
            }
        });
        System.in.read();
        zk.close();

    }

}