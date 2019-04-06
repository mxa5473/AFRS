/*
    Creates the GUI for the program
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class GUI extends Application implements EventHandler<ActionEvent> {

    /*
        Creates some nodes for the Scene
     */
    private Button button;
    private TextField text;
    private ComboBox<String> comboBox1;
    private ComboBox<String> comboBox2;
    private Boolean completeText = false;
    private Boolean completeCombo1 = false;
    private Boolean completeCombo2 = false;
    private TextArea output;

    /*
        Main method that launches the GUI
     */
    public static void main(String[] args) {
        launch(args);
    }

    /*
        Start method that creates the stage, scene, creates the nodes, and then adds all the nodes to the scene
        inside containers
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        /*
            Creates the stage
         */
        primaryStage.setTitle("AFRS");
        primaryStage.setResizable(true);

        /*
            Creates a label
         */
        Label label = new Label("Request");

        /*
            Creates a button
         */
        button = new Button("Enter");
        button.setDisable(true);
        button.relocate(100, 0);
        button.setOnAction(this);

        /*
            Creates a text field that can be edited, must press enter in order to hit event
         */
        text = new TextField("Remove this and press enter when done");
        text.setMaxWidth(200);
        text.setOnAction(this);

        /*
            Creates a combo box with default values
         */
        comboBox1 = new ComboBox<>();
        comboBox1.getItems().addAll("This", "is", "a", "test");
        comboBox1.setOnAction(this);

        comboBox2 = new ComboBox<>();
        comboBox2.getItems().addAll("This", "is", "a", "test");
        comboBox2.setOnAction(this);

        /*
            Creates the ouput text field
         */
        output = new TextArea();
        output.setEditable(false);
        output.setPrefSize(200, 400);
        output.setMaxSize(500,700);

        /*
            Creates an anchor pane to store all the boxes
         */
        AnchorPane anchorPane = new AnchorPane();

        /*
            Creates a vertical box
         */
        VBox list = new VBox(5);

        /*
            Creates a horizontal box to store the text field, combo boxes, and the button
         */
        HBox content = new HBox(5);
        content.getChildren().addAll(text, comboBox1, comboBox2, button);

        /*
            Adds the label and horizontal box
         */
        list.getChildren().addAll(label, content);

        /*
            Creates a VBox that contains the output text area
         */
        VBox ouputBox = new VBox();
        ouputBox.getChildren().addAll(output);

        /*
            Adds the vertical and horizontal boxes to the pane and then sets their location
         */
        anchorPane.getChildren().addAll(list, ouputBox);
        AnchorPane.setLeftAnchor(list, 8.0);
        AnchorPane.setRightAnchor(ouputBox, 10.0);

        /*
            Sets the scene with a default size and adds it to the stage
         */
        Scene scene = new Scene(anchorPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*
        Event handler class
     */
    @Override
    public void handle(ActionEvent event) {
        /*
            If the Button is pressed output the data, reset the booleans, and add the text to the output box
         */
        if (event.getSource() == button) {
            System.out.println("Action " + text.getText() + " " + comboBox1.getValue() + " " + comboBox2.getValue());
            button.setDisable(true);
            completeCombo1 = false;
            completeCombo2 = false;
            completeText = false;
            output.appendText("> " + "Action " + text.getText() + " " + comboBox1.getValue() + " " + comboBox2.getValue() + "\n");
        }
        /*
            If the text field is changed the completeText boolean to true and will enable the button if
            the other variables are true
         */
        else if (event.getSource() == text) {
            completeText = true;
            if (completeCombo1 && completeCombo2) {
                button.setDisable(false);
            }
        }
        /*
            If the first combo box is changed the completeCombo1 boolean to true and will enable the button if
            the other variables are true
         */
        else if (event.getSource() == comboBox1) {
            completeCombo1 = true;
            if (completeText && completeCombo2) {
                button.setDisable(false);
            }
        }
        /*
            If the second combo box is changed the completeCombo2 boolean to true and will enable the button if
            the other variables are true
         */
        else if (event.getSource() == comboBox2) {
            completeCombo2 = true;
            if (completeText && completeCombo1) {
                button.setDisable(false);
            }
        }
    }
}
