package com.theevilroot.epam.lab4.gui;

import com.sun.javafx.tk.Toolkit;
import com.theevilroot.epam.lab4.math.Function;
import com.theevilroot.epam.lab4.math.FunctionType;
import com.theevilroot.epam.lab4.math.Integral;
import com.theevilroot.epam.lab4.observable.Observer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GUI extends Application implements Observer<Double> {

    private final Label functionLabel;
    private final ComboBox<FunctionType> functionMenu;

    private final Label rangeALabel;
    private final TextField rangeAField;

    private final Label  rangeBLabel;
    private final TextField rangeBField;

    private final Label deltaLabel;
    private final TextField deltaField;

    private final Label resultLabel;
    private final Label resultValueLabel;

    private final Button calculateButton;

    private final Label statusLabel;
    private final Label currentTaskLabel;

    private final VBox rootBox;
    private final VBox functionBox;
    private final HBox rangesBox;
    private final VBox rangeABox;
    private final VBox rangeBBox;
    private final VBox deltaBox;
    private final VBox resultBox;
    private final VBox calculateBox;

    private final Scene mainScene;

    private Integral integral;
    private FunctionType currentType;

    public GUI() {
        functionLabel = new Label("Select function");

        functionMenu = new ComboBox<>();
        functionMenu.getItems().addAll(FunctionType.values());

        rangeALabel = new Label("Range: from");
        rangeAField = new TextField("0");

        rangeBLabel = new Label("Range: to");
        rangeBField = new TextField("1");

        deltaLabel = new Label("Delta");
        deltaField = new TextField("1");

        resultLabel = new Label("Result: ");
        resultValueLabel = new Label("0.0");

        calculateButton = new Button("Calculate integral");

        statusLabel = new Label("Idle");
        currentTaskLabel = new Label("0.1");

        rootBox = new VBox();
        functionBox = new VBox();
        rangesBox = new HBox();
        rangeABox = new VBox();
        rangeBBox = new VBox();
        deltaBox = new VBox();
        resultBox = new VBox();
        calculateBox = new VBox();

        mainScene = new Scene(rootBox);

        initHierarchy();
        initStyles();

        initModel();
    }

    private void initModel() {
        calculateButton.setOnMouseClicked(e -> {
            if (updateFunction()) {
                setControls(true);
                integral.startCalculation();
            }
        });
    }

    private boolean updateFunction() {
        if (integral != null && integral.getLatch().getCount() > 0)
            return false;

        if (integral != null) {
            integral.getResult().unsubscribe(this);
        }

        double aValue;
        double bValue;
        double dValue;

        try {
            aValue = Double.parseDouble(rangeAField.getText());
            bValue = Double.parseDouble(rangeBField.getText());
            dValue = Double.parseDouble(deltaField.getText());
        } catch (Exception ignored) {
            statusLabel.setText("Please, enter valid range and delta values ");
            return false;
        }

        currentType = functionMenu.getValue();
        integral = new Integral(new Function(aValue, bValue, currentType.evaluator), dValue);
        integral.getResult().subscribe(this);
        return true;
    }

    private void initHierarchy() {
        functionBox.getChildren().addAll(functionLabel, functionMenu);

        rangeABox.getChildren().addAll(rangeALabel, rangeAField);
        rangeBBox.getChildren().addAll(rangeBLabel, rangeBField);

        rangesBox.getChildren().addAll(rangeABox, rangeBBox);

        deltaBox.getChildren().addAll(deltaLabel, deltaField);

        calculateBox.getChildren().addAll(calculateButton, statusLabel, currentTaskLabel);

        resultBox.getChildren().addAll(resultLabel, resultValueLabel);

        rootBox.getChildren().addAll(functionBox, rangesBox, deltaBox, resultBox, calculateBox);
    }

    private void initStyles() {
        rootBox.setAlignment(Pos.CENTER);
        functionBox.setAlignment(Pos.CENTER);
        rangesBox.setAlignment(Pos.CENTER);
        rangeABox.setAlignment(Pos.CENTER);
        rangeBBox.setAlignment(Pos.CENTER);
        resultBox.setAlignment(Pos.CENTER);
        calculateBox.setAlignment(Pos.CENTER);

        rootBox.setSpacing(10f);
        rootBox.setPadding(new Insets(8f));

        deltaBox.setFillWidth(false);
        deltaBox.setAlignment(Pos.CENTER);

        resultValueLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 2em;");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(mainScene);
        primaryStage.setOnCloseRequest(closeEvent -> {
            if (integral != null) {
                integral.getCalculatorExecutor().shutdownNow();
            }
        });
        primaryStage.show();
    }

    @Override
    public void notify(Double neValue) {
        if (!Toolkit.getToolkit().isFxUserThread()) {
            Platform.runLater(() -> updateValue(neValue));
        } else updateValue(neValue);
    }

    private void updateValue(Double newValue) {
        resultValueLabel.setText(newValue.toString());
        if (integral.getLatch() != null) {
            long count = integral.getLatch().getCount();
            if (count > 0) {
                currentTaskLabel.setText("Calculating " + currentType + " integral");
                statusLabel.setText("Operations left: " + count);
            } else {
                statusLabel.setText("Operation complete");
                currentTaskLabel.setText("");
                setControls(false);
            }
        }
    }

    private void setControls(boolean disabled) {
        functionBox.setDisable(disabled);
        rangesBox.setDisable(disabled);
        deltaBox.setDisable(disabled);
        calculateButton.setDisable(disabled);
    }
}
