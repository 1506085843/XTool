package com.xtool.manager.gui.sidemenu;

import com.jfoenix.controls.JFXListView;
import com.xtool.manager.gui.uicomponents.*;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

import javax.annotation.PostConstruct;
import java.util.Objects;

@ViewController(value = "/fxml/SideMenu.fxml", title = "Material Design Example")
public class SideMenuController {

    @FXMLViewFlowContext
    private ViewFlowContext context;
//    @FXML
//    @ActionTrigger("buttons")
//    private Label button;
//    @FXML
//    @ActionTrigger("checkbox")
//    private Label checkbox;
//    @FXML
//    @ActionTrigger("combobox")
//    private Label combobox;
//    @FXML
//    @ActionTrigger("dialogs")
//    private Label dialogs;
//    @FXML
//    @ActionTrigger("icons")
//    private Label icons;
//    @FXML
//    @ActionTrigger("listview")
//    private Label listview;
//    @FXML
//    @ActionTrigger("treetableview")
//    private Label treetableview;
//    @FXML
//    @ActionTrigger("progressbar")
//    private Label progressbar;
//    @FXML
//    @ActionTrigger("radiobutton")
//    private Label radiobutton;
//    @FXML
//    @ActionTrigger("slider")
//    private Label slider;
//    @FXML
//    @ActionTrigger("spinner")
//    private Label spinner;
//    @FXML
//    @ActionTrigger("textfield")
//    private Label textfield;
//    @FXML
//    @ActionTrigger("togglebutton")
//    private Label togglebutton;
//    @FXML
//    @ActionTrigger("popup")
//    private Label popup;
//    @FXML
//    @ActionTrigger("svgLoader")
//    private Label svgLoader;
//    @FXML
//    @ActionTrigger("pickers")
//    private Label pickers;
//    @FXML
//    @ActionTrigger("masonry")
//    private Label masonry;
//    @FXML
//    @ActionTrigger("scrollpane")
//    private Label scrollpane;
//    @FXML
//    @ActionTrigger("chipview")
//    private Label chipview;
//    @FXML
//    @ActionTrigger("nodeslist")
//    private Label nodesList;
//    @FXML
//    @ActionTrigger("highlighter")
//    private Label highlighter;
    @FXML
    @ActionTrigger("urlEncode")
    private Label urlEncode;
    @FXML
    @ActionTrigger("base64Encode")
    private Label base64Encode;
    @FXML
    @ActionTrigger("aesEncode")
    private Label aesEncode;
    @FXML
    @ActionTrigger("jsonFormat")
    private Label jsonFormat;
    @FXML
    @ActionTrigger("searchWord")
    private Label searchWord;
    @FXML
    @ActionTrigger("lotteryTicket")
    private Label lotteryTicket;
    @FXML
    @ActionTrigger("timeStamp")
    private Label timeStamp;
    @FXML
    @ActionTrigger("modifyDym")
    private Label modifyDym;
    @FXML
    @ActionTrigger("dateCalculation")
    private Label dateCalculation;
    @FXML
    @ActionTrigger("fileDifferent")
    private Label fileDifferent;
    @FXML
    @ActionTrigger("ocr")
    private Label ocr;

    @FXML
    private JFXListView<Label> sideList;

    /**
     * 加载时初始化 fxml
     */
    @PostConstruct
    public void init() {
        Objects.requireNonNull(context, "context");
        FlowHandler contentFlowHandler = (FlowHandler) context.getRegisteredObject("ContentFlowHandler");
        sideList.propagateMouseEventsToParent();
        sideList.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            new Thread(()->{
                Platform.runLater(()->{
                    if (newVal != null) {
                        try {
                            contentFlowHandler.handle(newVal.getId());
                        } catch (VetoException exc) {
                            exc.printStackTrace();
                        } catch (FlowException exc) {
                            exc.printStackTrace();
                        }
                    }
                });
            }).start();
        });
        Flow contentFlow = (Flow) context.getRegisteredObject("ContentFlow");
//        bindNodeToController(button, ButtonController.class, contentFlow );
//        bindNodeToController(checkbox, CheckboxController.class, contentFlow );
//        bindNodeToController(combobox, ComboBoxController.class, contentFlow );
//        bindNodeToController(dialogs, DialogController.class, contentFlow );
//        bindNodeToController(icons, IconsController.class, contentFlow );
//        bindNodeToController(listview, ListViewController.class, contentFlow );
//        bindNodeToController(treetableview, TreeTableViewController.class, contentFlow );
//        bindNodeToController(progressbar, ProgressBarController.class, contentFlow );
//        bindNodeToController(radiobutton, RadioButtonController.class, contentFlow );
//        bindNodeToController(slider, SliderController.class, contentFlow );
//        bindNodeToController(spinner, SpinnerController.class, contentFlow );
//        bindNodeToController(textfield, TextFieldController.class, contentFlow );
//        bindNodeToController(highlighter, HighlighterController.class, contentFlow );
//        bindNodeToController(chipview, ChipViewController.class, contentFlow );
//        bindNodeToController(togglebutton, ToggleButtonController.class, contentFlow );
//        bindNodeToController(popup, PopupController.class, contentFlow );
//        bindNodeToController(svgLoader, SVGLoaderController.class, contentFlow );
//        bindNodeToController(pickers, PickersController.class, contentFlow );
//        bindNodeToController(masonry, MasonryPaneController.class, contentFlow );
//        bindNodeToController(scrollpane, ScrollPaneController.class, contentFlow );
//        bindNodeToController(nodesList, NodesListController.class, contentFlow );
        bindNodeToController(urlEncode, UrlEncodeController.class, contentFlow );
        bindNodeToController(base64Encode, Base64EncodeController.class, contentFlow );
        bindNodeToController(aesEncode, AesEncodeController.class, contentFlow );
        bindNodeToController(jsonFormat, JsonFormateController.class, contentFlow );
        bindNodeToController(searchWord, SearchWordController.class, contentFlow );
        bindNodeToController(lotteryTicket, LotteryTicketController.class, contentFlow );
        bindNodeToController(timeStamp, TimeStampController.class, contentFlow );
//        bindNodeToController(modifyDym, ModifyDymController.class, contentFlow );
        bindNodeToController(dateCalculation, DateCalculationController.class, contentFlow );
        bindNodeToController(fileDifferent, FileDifferentController.class, contentFlow );
        bindNodeToController(ocr, OcrController.class, contentFlow );
    }

    private void bindNodeToController(Node node, Class<?> controllerClass, Flow flow) {
        flow.withGlobalLink(node.getId(), controllerClass);
    }

}
