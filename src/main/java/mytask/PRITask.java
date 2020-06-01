package mytask;

import comparator.PRIComparator;
import javafx.concurrent.Task;
import pcb.PCB;
import ma.Main;
import util.TaskUtil;
/**
 * @author sky
 */
/*
可变优先级调度算法
 */
public class PRITask extends Task<Void> {
    @Override
    protected Void call() {

        Main.runningLog.appendText("\n"+"正在执行优先级调度算法");

        while (true) {
            while (Main.waitList.size() > 0) {
                //重置状态
                TaskUtil.resetStatus(Main.waitList);

                //按优先级排序
                Main.waitList.sort(new PRIComparator());

                //排序后第一个为要上cpu执行的进程
                PCB pcb = Main.waitList.get(0);
                pcb.setStatus("运行态");

                if(Main.preRunPCB !=pcb){
                    Main.runningLog.appendText("\n"+pcb.getPcbId()+"号进程正在运行");
                }

                try {
                    Thread.sleep(500);
                    TaskUtil.randomAddPCB();
                    if (TaskUtil.isBlock()) {
                        TaskUtil.processBlock(pcb);
                    } else {
                        pcb.setTime2Finish(pcb.getTime2Finish() - 1);
                        pcb.setPriority(pcb.getPriority() - 1);//可变优先级
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

                } else if(Main.waitList.remove(pcb)){
                    Main.waitList.add(pcb);
                }

            }

            //处理就绪队列为空，阻塞队列不为空的情况
            TaskUtil.waitListEmptyBlockListNotEmpty();

            //处理就绪与阻塞队列皆为空的情况
            TaskUtil.waitListEmptyBlockListEmpty();
        }
    }
}
