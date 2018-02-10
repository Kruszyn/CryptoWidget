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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Chart extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("CryptoChart");



        final LineChart<String, Number> lineChart = new LineChart<String, Number>(new CategoryAxis(), new NumberAxis());

        lineChart.setTitle("BTC/USD");

        XYChart.Series<String,Number> coinValues = new XYChart.Series();


        Data data = null;
        try {
            data = DataProcessor.processData(DataProcessor.getRequest());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Long> time = data.getTime();
        List<Double> open = data.getOpen();



        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        for (Double price : open) {
            date.setTime(date.getTime() * 11111);
            coinValues.getData().add(new XYChart.Data(sdf.format(date), price));
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