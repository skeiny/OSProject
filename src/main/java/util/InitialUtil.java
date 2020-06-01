package util;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import ma.Main;
import mytask.PRITask;
import mytask.RRTask;
import mytask.SPNTask;
import mytask.SRTTask;
import pcb.PCB;

/**
 * @author: Sky
 */
public class InitialUtil {

    //生成一个表格并初始化
    public static TableView<PCB> createATableView(ObservableList<PCB> list, int type){

        TableView<PCB> tableView= new TableView<>();

        tableView.setStyle("-fx-background-color: White;");

        TableColumn<PCB,Number> pcbId = new TableColumn<>("ID");
        TableColumn<PCB,String> status = new TableColumn<>("状态");
        TableColumn<PCB,Double>  progressBar= new TableColumn<>("进度条");

        pcbId.setCellValueFactory(new PropertyValueFactory<>("pcbId"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        progressBar.setCellValueFactory(new PropertyValueFactory<>("progressBar"));

        pcbId.setSortable(false);
        status.setSortable(false);
        progressBar.setSortable(false);

        //把Double类型的进度条设置成进度条格式
        progressBar.setCellFactory(ProgressBarTableCell.forTableColumn());

        if(type==0){//等待队列的表
            //创建列
            TableColumn<PCB,Number> priority = new TableColumn<>("优先级");
            TableColumn<PCB,Number> time4Finish = new TableColumn<>("所需运行时间");
            TableColumn<PCB,Number> time2Finish = new TableColumn<>("剩余运行时间");
            //设计数据
            priority.setCellValueFactory(new PropertyValueFactory<>("priority"));
            time4Finish.setCellValueFactory(new PropertyValueFactory<>("time4Finish"));
            time2Finish.setCellValueFactory(new PropertyValueFactory<>("time2Finish"));
            //设置不可点击列排序
            priority.setSortable(false);
            time4Finish.setSortable(false);
            time2Finish.setSortable(false);

            //调整列宽
            pcbId.setPrefWidth(50);
            status.setPrefWidth(60);
            priority.setPrefWidth(60);
            time4Finish.setPrefWidth(100);
            time2Finish.setPrefWidth(100);
            progressBar.setPrefWidth(150);

            //添加列
            tableView.getColumns().addAll(pcbId,status,priority,time4Finish,time2Finish,progressBar);

            //设置位置
            tableView.setLayoutX(50);
            tableView.setLayoutY(30);
        }
        else if (type==1){//阻塞队列的表

            TableColumn<PCB,Double>  reason4Block= new TableColumn<>("阻塞原因");
            TableColumn<PCB,Number>  time2Unblock= new TableColumn<>("阻塞剩余时间")
                    ;
            time2Unblock.setCellValueFactory(new PropertyValueFactory<>("time2Unblock"));
            reason4Block.setCellValueFactory(new PropertyValueFactory<>("reason4Block"));

            reason4Block.setSortable(false);
            time2Unblock.setSortable(false);

            pcbId.setPrefWidth(50);
            status.setPrefWidth(60);
            reason4Block.setPrefWidth(100);
            time2Unblock.setPrefWidth(100);
            progressBar.setPrefWidth(150);

            tableView.getColumns().addAll(pcbId,status,reason4Block,time2Unblock,progressBar);
            tableView.setLayoutX(600);
            tableView.setLayoutY(30);
        }
        else {//终止队列的表

            TableColumn<PCB,Double>  arriveTime= new TableColumn<>("到达时间");
            TableColumn<PCB,Double>  finishTime= new TableColumn<>("结束时间");
            TableColumn<PCB,Double>  turnaroundTime= new TableColumn<>("周转时间");
            TableColumn<PCB,Double>  turnaroundTimeWithWeight= new TableColumn<>("带权周转时间");

            arriveTime.setCellValueFactory(new PropertyValueFactory<>("arriveTime"));
            finishTime.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
            turnaroundTime.setCellValueFactory(new PropertyValueFactory<>("turnaroundTime"));
            turnaroundTimeWithWeight.setCellValueFactory(new PropertyValueFactory<>("turnaroundTimeWithWeight"));

            arriveTime.setSortable(false);
            finishTime.setSortable(false);
            turnaroundTime.setSortable(false);
            turnaroundTimeWithWeight.setSortable(false);

            pcbId.setPrefWidth(50);
            status.setPrefWidth(60);
            arriveTime.setPrefWidth(80);
            finishTime.setPrefWidth(80);
            turnaroundTime.setPrefWidth(80);
            turnaroundTimeWithWeight.setPrefWidth(100);

            tableView.getColumns().addAll(pcbId,status,arriveTime,finishTime,turnaroundTime,turnaroundTimeWithWeight);

            tableView.setLayoutX(1090);
            tableView.setLayoutY(30);
        }

        tableView.setItems(list);
        return tableView;
    }

    public static void initialTextArea(){
        Main.runningLog.setLayoutX(100);
        Main.runningLog.setLayoutY(450);
        Main.runningLog.appendText("运行日志");
        Main.runningLog.editableProperty().setValue(false);
        Main.runningLog.setPrefSize(400,400);
        Main.runningLog.setStyle("-fx-background-color: White;");
        Main.runningLog.setFont(new Font(20));
    }

    public static HBox initialAlgorithmSelection(){
        Button op_RR = ButtonUtil.createButton("时间片轮转");
        op_RR.setOnAction(event -> {
            Main.runningThread.stop();
            Main.runningThread = new Thread(new RRTask());
            Main.runningThread.start();
        });

        Button op_Pri = ButtonUtil.createButton("可变优先级");
        op_Pri.setOnAction(event -> {
            Main.runningThread.stop();
            Main.runningThread = new Thread(new PRITask());
            Main.runningThread.start();
        });

        Button op_SPN = ButtonUtil.createButton("最短进程");
        op_SPN.setOnAction(event -> {
            Main.runningThread.stop();
            Main.runningThread = new Thread(new SPNTask());
            Main.runningThread.start();
        });

        Button op_SRT = ButtonUtil.createButton("最短剩余时间");
        op_SRT.setOnAction(event -> {
            Main.runningThread.stop();
            Main.runningThread = new Thread(new SRTTask());
            Main.runningThread.start();
        });

        HBox algorithmSelection = new HBox(10);
        algorithmSelection.getChildren().addAll(op_RR,op_Pri,op_SPN,op_SRT);

        algorithmSelection.setLayoutX(600);
        algorithmSelection.setLayoutY(725);

        return algorithmSelection;
    }

    public static HBox initialFunctionSelection(){
        Button addPcb = ButtonUtil.createButton("添加进程");
        addPcb.setOnAction(event->{
            addPCB();
        });


        Button interrupt = ButtonUtil.createButton("暂停调度");
        interrupt.setOnAction(event->{

            if(Main.runningThread.isAlive()) {
                Main.runningThread.stop();
            }
            Main.runningLog.appendText("\n"+"调度已暂停");

        });

        Button reset = ButtonUtil.createButton("重置");
        reset.setOnAction(event->{
            if(Main.runningThread.isAlive()) {
                Main.runningThread.stop();
            }
            Main.waitList.clear();
            Main.blockList.clear();
            Main.doneList.clear();
            Main.systemTime=0;
            PCB.setCurPcbId(1);
            Main.runningLog.appendText("\n"+"已重置");
        });

        Button close = ButtonUtil.createButton("退出");
        close.setOnAction(event->{
            Main.runningThread.stop();
            Main.myStage.close();
        });




        HBox functionSelection = new HBox(10);
        functionSelection.getChildren().addAll(addPcb,interrupt,reset,close);
        functionSelection.setLayoutX(600);
        functionSelection.setLayoutY(805);

        return functionSelection;
    }

    public static TextArea initialTipsArea(){
        TextArea tipsArea = new TextArea();
        tipsArea.setEditable(false);
        tipsArea.setFont(new Font(18));
        tipsArea.setText("不温馨提示：\n" +
                "(1)最多同时有10个PCB在内存\n" +
                "(2)建议最开始手动添加几个进程\n" +
                "(3)从左往右依次为：运行与就绪队列、阻塞队列、终止队列\n" +
                "(4)点击算法即可进行调度（会随机生成进程，因为是随机事件所以需要等待）\n" +
                "(5)算法可随便切换（不需要暂停），可暂停观察日志，暂停后需重新点击算法\n" +
                "(6)为了方便测试，生成的进程基本为短进程，进程生成概率与阻塞概率均为1/9\n" +
                "(7)进程会随机发生阻塞，并随机生成阻塞时间与阻塞原因，阻塞结束会返回就绪队列\n" +
                "(8)文本编码方式:UTF-8，尚存不足，请多指教");
        tipsArea.setStyle("-fx-background-color: White;");
        tipsArea.setPrefSize(700,230);

        tipsArea.setLayoutX(600);
        tipsArea.setLayoutY(Main.runningLog.getLayoutY());

        return tipsArea;

    }

    private static void addPCB(){
        if((Main.waitList.size()+Main.blockList.size())>=Main.MAX_PCB){
            Main.runningLog.appendText("\n"+"程序控制块数量已满，无法添加");
            return;
        }
        int x = (int)(10*Math.random()+1);
        int y = (int)(10*Math.random()+1);
        Main.waitList.add(new PCB(x,y));
        Main.runningLog.appendText("\n"+"手动添加了新进程");
        return;
    }
}
