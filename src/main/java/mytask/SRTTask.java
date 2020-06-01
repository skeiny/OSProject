package mytask;

import comparator.SRTComparator;
import javafx.concurrent.Task;
import pcb.PCB;
import ma.Main;
import util.TaskUtil;
/**
 * @author sky
 */
/*
可抢占式最短剩余时间调度算法
 */
public class SRTTask extends Task<Void> {
    @Override
    protected Void call() {
        Main.runningLog.appendText("\n"+"正在执行最短剩余时间调度算法");
        while (true) {
            while (Main.waitList.size() > 0) {
                //重置状态
                TaskUtil.resetStatus(Main.waitList);
                //排序
                Main.waitList.sort(new SRTComparator());

                //排序后第一个为要上cpu执行的进程
                PCB pcb = Main.waitList.get(0);
                pcb.setStatus("运行态");

                if(Main.preRunPCB !=pcb){//避免重复打印某进程正在运行
                    Main.runningLog.appendText("\n"+pcb.getPcbId()+"号进程正在运行");
                }

                //模拟上cpu执行
                try {
                    Thread.sleep(500);
                    //TaskUtil.randomAddPCB();//随机生成新进程
                    if(TaskUtil.isBlock()){
                        //如果该进程阻塞了，将该进程转移到阻塞队列
                        TaskUtil.processBlock(pcb);
                    }
                    else {
                        pcb.setTime2Finish(pcb.getTime2Finish() - 1);
                        pcb.setProgressBar((double) (pcb.getTime4Finish() - pcb.getTime2Finish()) / pcb.getTime4Finish());
                    }
                    TaskUtil.blockTimeSub();
                    Thread.sleep(500);
                    Main.preRunPCB = pcb;
                    Main.systemTime++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //判断进程是否终止
                if (pcb.getTime2Finish() <= 0) {
                    //修改状态
                    pcb.setStatus("终止态");
                    Main.runningLog.appendText("\n"+pcb.getPcbId()+"号进程终止");

                    //从继续队列移动到完成队列
                    pcb.setFinishTime(Main.systemTime);
                    pcb.setTurnaroundTime(pcb.getFinishTime()-pcb.getArriveTime());
                    pcb.setTurnaroundTimeWithWeight((double) pcb.getTurnaroundTime()/pcb.getTime4Finish());
                    Main.waitList.remove(pcb);
                    Main.doneList.add(pcb);
                }

            }

            //处理就绪队列为空，阻塞队列不为空的情况
            TaskUtil.waitListEmptyBlockListNotEmpty();

            //处理就绪与阻塞队列皆为空的情况
            TaskUtil.waitListEmptyBlockListEmpty();
        }
    }
}
