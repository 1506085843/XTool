package com.xtool.manager.gui.uicomponents;

import com.jfoenix.controls.JFXButton;
import com.xtool.manager.utils.SearchWordUtils;
import io.datafx.controller.ViewController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ViewController(value = "/fxml/ui/Ocr.fxml", title = "OCR图片识字")
public class OcrController {
    @FXML
    private JFXButton fileButton;
    @FXML
    private JFXButton identify;
    @FXML
    private JFXButton copy;
    @FXML
    private JFXButton clear;
    @FXML
    private TextArea borderArea;
    @FXML
    private ImageView image;
    @FXML
    private TextArea resultArea;
    @FXML
    private Slider Hscroll;
    @FXML
    private Label hint;
    @FXML
    private Slider zoomLvl;
    @FXML
    private Label value;
    @FXML
    private Slider Vscroll;

    private static double size = 1;
    private static double count = 1.0;
    private String imagePath = "";//图像路径
    private static int width = 0;
    static int height = 0;
    static double ratio = 0;
    private Image source = null;
    private static double offSetX, offSetY, zoomlvl;
    private static double initx;
    private static double inity;

    @PostConstruct
    public void init() {
        //选择文件按钮
        fileButton.setOnAction(action -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择图像文件");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                //获取图像路径
                imagePath = file.getAbsolutePath();
                System.out.println(imagePath);
                FileInputStream input = null;
                try {
                    input = new FileInputStream(imagePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //获取图像为Image对象
                source = new Image(input);
                //获取长宽比
                getWidthHeight();
                //设置图像的长宽和设置到ImageView对象
                image.setImage(source);
                image.setPreserveRatio(false);
                image.setFitWidth(width);
                image.setFitHeight(height);
                height = (int) source.getHeight();
                width = (int) source.getWidth();

                offSetX = width / 2;
                offSetY = height / 2;
                //设置顶部滚动条
                Hscroll.setMax(width);
                Hscroll.setMaxWidth(image.getFitWidth());
                Hscroll.setMinWidth(image.getFitWidth());
                //设置右侧滚动条
                Vscroll.setMax(height);
                Vscroll.setMaxHeight(image.getFitHeight());
                Vscroll.setMinHeight(image.getFitHeight());
                BorderPane.setAlignment(Hscroll, Pos.CENTER);
                BorderPane.setAlignment(Vscroll, Pos.CENTER_LEFT);
                //顶部滚动条监听
                Hscroll.valueProperty().addListener(e -> {
                    offSetX = Hscroll.getValue();
                    zoomlvl = zoomLvl.getValue();
                    double newValue = (double) ((int) (zoomlvl * 10)) / 10;
                    value.setText(newValue + "");
                    if (offSetX < (width / newValue) / 2) {
                        offSetX = (width / newValue) / 2;
                    }
                    if (offSetX > width - ((width / newValue) / 2)) {
                        offSetX = width - ((width / newValue) / 2);
                    }

                    image.setViewport(new Rectangle2D(offSetX - ((width / newValue) / 2), offSetY - ((height / newValue) / 2), width / newValue, height / newValue));
                });
                //右侧滚动条监听
                Vscroll.valueProperty().addListener(e -> {
                    offSetY = height - Vscroll.getValue();
                    zoomlvl = zoomLvl.getValue();
                    double newValue = (double) ((int) (zoomlvl * 10)) / 10;
                    value.setText(newValue + "");
                    if (offSetY < (height / newValue) / 2) {
                        offSetY = (height / newValue) / 2;
                    }
                    if (offSetY > height - ((height / newValue) / 2)) {
                        offSetY = height - ((height / newValue) / 2);
                    }

                    image.setViewport(new Rectangle2D(offSetX - ((width / newValue) / 2), offSetY - ((height / newValue) / 2), width / newValue, height / newValue));
                });
                //底部缩放滚动条监听
                zoomLvl.valueProperty().addListener(e -> {
                    zoomlvl = zoomLvl.getValue();
                    double newValue = (double) ((int) (zoomlvl * 10)) / 10;
                    value.setText(newValue + "");
                    if (offSetX < (width / newValue) / 2) {
                        offSetX = (width / newValue) / 2;
                    }
                    if (offSetX > width - ((width / newValue) / 2)) {
                        offSetX = width - ((width / newValue) / 2);
                    }
                    if (offSetY < (height / newValue) / 2) {
                        offSetY = (height / newValue) / 2;
                    }
                    if (offSetY > height - ((height / newValue) / 2)) {
                        offSetY = height - ((height / newValue) / 2);
                    }
                    Hscroll.setValue(offSetX);
                    Vscroll.setValue(height - offSetY);
                    image.setViewport(new Rectangle2D(offSetX - ((width / newValue) / 2), offSetY - ((height / newValue) / 2), width / newValue, height / newValue));
                });
                //鼠标在图片上按压的事件
                image.setOnMousePressed(e -> {
                    initx = e.getSceneX();
                    inity = e.getSceneY();
                    image.setCursor(Cursor.CLOSED_HAND);
                });
                //鼠标在图片上松开的事件
                image.setOnMouseReleased(e -> {
                    image.setCursor(Cursor.OPEN_HAND);
                });
                //鼠标在图片上拖动的事件
                image.setOnMouseDragged(e -> {
                    Hscroll.setValue(Hscroll.getValue() + (initx - e.getSceneX()));
                    Vscroll.setValue(Vscroll.getValue() - (inity - e.getSceneY()));
                    initx = e.getSceneX();
                    inity = e.getSceneY();
                });
            }
        });

        //鼠标图片缩放控制
        image.setOnScroll(event -> {
            zoomlvl = zoomLvl.getValue();
            if (event.getDeltaY() > 0) {
                if (zoomlvl < 6) {
                    zoomLvl.setValue(zoomlvl + 0.3);
                }
            } else {
                if (zoomlvl > 1) {
                    zoomLvl.setValue(zoomlvl - 0.3);
                }
            }
        });

        //识别按钮
        identify.setOnAction(action -> {
            if (imagePath.length() == 0) {
                return;
            }
            long start = System.currentTimeMillis();
            //加载要识别的图片
            File image = new File(imagePath);
            //设置配置文件夹微视、识别语言、识别模式
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("src/main/resources/tessdata");
            tesseract.setLanguage("chi_sim");
            tesseract.setPageSegMode(1);
            //设置引擎模式
            tesseract.setOcrEngineMode(1);
            //开始识别图片中的文字
            String result = null;
            try {
                result = tesseract.doOCR(image);
            } catch (TesseractException e) {
                e.printStackTrace();
            }
            long time = System.currentTimeMillis() - start;
            System.out.println("识别结束,耗时：" + time + " 毫秒，识别结果如下：");
            System.out.println();
            System.out.println(result);
            resultArea.setText(result);
        });

        //复制按钮事件
        copy.setOnAction(action -> {
            String str = resultArea.getText();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(str);
            clipboard.setContent(content);
        });

        //清除按钮事件
        clear.setOnAction(action -> {
            source = null;
            image.setImage(null);
            imagePath = "";
            ratio = 0;
            width = 0;
            height = 0;
            resultArea.setText("");
            zoomlvl = 1;
            initx = 0;
            inity = 0;
            zoomLvl.setValue(1);
        });
    }

    //获取长宽、长宽比
    private void getWidthHeight() {
        width = (int) source.getWidth();
        height = (int) source.getHeight();
        ratio = source.getWidth() / source.getHeight();//获取长宽比
        if (500 / ratio < 500) {
            width = 500;
            height = (int) (500 / ratio);
        } else if (500 * ratio < 500) {
            height = 500;
            width = (int) (500 * ratio);
        } else {
            height = 500;
            width = 500;
        }
    }
}
