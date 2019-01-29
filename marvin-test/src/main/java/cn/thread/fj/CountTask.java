package cn.thread.fj;

import java.util.concurrent.*;

/**
 * @program: marvin-all
 * @description: fork/join 使用
 * @author: Mr.Wang
 * @create: 2019-01-02 18:59
 **/
public class CountTask extends RecursiveTask<Integer> {
    public static final int THRESHOLD=5;//阀值
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }


    @Override
    protected Integer compute() {
        int sum = 0;
        //如果任务足够小就计算任务
        boolean canCompute = (end-start) <=THRESHOLD;
        if(canCompute) {
            for(int i =start; i <=end; i++) {
                sum += i;
            }
        }else{
            //如果任务大于阀值，就分裂成两个子任务计算
            int middle = (start+end) / 2;
            CountTask leftTask =new CountTask(start, middle);
            CountTask rightTask =new CountTask(middle + 1,end);
            //执行子任务
            leftTask.fork();
            rightTask.fork();
            //等待子任务执行完，并得到其结果
            int leftResult=leftTask.join();
            int rightResult=rightTask.join();
            //合并子任务
            sum = leftResult  + rightResult;
        }
        return sum;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(1, 100);
        Future<Integer> future = forkJoinPool.submit(task);

        System.out.println("多线程执行结果："+future.get());
        forkJoinPool.shutdown(); //关闭线程池
    }
}
