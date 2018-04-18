package cryptochart;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sun.java2d.pipe.SpanShapeRenderer;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

public class Chart extends Application {

    private static final SimpleDateFormat DF = new SimpleDateFormat("HH:mm");
    private static final int HOUR_IN_MS = 3600000;
    private static final int MIN_IN_MS = 60000;
    private Double lastHourChange;
    private List<Double> open;
    private NumberAxis yAxis;

    private XYChart.Series<String,Number> coinValues = new XYChart.Series();

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("CryptoChart");
        getData();
        setAxisY();

        final LineChart<String, Number> lineChart = new LineChart<String, Number>(new CategoryAxis(), yAxis);

        setData();
        lineChart.setTitle("BTC/USD");

        Scene scene = new Scene(lineChart, 400, 400, Color.TRANSPARENT);
        File f = new File("chart.css");
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

        lineChart.getData().add(coinValues);

        primaryStage.setScene(scene);

        primaryStage.show();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while(true) {
                    Thread.sleep(MIN_IN_MS);
                    Platform.runLater(new Runnable() {
                        public void run() {
                            updateCoinValue(getNewestCoinVal());
                        }
                    });
                }
            }

            @Override
            protected void succeeded() {
                super.succeeded();
            }
        };
        // TODO THREAD CLOSE AFTER WINDOW CLOSE
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void getData(){

        Data data = null;
        try {
            data = DataProcessor.processData(DataProcessor.getRequest());
        } catch (IOException e) {
            e.printStackTrace();
        }

        open = data.getOpen();

        evalChange();
    }

    private void setAxisY() {
        yAxis = new NumberAxis();
        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(Collections.max(open) * 1.01);
        yAxis.setLowerBound(Collections.min(open) * 0.99);
        yAxis.setTickUnit(50);
    }

    private void setData(){
        Date date = new Date();

        date.setTime(date.getTime() - HOUR_IN_MS);
        for (Double price : open) {
            coinValues.getData().add(new XYChart.Data(DF.format(date), price));
            date.setTime(date.getTime() + MIN_IN_MS);
        }

    }

    //TODO zrobić get request do API na ostatnią wartość zamiast pobierać całą ost godzinę
    private Double getNewestCoinVal() {
        Data data = null;
        try {
            data = DataProcessor.processData(DataProcessor.getRequest());
        } catch (IOException e) {
            e.printStackTrace();
        }

        open = data.getOpen();
        return open.get(open.size()-1);
    }

    private void updateCoinValue(Double newestPrice){
        Date date = new Date();
        coinValues.getData().remove(0);
        coinValues.getData().add(new XYChart.Data(DF.format(date), newestPrice));

        evalChange();
    }

    private void evalChange(){
        lastHourChange = (open.get(0) - open.get(open.size()-1))/100;
        lastHourChange = round(lastHourChange, 2);
        System.out.println(lastHourChange);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}