package mytask;

import javafx.concurrent.Task;
import pcb.PCB;
import ma.Main;
import util.TaskUtil;
/**
 * @author sky
 */
/*
轮转调度算法
 */
public class RRTask extends Task<Void> {
    @Override
    protected Void call() {

        Main.runningLog.appendText("\n"+"正在执行轮转调度算法");

        //如果没有进行算法切换，则一直执行
        while (true){

            //当就绪队列中存在进程时，进行轮转调度
            while (Main.waitList.size()>0){
                TaskUtil.resetStatus(Main.waitList);//把所有进程的状态刷新为就绪态
                PCB pcb = Main.waitList.get(0);
                //模拟cpu调度
                try {
                    //轮转算法调度队列头的进程

                    pcb.setStatus("运行态");

                    if(Main.preRunPCB !=pcb){
                        Main.runningLog.appendText("\n"+pcb.getPcbId()+"号进程正在运行");
                    }

                    Thread.sleep(500);//战术停顿
                    TaskUtil.randomAddPCB();//随机生成新进程

                    if(TaskUtil.isBlock()){//如果发生阻塞
                        //将进程丢进阻塞队列
                        TaskUtil.processBlock(pcb);
                    }
                    else {
                        //剩余时间减一
                        pcb.setTime2Finish(pcb.getTime2Finish()-1);
                        //更新进度条信息
                        pcb.setProgressBar((double)(pcb.getTime4Finish()-pcb.getTime2Finish())/pcb.getTime4Finish());
                    }

                    TaskUtil.blockTimeSub();//刷新阻塞队列等待时间

                    Thread.sleep(500);//战术停顿

                    Main.preRunPCB = pcb;
                    Main.systemTime++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(pcb.getTime2Finish()<=0){//如果进程剩余时间为0，丢进阻塞队列
                    //修改状态
                    pcb.setStatus("终止态");
                    Main.runningLog.appendText("\n"+pcb.getPcbId()+"号进程终止");
                    //从就绪队列移动到终止队列
                    pcb.setFinishTime(Main.systemTime);
                    pcb.setTurnaroundTime(pcb.getFinishTime()-pcb.getArriveTime());
                    pcb.setTurnaroundTimeWithWeight((double) pcb.getTurnaroundTime()/pcb.getTime4Finish());
                    Main.waitList.remove(pcb);
                    Main.doneList.add(pcb);
                }
                else if(Main.waitList.remove(pcb)){//否则将进程丢到队尾,如果进程阻塞，则删除失败，无需添加到队尾
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
