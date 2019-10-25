package com.xbh.wendaPlus.ui;

import com.xbh.wendaPlus.cofig.SettingConfig;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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

    public void issueConfig(MouseEvent mouseEvent) {
        clearChildren();

        // 创建字体设置
        createIssueConfigUi();
    }

    public void createIssueConfigUi() {
        Label issueTitleLabel = new Label("问题描述设置");
        issueTitleLabel.setFont(new Font("", 40));
        AnchorPane.setLeftAnchor(issueTitleLabel, 50.0);
        AnchorPane.setLeftAnchor(issueTitleLabel, 30D);

        Label label2 = new Label("字数大于");
        AnchorPane.setLeftAnchor(label2, 50.0);
        AnchorPane.setTopAnchor(label2, 100.0);

        TextField field2 = new TextField();;
        AnchorPane.setLeftAnchor(field2, 150.0);
        AnchorPane.setTopAnchor(field2, 100.0D);

        Label label = new Label("字数小于");
        AnchorPane.setLeftAnchor(label, 50.0);
        AnchorPane.setTopAnchor(label, 150.0);

        TextField field = new TextField();
        AnchorPane.setLeftAnchor(field, 150.0);
        AnchorPane.setTopAnchor(field, 150.0);

        Button button = new Button("确认修改");
        AnchorPane.setRightAnchor(button, 50.0);
        AnchorPane.setBottomAnchor(button, 50D);

        // 模式选择框
        Node node = createResultModeRadio("字数优先", "质量优先");
        AnchorPane.setRightAnchor(node, 100.0);
        AnchorPane.setTopAnchor(node, 200d);


        button.setOnMouseClicked(val->{
            String max = field2.getText();
            String min = field.getText();
            if (max != null && min != null && !max.equals("") && !max.equals("")) {
                saveIssueWordsLength(min, max);
            }
        });

        settingPane.getChildren().addAll(label, field, label2, field2, button, node, issueTitleLabel);

    }

    public void ansWordsLength(MouseEvent mouseEvent) {
        clearChildren();

        Label ansLabel = new Label("答案设置");
        ansLabel.setFont(new Font("", 40));
        AnchorPane.setLeftAnchor(ansLabel, 50.0);
        AnchorPane.setLeftAnchor(ansLabel, 30D);

        Label label2 = new Label("字数大于");
        AnchorPane.setLeftAnchor(label2, 50.0);
        AnchorPane.setTopAnchor(label2, 100.0);

        TextField field2 = new TextField();;
        AnchorPane.setLeftAnchor(field2, 150.0);
        AnchorPane.setTopAnchor(field2, 100.0);

        Label label = new Label("字数小于");
        AnchorPane.setLeftAnchor(label, 50.0);
        AnchorPane.setTopAnchor(label, 150.0);

        TextField field = new TextField();
        AnchorPane.setLeftAnchor(field, 150.0);
        AnchorPane.setTopAnchor(field, 150.0);

        Button button = new Button("确认修改");
        AnchorPane.setRightAnchor(button, 50.0);
        AnchorPane.setBottomAnchor(button, 50D);

        // 模式选择框
        Node node = createIssueModeRadio("字数优先", "质量优先");
        AnchorPane.setRightAnchor(node, 100.0);
        AnchorPane.setTopAnchor(node, 200d);

        button.setOnMouseClicked(val->{
            String max = field2.getText();
            String min = field.getText();
            if (max != null & min != null) {
                saveAnsWordsLength(min, max);
            }
        });

        settingPane.getChildren().addAll(label, field, label2, field2, button, node, ansLabel);
    }

    private void saveAnsWordsLength(String min, String max) {
        SettingConfig.setAnsWordLengthMax(min);
        SettingConfig.setAnsWordLengthMaxMin(max);
    }

    private Node createIssueModeRadio(String... strings) {
        HBox hBox = new HBox();

        final ToggleGroup group = new ToggleGroup();

        for (int i = 0; i < strings.length; i++) {
            RadioButton radioButton = new RadioButton(strings[i]);
            radioButton.setToggleGroup(group);
            hBox.getChildren().add(radioButton);
            radioButton.setUserData(strings[i]);
            if (i == 0) {
                radioButton.setSelected(true);
            }
        }


        group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov,
                                                    Toggle old_toggle, Toggle new_toggle) -> {
            if (group.getSelectedToggle() != null) {
                Object data = group.getSelectedToggle().getUserData();
                SettingConfig.issueMode = data.toString();
                System.out.println(SettingConfig.issueMode);
            }
        });
        return hBox;
    }

    private Node createResultModeRadio(String... strings) {
        HBox hBox = new HBox();

        final ToggleGroup group = new ToggleGroup();

        for (int i = 0; i < strings.length; i++) {
            RadioButton radioButton = new RadioButton(strings[i]);
            radioButton.setToggleGroup(group);
            hBox.getChildren().add(radioButton);
            radioButton.setUserData(strings[i]);
            if (i == 0) {
                radioButton.setSelected(true);
            }
        }


        group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov,
                                                    Toggle old_toggle, Toggle new_toggle) -> {
            if (group.getSelectedToggle() != null) {
                Object data = group.getSelectedToggle().getUserData();
                SettingConfig.resultMode = data.toString();
                System.out.println(SettingConfig.resultMode);
            }
        });
        return hBox;
    }

    private void clearChildren() {
        settingPane.getChildren().clear();
    }

    private void saveIssueWordsLength(String min, String max) {
        SettingConfig.setIssueWordsLengthMax(min);
        SettingConfig.setIssueWordsLengthMin(max);
    }
}
