import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

public class Runner extends Application {
    private Stage stage;

    private HBox layout;

    private VBox leftLayout;
    private VBox middleLayout;
    private VBox rightLayout;

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
    private static ArrayList<Subnet> subnets;

    private void initializeUIComponents() {
        layout = new HBox();

        leftLayout = new VBox();
        middleLayout= new VBox();
        rightLayout = new VBox();
        layout.getChildren().addAll(leftLayout, middleLayout, rightLayout);

        header = new VBox();
        middle = new VBox();
        footer = new VBox();
        leftLayout.getChildren().addAll(header, middle, footer);

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
        addNetworkButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleAddNetworkButton();
            }
        });
        runNetworkSubnettingButton = new Button("Divide into Subnets");
        runNetworkSubnettingButton.setOnAction(event -> handle(event));
        runNetworkSubnettingButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleRunNetworkSubnettingButton();
            }
        });
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
        TextField middleTextField = new TextField();
        leftTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            network.setNetworkName(newValue);
            if (newValue.toLowerCase().startsWith("wan")) {
                network.setNumberOfHosts(2);
                middleTextField.setText("2");
                middleTextField.setEditable(false);
            } else {
                middleTextField.setEditable(true);
            }
        });
        leftVBox.getChildren().addAll(leftLabel, leftTextField);

        Label middleLabel = new Label("Number of Hosts");
        middleTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                network.setNumberOfHosts(Integer.parseInt(newValue));
            } catch (IllegalArgumentException e) {
                middleTextField.setText("");
            }
        });
        middleVBox.getChildren().addAll(middleLabel, middleTextField);

        Label rightLabel = new Label("Delete Network");
        Button rightButton = new Button("Delete!");
        rightButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                middleContent.getChildren().remove(networkHBox);
                networks.remove(network);
                stage.sizeToScene();
            }
        });
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

    private void generateRightLayout() {
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        gridPane.add(new Label("   " + "Network Name"), 0, 0);
        gridPane.add(new Label("   " + "Network Address"), 1, 0);
        gridPane.add(new Label("   " + "Left Boundary"), 2, 0);
        gridPane.add(new Label("   " + "Right Boundary"), 3, 0);
        gridPane.add(new Label("   " + "Broadcast Address"), 4, 0);


        int rowNum = 1;
        for (Subnet i : subnets) {
            gridPane.add(new Label("   " + i.getNetworkName()), 0, rowNum);
            gridPane.add(new Label("   " + i.getNetworkAddressPretty()), 1, rowNum);
            gridPane.add(new Label("   " + i.getLeftBoundaryPretty()), 2, rowNum);
            gridPane.add(new Label("   " + i.getRightBoundaryPretty()), 3, rowNum);
            gridPane.add(new Label("   " + i.getBroadcastAddressPretty()), 4, rowNum);
            rowNum++;
        }

        for (int i = 0; i < subnets.size() + 1; i++) {
            RowConstraints constraints = new RowConstraints(35);
            gridPane.getRowConstraints().add(constraints);
        }

        for (int i = 0; i < 5; i++) {
            ColumnConstraints constraints = new ColumnConstraints(150);
            gridPane.getColumnConstraints().add(constraints);
        }

        rightLayout.getChildren().add(gridPane);
        stage.sizeToScene();
    }

    private void handleRunNetworkSubnettingButton() {
        middleLayout.setMinSize(20, 0);
        rightLayout.getChildren().clear();
        Collections.sort(networks);
        NetworkSubnetter networkSubnetter = new NetworkSubnetter(networkAddressTextField.getText(), networks);
        subnets = networkSubnetter.createSubnets();
        generateRightLayout();

    }

    private void handle(ActionEvent event) {
        if (event.getSource() == addNetworkButton) {
            handleAddNetworkButton();
        } else if (event.getSource() == runNetworkSubnettingButton) {
            handleRunNetworkSubnettingButton();
        }
    }
}
