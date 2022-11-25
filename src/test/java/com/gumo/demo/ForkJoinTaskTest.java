package com.gumo.demo;

import cn.hutool.core.collection.ListUtil;
import com.gumo.demo.elasticsearch.pojo.Item;
import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * ForkJoinPool与ThreadPool思考
 * 共同点
 * 它们都是线程池中有多个线程，能够将任务进行执行
 * 区别
 * ForkJoinPool是将一个任务分配为多个子任务来进行并发执行。ThreadPool是将不同任务放入队列进行分配执行
 */
public class ForkJoinTaskTest {

    public static void main(String[] args) {
        //异步处理 提升插入速度
        ForkJoinPool forkJoinPool = new ForkJoinPool(16);
//        ForkJoinTask forkJoinTask = new ForkJoinTask(list, 0, list.size() - 1);
//        forkJoinPool.invoke(forkJoinTask);

    }

    /**
     * 同步新增类型ForkJoinTask
     */
    class ForkJoinTask extends RecursiveAction {
        private static final long serialVersionUID = 1608518099L;
        // 起始索引
        private int indexStart;
        // 终止索引
        private int indexEnd;
        private List<Item> list;

        public ForkJoinTask(List<Item> list, Integer indexStart, Integer indexEnd) {
            this.indexStart = indexStart;
            this.indexEnd = indexEnd;
            this.list = list;
        }
        @SneakyThrows
        @Override
        protected void compute() {
            // 定义当前数据的长度
            long range = indexEnd - indexStart + 1;
            // 是否要拆分的临界值
            int threshold = 500;
            if (range <= threshold) {  // 小于阈值，直接计算
//                buildPassAuthPersonDevicePojoList(list);
                // 2.同步到ES，实现批量新增
//                passAuthPersonDeviceSupport.saveAll(list);
                // 3. 记录到同步历史表
//                saveSyncLog(list);
                return;
            } else {  // 继续拆分
                // 获取中间值
                int middle = (indexStart + indexEnd) / 2;
                List<Item> lefts = list.subList(indexStart, middle + 1);
                ForkJoinTask left = new ForkJoinTask(lefts, 0, lefts.size() - 1);
                List<Item> righs = list.subList(middle + 1, indexEnd + 1);
                ForkJoinTask right = new ForkJoinTask(righs, 0, righs.size() - 1);
                invokeAll(left, right);
                left.join();
                right.join();
                return;
            }
        }
    }
}
