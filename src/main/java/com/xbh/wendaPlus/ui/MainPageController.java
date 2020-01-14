package com.xbh.wendaPlus.ui;

import com.sun.javafx.binding.StringFormatter;
import com.xbh.wendaPlus.ArticleController;
import com.xbh.wendaPlus.AskController;
import com.xbh.wendaPlus.Main;
import com.xbh.wendaPlus.cofig.ArticleUIConfig;
import com.xbh.wendaPlus.spider.SpiderController;
import com.xbh.wendaPlus.spider.baidu.HtmlUnit.HtmlUnit;
import com.xbh.wendaPlus.url.UrlFactory;
import javafx.application.Application;
import javafx.beans.property.StringProperty;
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
import javafx.scene.text.Text;
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
                System.exit(-1);
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
                    if (runType == 0) {
                        updateValue("最大"+(SpiderController.beanList.size() + (SpiderController.beanList.size() * 10) + (SpiderController.beanList.size() * 3) )+ "| 已完成:" + SpiderController.completed);
//							//更新service的progress属性
//							updateProgress(a, 100d);
                        Thread.sleep(100);
                    } else if (runType == 1) {
                        updateValue("最大"+ArticleController.articleBeanList.size()+ "| 已完成:" + ArticleController.completed);
//							//更新service的progress属性
//							updateProgress(a, 100d);
                        Thread.sleep(100);
                    }
                }
                    }
            };
            }
        };

        this.total.textProperty().bind(service.valueProperty());

        service.start();
}


    public void selectTargetSiteUrl(ActionEvent actionEvent) {
        // 如果是问答模式
        if (this.runType == 0) {
            AskController.CurrentTargetSite = this.selectTargetSite.getValue().toString();
        } else if (this.runType == 1) {
            // 文章模式
            // 改变文章控制器的站点id
            String siteName = this.selectTargetSite.getValue().toString();

            ArticleController.siteType = ArticleUIConfig.articleNameMap.get(siteName);
        }
    }

    public void otherSetting(MouseEvent mouseEvent) throws Exception {
        Stage settingStage = new Stage();
        stageMap.put("settingStage", settingStage);
        OtherSettingPageController otherSettingPageController = new OtherSettingPageController();
        otherSettingPageController.start(settingStage);
    }

    public void radioClickEvent(MouseEvent mouseEvent) {
        Toggle selectedToggle = this.radioToggleGroup.getSelectedToggle();
        this.runType = Integer.valueOf(selectedToggle.getUserData().toString());
        // 判断模式
        // 如果是文章模式
        if (this.runType == 1) {
            // 重置目标站点
            this.selectTargetSite.getItems().remove(0, this.selectTargetSite.getItems().size());
//            this.selectTargetSite.getItems().removeAll(this.selectTargetSite.getItems());
            this.selectTargetSite.setValue("百度文库");
            for (String siteName : ArticleUIConfig.articleNameMap.keySet()) {
                this.selectTargetSite.getItems().add(siteName);
            }
        }
    }
}
