package cryptochart;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sun.java2d.pipe.SpanShapeRenderer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Chart extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("CryptoChart");


        Data data = null;
        try {
            data = DataProcessor.processData(DataProcessor.getRequest());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Long> time = data.getTime();
        List<Double> open = data.getOpen();

        Double openMax = Collections.max(open);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(Collections.max(open) * 1.01);
        yAxis.setLowerBound(Collections.min(open) * 0.99);
        yAxis.setTickUnit(50);

        final LineChart<String, Number> lineChart = new LineChart<String, Number>(new CategoryAxis(), yAxis);

        lineChart.setTitle("BTC/USD");

        XYChart.Series<String,Number> coinValues = new XYChart.Series();


        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        int hourInMs = 3600000;
        date.setTime(date.getTime()-hourInMs);
        for (Double price : open) {
            coinValues.getData().add(new XYChart.Data(sdf.format(date), price));
            date.setTime(date.getTime() + 60000);
        }




        Scene scene = new Scene(lineChart, 400, 400, Color.TRANSPARENT);
        lineChart.getData().add(coinValues);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {

        Data data = null;
        try {
            data = DataProcessor.processData(DataProcessor.getRequest());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Long> time = data.getTime();
        List<Double> open = data.getOpen();
        int i = 0;
        for (Long t : time) {
            System.out.print(t + " ");
            i++;
        }

        System.out.println();
        for (Double o : open) {
            System.out.print(o + " ");
        }
        System.out.println();
        System.out.println(i);
        /*List<Double> opens = prices.getOpen();
        for( Double p : opens){
            System.out.println(p);

        }*/
        /*
        * 1.Wyslanie GET request na wskazany URL DONE
        * 2.Przetowrzenie JSONa na odp format DONE
        * 3.Przekazanie danych do wykresu
        *
        * */
        launch(args);
    }

}