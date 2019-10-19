package com.xbh.wendaPlus.ui;

import com.xbh.wendaPlus.cofig.SettingConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;

public class OtherSettingPageController extends Application {

    public AnchorPane settingPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final URL fxmlUrl = getClass().getResource("otherSettingPage.fxml");
        AnchorPane root = FXMLLoader.load(fxmlUrl);
        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest((event)->{
                primaryStage.close();
        });
    }

    public void WordsLength(MouseEvent mouseEvent) {
        clearChildren();

        Label label2 = new Label("字数大于");
        AnchorPane.setLeftAnchor(label2, 50.0);
        AnchorPane.setTopAnchor(label2, 50.0);

        TextField field2 = new TextField();;
        AnchorPane.setLeftAnchor(field2, 150.0);
        AnchorPane.setTopAnchor(field2, 50.0);

        Label label = new Label("字数小于");
        AnchorPane.setLeftAnchor(label, 50.0);
        AnchorPane.setTopAnchor(label, 100.0);

        TextField field = new TextField();
        AnchorPane.setLeftAnchor(field, 150.0);
        AnchorPane.setTopAnchor(field, 100.0);

        Button button = new Button("确认修改");
        AnchorPane.setRightAnchor(button, 50.0);
        AnchorPane.setBottomAnchor(button, 50D);

        button.setOnMouseClicked(val->{
            String max = field2.getText();
            String min = field.getText();
            if (max != null & min != null) {
                saveIssueWordsLength(min, max);
            }
        });

        settingPane.getChildren().addAll(label, field, label2, field2, button);

    }

    public void ansWordsLength(MouseEvent mouseEvent) {
        clearChildren();

        Label label2 = new Label("字数大于");
        AnchorPane.setLeftAnchor(label2, 50.0);
        AnchorPane.setTopAnchor(label2, 50.0);

        TextField field2 = new TextField();;
        AnchorPane.setLeftAnchor(field2, 150.0);
        AnchorPane.setTopAnchor(field2, 50.0);

        Label label = new Label("字数小于");
        AnchorPane.setLeftAnchor(label, 50.0);
        AnchorPane.setTopAnchor(label, 100.0);

        TextField field = new TextField();
        AnchorPane.setLeftAnchor(field, 150.0);
        AnchorPane.setTopAnchor(field, 100.0);

        Button button = new Button("确认修改");
        AnchorPane.setRightAnchor(button, 50.0);
        AnchorPane.setBottomAnchor(button, 50D);

        button.setOnMouseClicked(val->{
            String max = field2.getText();
            String min = field.getText();
            if (max != null & min != null) {
                saveAnsWordsLength(min, max);
            }
        });

        settingPane.getChildren().addAll(label, field, label2, field2, button);
    }

    private void saveAnsWordsLength(String min, String max) {
        SettingConfig.setAnsWordLengthMax(min);
        SettingConfig.setAnsWordLengthMaxMin(max);
    }


    private void clearChildren() {
        settingPane.getChildren().clear();
    }

    private void saveIssueWordsLength(String min, String max) {
        SettingConfig.setIssueWordsLengthMax(min);
        SettingConfig.setIssueWordsLengthMin(max);
    }
}
