package eqsolver;

import eqsolver.model.CubicEquation;
import eqsolver.model.IncorrectEpsilonException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.regex.Pattern;

public class Main extends Application {
    private static final int WINDOW_WIDTH = 640;
    private static final int WINDOW_HEIGHT = 480;

    TextField textFieldA = new TextField();
    TextField textFieldB = new TextField();
    TextField textFieldC = new TextField();
    TextField textFieldD = new TextField();
    TextField textFieldEps = new TextField("0.001");

    TextArea answerArea = new TextArea();

    Button solveBtn = new Button("SOLVE");

    Pattern p = Pattern.compile("-?(\\d+\\.?\\d*)?");;

    @Override
    public void start(Stage stage) {
        StackPane stackPane = new StackPane();
        ImageView imageView = new ImageView(new Image("/background.jpg"));
        stackPane.getChildren().add(imageView);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));

        Label equationLabel = new Label("Ax³ + Bx² + Cx + D = 0");
        equationLabel.setFont(new Font(16));
        equationLabel.setTextFill(Color.WHITE);

        HBox aBox = new HBox();
        aBox.setAlignment(Pos.CENTER);
        Label aLabel = new Label("A = ");
        aLabel.setTextFill(Color.WHITE);
        textFieldA.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) textFieldA.setText(oldValue);
        });
        aBox.getChildren().add(aLabel);
        aBox.getChildren().add(textFieldA);

        HBox bBox = new HBox();
        bBox.setAlignment(Pos.CENTER);
        Label bLabel = new Label("B = ");
        bLabel.setTextFill(Color.WHITE);
        textFieldB.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) textFieldB.setText(oldValue);
        });
        bBox.getChildren().add(bLabel);
        bBox.getChildren().add(textFieldB);

        HBox cBox = new HBox();
        cBox.setAlignment(Pos.CENTER);
        Label cLabel = new Label("C = ");
        cLabel.setTextFill(Color.WHITE);
        textFieldC.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) textFieldC.setText(oldValue);
        });
        cBox.getChildren().add(cLabel);
        cBox.getChildren().add(textFieldC);

        HBox dBox = new HBox();
        dBox.setAlignment(Pos.CENTER);
        Label dLabel = new Label("D = ");
        dLabel.setTextFill(Color.WHITE);
        textFieldD.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) textFieldD.setText(oldValue);
        });
        dBox.getChildren().add(dLabel);
        dBox.getChildren().add(textFieldD);

        HBox epsBox = new HBox();
        epsBox.setAlignment(Pos.CENTER);
        Label epsLabel = new Label("A ≠ 0 → ε = ");
        epsLabel.setTextFill(Color.WHITE);
        textFieldEps.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) textFieldEps.setText(oldValue);
        });
        epsBox.getChildren().add(epsLabel);
        epsBox.getChildren().add(textFieldEps);

        solveBtn.setPrefWidth(200);

        answerArea.setEditable(false);

        solveBtn.setOnAction(ae -> solve());

        vBox.getChildren().add(equationLabel);
        vBox.getChildren().add(aBox);
        vBox.getChildren().add(bBox);
        vBox.getChildren().add(cBox);
        vBox.getChildren().add(dBox);
        vBox.getChildren().add(epsBox);
        vBox.getChildren().add(solveBtn);
        vBox.getChildren().add(answerArea);

        stackPane.getChildren().add(vBox);

        Scene scene = new Scene(stackPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void solve() {
        answerArea.setText("");
        double a, b, c, d, eps;
        try {
            a = Double.parseDouble(textFieldA.getText());
            b = Double.parseDouble(textFieldB.getText());
            c = Double.parseDouble(textFieldC.getText());
            d = Double.parseDouble(textFieldD.getText());
            eps = (a != 0) ? Double.parseDouble(textFieldEps.getText()) : 0;
        } catch (NumberFormatException exc) {
            answerArea.appendText("Коэффициенты введены неверно");
            return;
        }

        try {
            CubicEquation cubicEquation = new CubicEquation(a, b, c, d, eps);
            answerArea.appendText(cubicEquation.toString() + "\n");
            answerArea.appendText("...\n");
            answerArea.appendText(cubicEquation.getAnswer());
        } catch (IncorrectEpsilonException exc) {
            answerArea.setText(exc.toString());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
