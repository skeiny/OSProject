package util;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ma.Main;

import java.io.File;
import java.net.URL;

/**
 * @author sky
 */
public class ButtonUtil {

    //创建一个Button，button的文字为函数参数
    public static Button createButton(String buttonName) {
        Button button = new Button();
        button.setId(buttonName);
        button.setPadding(new Insets(10, 10, 10, 10));
        button.setStyle("-fx-background-color:White;");
        //String path = String.valueOf(ButtonUtil.class.getClassLoader().getResource("icons\\"+buttonName+".png"));
        //System.out.println(path);
        URL url = ButtonUtil.class.getClassLoader().getResource(buttonName+".png");
        button.setGraphic(new ImageView(new Image(String.valueOf(url),30,30,true,true)));

       // button.setGraphic(new ImageView(new Image("file:"+new File("icons\\"+buttonName+".png"),30,30,true,true)));
        button.setTooltip(new Tooltip(buttonName));
        button.setOnMouseEntered(event -> {
            button.setStyle("-fx-background-color:#e6e6e6;");
        });
        button.setOnMouseExited(event -> {
            button.setStyle("-fx-background-color:White;");
        });
        button.setText(buttonName);
        button.setPrefSize(150,50);
        return button;
    }
}
