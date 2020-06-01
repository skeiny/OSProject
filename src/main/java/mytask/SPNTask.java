package mytask;

import comparator.SPNComparator;
import javafx.concurrent.Task;
import pcb.PCB;
import ma.Main;
import util.TaskUtil;
/**
 * @author sky
 */
/*
非抢占式最短进程调度算法
 */
public class SPNTask extends Task<Void> {
    @Override
    protected Void call() {
        Main.runningLog.appendText("\n"+"正在执行最短进程调度算法");

        while (true) {

            while (Main.waitList.size() > 0) {

                TaskUtil.resetStatus(Main.waitList);

                Main.waitList.sort(new SPNComparator());
                PCB pcb = Main.waitList.get(0);
                pcb.setStatus("运行态");
                Main.runningLog.appendText("\n"+pcb.getPcbId()+"号进程正在运行");

                //Main.currentPCB.setText("当前运行的进程ID:"+pcb.getPcbId());
                while (Main.waitList.contains(pcb)){
                    //模拟上cpu执行
                    try {
                        Thread.sleep(500);
                        TaskUtil.randomAddPCB();//随机生成新进程
                        if(TaskUtil.isBlock()){
                            TaskUtil.processBlock(pcb);
                        }
                        else {
                            pcb.setTime2Finish(pcb.getTime2Finish() - 1);
                            pcb.setProgressBar((double) (pcb.getTime4Finish() - pcb.getTime2Finish()) / pcb.getTime4Finish());
                        }
                        TaskUtil.blockTimeSub();
                        Thread.sleep(500);
                        Main.systemTime++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (pcb.getTime2Finish() <= 0) {
                        pcb.setStatus("终止态");
                        Main.runningLog.appendText("\n"+pcb.getPcbId()+"号进程终止");
                        pcb.setFinishTime(Main.systemTime);
                        pcb.setTurnaroundTime(pcb.getFinishTime()-pcb.getArriveTime());
                        pcb.setTurnaroundTimeWithWeight((double) pcb.getTurnaroundTime()/pcb.getTime4Finish());
                        Main.doneList.add(pcb);
                        Main.waitList.remove(pcb);
                    }
                }
            }


            //处理就绪队列为空，阻塞队列不为空的情况
            TaskUtil.waitListEmptyBlockListNotEmpty();

            //处理就绪与阻塞队列皆为空的情况
            TaskUtil.waitListEmptyBlockListEmpty();
        }
    }

}
