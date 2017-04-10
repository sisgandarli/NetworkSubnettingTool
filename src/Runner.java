import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Runner extends Application {
    private Stage stage;

    private VBox layout;
    private VBox header;
    private VBox middle;
    private VBox footer;

    private HBox headerContent;
    private VBox middleContent;
    private HBox footerContent;

    private Label networkAddressLabel;
    private TextField networkAddressTextField;

    private Button addNetworkButton;
    private Button runNetworkSubnettingButton;

    private static ArrayList<Network> networks = new ArrayList<>();

    private void initializeUIComponents() {
        layout = new VBox();

        header = new VBox();
        middle = new VBox();
        footer = new VBox();
        layout.getChildren().addAll(header, middle, footer);

        headerContent = new HBox();
        headerContent.setAlignment(Pos.CENTER_RIGHT);
        header.getChildren().add(headerContent);

        middleContent = new VBox();
        middleContent.setAlignment(Pos.CENTER_RIGHT);
        middle.getChildren().add(middleContent);

        footerContent = new HBox();
        footerContent.setAlignment(Pos.CENTER_RIGHT);
        footer.getChildren().add(footerContent);

        networkAddressLabel = new Label("Enter Network Address");
        networkAddressTextField = new TextField();
        headerContent.getChildren().addAll(networkAddressLabel, networkAddressTextField);

        addNetworkButton = new Button("Add Network");
        addNetworkButton.setOnAction(event -> handle(event));
        runNetworkSubnettingButton = new Button("Divide into Networks");
        runNetworkSubnettingButton.setOnAction(event -> handle(event));
        footerContent.getChildren().addAll(addNetworkButton, runNetworkSubnettingButton);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        initializeUIComponents();

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Network Subnetting Tool");
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private void handleAddNetworkButton() {
        Network network = new Network();
        networks.add(network);

        HBox networkHBox = new HBox();

        VBox leftVBox = new VBox();
        VBox middleVBox = new VBox();
        VBox rightVBox = new VBox();
        leftVBox.setAlignment(Pos.CENTER);
        middleVBox.setAlignment(Pos.CENTER);
        rightVBox.setAlignment(Pos.CENTER);
        networkHBox.getChildren().addAll(leftVBox, middleVBox, rightVBox);

        Label leftLabel = new Label("Network Name");
        TextField leftTextField = new TextField();
        leftTextField.textProperty().addListener((observable, oldValue, newValue) -> network.setNetworkName(newValue));
        leftVBox.getChildren().addAll(leftLabel, leftTextField);

        Label middleLabel = new Label("Number of Hosts");
        TextField middleTextField = new TextField();
        middleTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                network.setNumberOfHosts(Integer.parseInt(newValue));
            } catch (NumberFormatException e) {
                middleTextField.setText("");
            }
        });
        middleVBox.getChildren().addAll(middleLabel, middleTextField);

        Label rightLabel = new Label("Delete Network");
        Button rightButton = new Button("Delete!");
        rightButton.setOnAction(event -> {
            middleContent.getChildren().remove(networkHBox);
            networks.remove(network);
            stage.sizeToScene();
        });
        rightVBox.getChildren().addAll(rightLabel, rightButton);

        middleContent.getChildren().add(networkHBox);
        stage.sizeToScene();

        rightButton.setMinWidth(rightLabel.getWidth());
    }


    private void handle(ActionEvent event) {
        if (event.getSource() == addNetworkButton) {
            handleAddNetworkButton();
        } else if (event.getSource() == runNetworkSubnettingButton) {
            for (Network i : networks) {
                System.out.println(i);
            }
        }
    }
}
