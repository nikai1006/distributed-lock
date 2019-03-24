package com.nikai.distributed.lock.zookeeper;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
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

    private ThreadLocal<ZooKeeper> zk = new ThreadLocal<ZooKeeper>();

    public static final String LOCK_NAME = "/MYLOCK";

    private static ThreadLocal<String> CURRENT_NODE = new ThreadLocal<>();

    public void init() throws Exception {
        zk.set(new ZooKeeper("nikai.org:2181", 3000, (watchEvent) -> {
            System.out.println(watchEvent.getPath());
        }));
        CURRENT_NODE.set("");
    }

    public boolean tryLock() {
        String nodeName = LOCK_NAME + "/zk_";
        try {
            CURRENT_NODE
                .set(zk.get().create(nodeName, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL));

            List<String> children = zk.get().getChildren(LOCK_NAME, false);
            Collections.sort(children);
            System.out.println(children);
            String minNode = children.get(0);
            if ((LOCK_NAME + "/" + minNode).equals(CURRENT_NODE.get())) {
                return true;
            } else {
                int currentIndex = children
                    .indexOf(CURRENT_NODE.get().substring(CURRENT_NODE.get().lastIndexOf("/") + 1));
                String prevNodeName = children.get(currentIndex - 1);
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                zk.get().exists(LOCK_NAME + "/" + prevNodeName, (watchEvent) -> {
                    if (EventType.NodeDeleted.equals(watchEvent.getType())) {
                        countDownLatch.countDown();
                        System.out.println(Thread.currentThread().getName() + "唤醒锁");
                    }

                });
                System.out.println(Thread.currentThread().getName() + "等待锁");
                countDownLatch.wait();
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void lock() {
        try {
            init();
            if (tryLock()) {
                System.out.println("获得锁");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void unlock() {
        try {
            zk.get().delete(CURRENT_NODE.get(), -1);
            CURRENT_NODE.remove();
            zk.get().close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

}
