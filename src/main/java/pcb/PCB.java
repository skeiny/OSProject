package pcb;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import ma.Main;
/**
 * @author sky
 */

public class PCB {

    //一个PCB中的属性
    private SimpleIntegerProperty pcbId = new SimpleIntegerProperty();//ID
    private SimpleStringProperty status = new SimpleStringProperty();//状态
    private SimpleIntegerProperty priority = new SimpleIntegerProperty();//优先级
    private SimpleIntegerProperty time4Finish = new SimpleIntegerProperty();//运行所需时间
    private SimpleIntegerProperty time2Finish = new SimpleIntegerProperty();//剩余时间
    private SimpleIntegerProperty time4Block = new SimpleIntegerProperty();//阻塞所需总时间
    private SimpleIntegerProperty time2Unblock = new SimpleIntegerProperty();//阻塞剩余时间
    private SimpleIntegerProperty arriveTime = new SimpleIntegerProperty();//到达时间
    private SimpleIntegerProperty finishTime = new SimpleIntegerProperty();//结束时间
    private SimpleIntegerProperty turnaroundTime = new SimpleIntegerProperty();//周转时间
    private SimpleDoubleProperty turnaroundTimeWithWeight = new SimpleDoubleProperty();//带权周转时间
    private SimpleStringProperty reason4Block = new SimpleStringProperty();//阻塞原因
    private SimpleDoubleProperty progressBar = new SimpleDoubleProperty();//进度条
    private static int curPcbId = 1;//下一个进程的ID号

    public PCB(int priority,int time4Finish) {
        //初始化一个进程
        this.pcbId.set(curPcbId++);
        this.status.set("就绪态");
        this.priority.set(priority);
        this.time4Finish.set(time4Finish);
        this.time2Finish.set(time4Finish);
        this.time4Block.set(0);
        this.reason4Block.set(null);
        this.progressBar.set(0);
        this.arriveTime.set(Main.systemTime);
    }

    //没用到的getter不能删，TableView的反射机制需要用到getter
    public int getPcbId() {
        return pcbId.get();
    }

    public SimpleIntegerProperty pcbIdProperty() {
        return pcbId;
    }

    public void setPcbId(int pcbId) {
        this.pcbId.set(pcbId);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public int getPriority() {
        return priority.get();
    }

    public SimpleIntegerProperty priorityProperty() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority.set(priority);
    }

    public int getTime4Finish() {
        return time4Finish.get();
    }

    public SimpleIntegerProperty time4FinishProperty() {
        return time4Finish;
    }

    public void setTime4Finish(int time4Finish) {
        this.time4Finish.set(time4Finish);
    }

    public int getTime2Finish() {
        return time2Finish.get();
    }

    public SimpleIntegerProperty time2FinishProperty() {
        return time2Finish;
    }

    public void setTime2Finish(int time2Finish) {
        this.time2Finish.set(time2Finish);
    }

    public SimpleDoubleProperty progressBarProperty() {
        return progressBar;
    }

    public double getProgressBar() {
        return progressBar.get();
    }

    public void setProgressBar(double progressBar) {
        this.progressBar.set(progressBar);
    }

    public int getTime4Block() {
        return time4Block.get();
    }

    public SimpleIntegerProperty time4BlockProperty() {
        return time4Block;
    }

    public void setTime4Block(int time4Block) {
        this.time4Block.set(time4Block);
    }

    public String getReason4Block() {
        return reason4Block.get();
    }

    public SimpleStringProperty reason4BlockProperty() {
        return reason4Block;
    }

    public void setReason4Block(String reason4Block) {
        this.reason4Block.set(reason4Block);
    }

    public int getTime2Unblock() {
        return time2Unblock.get();
    }

    public SimpleIntegerProperty time2UnblockProperty() {
        return time2Unblock;
    }

    public void setTime2Unblock(int time2Unblock) {
        this.time2Unblock.set(time2Unblock);
    }

    public int getArriveTime() {
        return arriveTime.get();
    }

    public SimpleIntegerProperty arriveTimeProperty() {
        return arriveTime;
    }

    public void setArriveTime(int arriveTime) {
        this.arriveTime.set(arriveTime);
    }

    public int getFinishTime() {
        return finishTime.get();
    }

    public SimpleIntegerProperty finishTimeProperty() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime.set(finishTime);
    }

    public int getTurnaroundTime() {
        return turnaroundTime.get();
    }

    public SimpleIntegerProperty turnaroundTimeProperty() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime.set(turnaroundTime);
    }

    public double getTurnaroundTimeWithWeight() {
        return turnaroundTimeWithWeight.get();
    }

    public SimpleDoubleProperty turnaroundTimeWithWeightProperty() {
        return turnaroundTimeWithWeight;
    }

    public void setTurnaroundTimeWithWeight(double turnaroundTimeWithWeight) {
        this.turnaroundTimeWithWeight.set(turnaroundTimeWithWeight);
    }

    public static int getCurPcbId() {
        return curPcbId;
    }

    public static void setCurPcbId(int curPcbId) {
        PCB.curPcbId = curPcbId;
    }
}
