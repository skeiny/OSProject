package ma;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pcb.PCB;
import util.InitialUtil;


/**
 * @author sky
 */
public class Main extends Application {

    //共享变量
    public static int systemTime = 0;//系统时间
    public static int MAX_PCB = 10;//PCB数量的最大值
    public static ObservableList<PCB> waitList =  FXCollections.observableArrayList();//就绪队列
    public static ObservableList<PCB> blockList = FXCollections.observableArrayList();//阻塞队列
    public static ObservableList<PCB> doneList = FXCollections.observableArrayList();//终止队列

    public static Thread runningThread = new Thread();//当前正在运行的线程
    public static TextArea runningLog = new TextArea();//运行日志

    public static PCB preRunPCB;//上一个运行的进程的PCB

    public static Stage myStage = new Stage();


    @Override
    public void start(Stage primaryStage) {

        //创建TableView
        TableView<PCB> waitTableView = InitialUtil.createATableView(waitList,0);
        TableView<PCB> blockTableView = InitialUtil.createATableView(blockList,1);
        TableView<PCB> doneTableView= InitialUtil.createATableView(doneList,2);

        //初始化日志栏
        InitialUtil.initialTextArea();

        //初始化提示框
        TextArea tipsArea = InitialUtil.initialTipsArea();

        //初始化按钮
        HBox algorithmSelection = InitialUtil.initialAlgorithmSelection();
        HBox functionSelection = InitialUtil.initialFunctionSelection();

        //初始化root，并将上述组件组合
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1600,900);
        root.setStyle("-fx-background-color:white;");
        root.getChildren().addAll(waitTableView,blockTableView,doneTableView,Main.runningLog,tipsArea,algorithmSelection,functionSelection);

        //设置Scene
        Scene myScene = new Scene(root);

        //设置Stage
        myStage.setScene(myScene);
        //设置图标
        myStage.getIcons().add(new Image(String.valueOf(Main.class.getClassLoader().getResource("程序图标.png"))));
        //设置标题
        myStage.setTitle("单处理器进程调度");
        //展示
        myStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
