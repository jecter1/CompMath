package eqsolver;

import eqsolver.model.CubicEquation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class View {
    private final Stage stage;

    TextField textFieldA;
    TextField textFieldB;
    TextField textFieldC;
    TextField textFieldD;
    TextField textFieldE;
    TextField textFieldEps;

    CubicEquation cubicEquation;
    TextArea answerArea;

    Font font = new Font(20);
    Pattern p = Pattern.compile("(\\d+\\.?\\d*)?");;

    public View(Stage stage) {
        this.stage = stage;
        stage.setResizable(false);
        stage.setTitle("Equation solver");
        StackPane stackPane = new StackPane();

        // Background
        Image image = new Image(BACKGROUND_FILENAME);
        ImageView background = new ImageView(image);
        stackPane.getChildren().add(background);

        // Box for all Boxes
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.TOP_CENTER);

        // A, B, C, D, E
        HBox equationBox = new HBox();
        equationBox.setAlignment(Pos.CENTER);
        fillEquationBox(equationBox);
        stackPane.getChildren().add(equationBox);

        // Epsilon
        HBox epsBox = new HBox();
        epsBox.setAlignment(Pos.CENTER);
        Label labelEps = new Label(LABEL_EPS_TEXT);
        labelEps.setTextFill(Color.WHITE);
        labelEps.setFont(font);
        epsBox.getChildren().add(labelEps);
        textFieldEps = new TextField();
        textFieldEps.setPrefWidth(EPS_TEXT_FIELD_RATIO_X * WINDOW_WIDTH);
        textFieldEps.setAlignment(Pos.CENTER);
        textFieldEps.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) textFieldEps.setText(oldValue);
        });
        epsBox.getChildren().add(textFieldEps);

        // Button "Solve"
        Button solveButton = new Button(BUTTON_TEXT);
        solveButton.setAlignment(Pos.CENTER);
        solveButton.setFont(font);
        solveButton.setOnKeyPressed(ae -> {

        });

        vBox.getChildren().add(equationBox);
        vBox.getChildren().add(epsBox);
        vBox.getChildren().add(solveButton);

        stackPane.getChildren().add(vBox);

        // Scene
        Scene scene = new Scene(stackPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
    }

    private void fillEquationBox(HBox equationBox) {
        textFieldA = new TextField();
        textFieldA.setPrefWidth(TEXT_FIELD_RATIO_X * WINDOW_WIDTH);
        textFieldA.setAlignment(Pos.CENTER);
        textFieldA.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) textFieldA.setText(oldValue);
        });
        equationBox.getChildren().add(textFieldA);
        Label labelA = new Label(LABEL_A_TEXT);
        labelA.setTextFill(Color.WHITE);
        labelA.setFont(font);
        equationBox.getChildren().add(labelA);

        textFieldB = new TextField();
        textFieldB.setPrefWidth(TEXT_FIELD_RATIO_X * WINDOW_WIDTH);
        textFieldB.setAlignment(Pos.CENTER);
        textFieldB.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) textFieldA.setText(oldValue);
        });
        equationBox.getChildren().add(textFieldB);
        Label labelB = new Label(LABEL_B_TEXT);
        labelB.setTextFill(Color.WHITE);
        labelB.setFont(font);
        equationBox.getChildren().add(labelB);

        textFieldC = new TextField();
        textFieldC.setPrefWidth(TEXT_FIELD_RATIO_X * WINDOW_WIDTH);
        textFieldC.setAlignment(Pos.CENTER);
        textFieldC.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) textFieldA.setText(oldValue);
        });
        equationBox.getChildren().add(textFieldC);
        Label labelC = new Label(LABEL_C_TEXT);
        labelC.setTextFill(Color.WHITE);
        labelC.setFont(font);
        equationBox.getChildren().add(labelC);

        textFieldD = new TextField();
        textFieldD.setPrefWidth(TEXT_FIELD_RATIO_X * WINDOW_WIDTH);
        textFieldD.setAlignment(Pos.CENTER);
        textFieldD.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) textFieldA.setText(oldValue);
        });
        equationBox.getChildren().add(textFieldD);
        Label labelD = new Label(LABEL_D_TEXT);
        labelD.setTextFill(Color.WHITE);
        labelD.setFont(font);
        equationBox.getChildren().add(labelD);

        textFieldE = new TextField();
        textFieldE.setAlignment(Pos.CENTER);
        textFieldE.setPrefWidth(TEXT_FIELD_RATIO_X * WINDOW_WIDTH);
        equationBox.getChildren().add(textFieldE);
    }

    public void show() {
        stage.show();
    }

    private static final double TEXT_FIELD_RATIO_X = 0.125;
    private static final double EPS_TEXT_FIELD_RATIO_X = 0.25;

    private static final String LABEL_A_TEXT = " x³ + ";
    private static final String LABEL_B_TEXT = " x² + ";
    private static final String LABEL_C_TEXT = " x + ";
    private static final String LABEL_D_TEXT = " = ";
    private static final String LABEL_EPS_TEXT = "If A ≠ 0 then ε = ";
    private static final String BUTTON_TEXT = "SOLVE";

    private static final String BACKGROUND_FILENAME = "/background.jpg";

    private static final int WINDOW_WIDTH = 640;
    private static final int WINDOW_HEIGHT = 360;
}
