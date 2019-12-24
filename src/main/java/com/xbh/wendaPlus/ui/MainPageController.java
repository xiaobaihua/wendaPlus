package com.xbh.wendaPlus.ui;

import com.xbh.wendaPlus.Main;
import com.xbh.wendaPlus.spider.SpiderController;
import com.xbh.wendaPlus.spider.baidu.HtmlUnit.HtmlUnit;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
public class MainPageController extends Application {
    public TextField maxThread;
    public int runType = 0;
    private SpiderController sc = new SpiderController();

//    @FXML
    public ToggleGroup radioToggleGroup;

    @FXML
    private ChoiceBox selectTargetSite;

    @FXML
    private Label inputExcel;

    @FXML
    private Label outputExcel;

    @FXML
    public Label total;

    public static Map<String, Stage> stageMap = new HashMap<>();
    @FXML
    public void selectInputExcelFile() {
        final File file = this.openFile();
        if (file != null) {
            inputExcel.setText(file.getPath());
            outputExcel.setText(file.getPath());
            this.sc.setInFile(file);
            this.sc.setOutFile(file);
        }
    }

    private File openFile() {
        final Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("请选择输入EXCEL");
        final File file = fileChooser.showOpenDialog(stage);

        if (file.length() > 0) {
            return file;
        }
        throw new RuntimeException("数据获取异常");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final URL fxmlUrl = getClass().getResource("mainPage.fxml");
        AnchorPane root = FXMLLoader.load(fxmlUrl);
        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        stageMap.put("primaryStage", primaryStage);

        primaryStage.show();

        primaryStage.setOnCloseRequest((event)->{
            System.exit(0);
        });
    }

    public void selectOutputExcel(MouseEvent mouseEvent) {
        final File file = this.openFile();

        if (file != null) {
            outputExcel.setText(file.getName());
            this.sc.setOutFile(file);
        }

    }

    @FXML
    public void run(MouseEvent mouseEvent) throws IOException {
        this.selectTargetSiteUrl(null);
        this.total.setStyle("-fx-text-fill:red");
        // 改变运行模式
        SpiderController.runType = this.runType;

        // 改变线程数
        Integer value = Integer.valueOf(maxThread.getText());
        HtmlUnit.maxThread = value;

        new Thread(()->{
            try {
                sc.spiderRun(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        test();
    }

    private void test() {
        Service<String> service=new Service<String>() {
            @Override
            protected Task<String> createTask() {
                return new Task<String>() {
                    @Override
                    protected String call() throws Exception {

                        while (true){
                            //更新service的value属性
                            updateValue("最大"+(SpiderController.beanList.size() + SpiderController.beanList.size() * 20 )+ "| 已完成:" + SpiderController.completed);
//							//更新service的progress属性
//							updateProgress(a, 100d);
                            Thread.sleep(100);
                        }
                    }
                };
            }
        };

        this.total.textProperty().bind(service.valueProperty());

        service.start();
}


    public void selectTargetSiteUrl(ActionEvent actionEvent) {
        SpiderController.CurrentTargetSite = this.selectTargetSite.getValue().toString();
    }

    public void otherSetting(MouseEvent mouseEvent) throws Exception {
//        System.out.println(stageMap);
        Stage settingStage = new Stage();
        stageMap.put("settingStage", settingStage);
        OtherSettingPageController otherSettingPageController = new OtherSettingPageController();
        otherSettingPageController.start(settingStage);
    }

    public void radioClickEvent(MouseEvent mouseEvent) {
        Toggle selectedToggle = this.radioToggleGroup.getSelectedToggle();
        this.runType = Integer.valueOf(selectedToggle.getUserData().toString());
        System.out.println(this.runType);
    }
}
