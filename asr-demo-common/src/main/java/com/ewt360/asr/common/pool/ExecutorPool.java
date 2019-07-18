package com.ewt360.asr.common.pool;

import com.ewt360.asr.common.constant.APIConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
@Slf4j
public class ExecutorPool {
    private ExecutorService executor;

    public ExecutorPool() {
        log.info("创建线程池完成");
        executor = Executors.newFixedThreadPool(APIConstant.THREAD_MAX);
    }

    /**
     * 关闭线程池，这里要说明的是：调用关闭线程池方法后，线程池会执行完队列中的所有任务才退出
     *
     * @author allan
     * @date   2017年3月20日
     */
    public void shutdown(){
        executor.shutdown();
    }

    /**
     * 提交任务到线程池，可以接收线程返回值
     *
     * @param task
     * @return
     * @author allan
     * @date   2017年3月20日
     */
    public <T> Future<T> submit(Callable<T> task) {
        return executor.submit(task);
    }

    /**
     * 直接提交任务到线程池，无返回值
     *
     * @param task
     * @author allan
     * @date   2017年3月20日
     */
    public void execute(Runnable task){
        executor.execute(task);
    }
}
