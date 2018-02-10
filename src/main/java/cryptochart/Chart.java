package cryptochart;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Chart extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("CryptoChart");

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Date");
        yAxis.setLabel("Price");

        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle("BTC/USD");

        XYChart.Series coinValues = new XYChart.Series();


        coinValues.getData().add(new XYChart.Data(1, 23));
        coinValues.getData().add(new XYChart.Data(2, 14));
        coinValues.getData().add(new XYChart.Data(3, 15));
        coinValues.getData().add(new XYChart.Data(4, 24));
        coinValues.getData().add(new XYChart.Data(5, 34));
        coinValues.getData().add(new XYChart.Data(6, 36));
        coinValues.getData().add(new XYChart.Data(7, 22));
        coinValues.getData().add(new XYChart.Data(8, 45));
        coinValues.getData().add(new XYChart.Data(9, 43));
        coinValues.getData().add(new XYChart.Data(10, 17));
        coinValues.getData().add(new XYChart.Data(11, 29));
        coinValues.getData().add(new XYChart.Data(12, 25));

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

        for (Long t : time) {
            System.out.print(t + " ");
        }

        System.out.println();
        for (Double o : open) {
            System.out.print(o + " ");
        }
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