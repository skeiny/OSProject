package util;

import javafx.collections.ObservableList;
import ma.Main;
import pcb.PCB;

/**
 * @author sky
 */
public class TaskUtil {

    //重置进程状态
    public static void resetStatus(ObservableList<PCB> list){
        for(PCB each:list){
            if (each.getTime2Finish()<=0){
                each.setStatus("终止态");
                Main.waitList.remove(each);
                Main.doneList.add(each);
            }
            else {
                each.setStatus("就绪态");
            }

        }
    }

    //判断进程是否阻塞
    public static boolean isBlock(){
        int random = (int)(9*Math.random());
        if(random==5){
            return true;
        }
        return false;
    }

    //处于阻塞态的进程阻塞时间减少
    public static void blockTimeSub(){
        //时间统统减一
        for(int i=0;i<Main.blockList.size();i++){
            PCB pcb = Main.blockList.get(i);
            pcb.setTime2Unblock(pcb.getTime2Unblock()-1);
            pcb.setProgressBar((double)(pcb.getTime4Block()-pcb.getTime2Unblock())/pcb.getTime4Block());
        }
        //剩余阻塞时间为0就离开阻塞队列
        for(int i=0;i<Main.blockList.size();i++){
            PCB pcb = Main.blockList.get(i);
            if(pcb.getTime2Unblock()<=0){
                Main.waitList.add(pcb);
                Main.blockList.remove(pcb);
                pcb.setStatus("就绪态");
                pcb.setProgressBar((double)(pcb.getTime4Finish()-pcb.getTime2Finish())/pcb.getTime4Finish());
                Main.runningLog.appendText("\n"+pcb.getPcbId()+"号进程结束阻塞，进入就绪");
            }
        }
    }

    //随机增加进程
    public static void randomAddPCB(){
        //如果当前进程数已经到达最大值，添加失败
        if((Main.waitList.size()+Main.blockList.size())>=Main.MAX_PCB){
            return;
        }
        //生成随机数判断能否生成（概率事件）
        int random = (int)(9*Math.random());
        if(random==3){
            int x = (int)(10*Math.random()+1);
            int y = (int)(10*Math.random()+1);
            PCB pcb = new PCB(x,y);
            Main.waitList.add(pcb);
            Main.runningLog.appendText("\n"+pcb.getPcbId()+"号进程被创建，进入就绪");
        }
        return;
    }

    //进程发生阻塞时的一些操作
    public static void processBlock(PCB pcb){
        Main.waitList.remove(pcb);
        Main.blockList.add(pcb);
        pcb.setStatus("阻塞态");
        int blockTime = (int)(8*Math.random()+3);
        String[] reason4Blocks = {"I/O阻塞","等待缓冲","等待其他资源"};
        pcb.setReason4Block(reason4Blocks[(int)(3*Math.random())]);
        pcb.setTime4Block(blockTime);
        pcb.setTime2Unblock(blockTime);
        pcb.setProgressBar((double)(pcb.getTime4Block()-pcb.getTime2Unblock())/pcb.getTime4Block());
        Main.runningLog.appendText("\n"+pcb.getPcbId()+"号进程阻塞，预计阻塞时间："+blockTime+"秒");

    }

    //就绪队列为空，阻塞队列不为空
    public static void waitListEmptyBlockListNotEmpty(){
        //处理就绪队列为空，阻塞队列不为空的情况
        while (Main.blockList.size()>0){
            try {
                Thread.sleep(1000);
                Main.systemTime++;
                //新进程也可能随机添加进来
                TaskUtil.randomAddPCB();
                //阻塞队列继续倒计时
                TaskUtil.blockTimeSub();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(Main.waitList.size()>0){ //判断就绪队列是否为空
                break;
            }
        }

    }

    //就绪队列与阻塞队列都为空
    public static void waitListEmptyBlockListEmpty(){
        Main.runningLog.appendText("\n"+"没有进程，等待进程生成中");
        Main.runningLog.appendText("\n"+"可手动添加");
        while (Main.waitList.size()<=0&&Main.blockList.size()<=0){
            try {
                Thread.sleep(1000);
                Main.systemTime++;
                //新进程也可能随机添加进来
                TaskUtil.randomAddPCB();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
