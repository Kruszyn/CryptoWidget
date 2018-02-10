package cryptochart;

import com.fasterxml.jackson.databind.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.UNWRAP_ROOT_VALUE;
import static com.fasterxml.jackson.databind.DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY;

/**
 * Created by ibm on 2018-02-07.
 */
public class DataProcessor {


    /* API from https://min-api.cryptocompare.com/ */

    final static String urlBitcoinPriceDay = "https://min-api.cryptocompare.com/data/histominute?fsym=BTC&tsym=USD&limit=59";
    final static String urlBitcoinPriceLastStamp = "https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD";


    public static String getRequest(){
        String requestUrl = urlBitcoinPriceDay;
        StringBuilder sb = new StringBuilder();
        URLConnection connection = null;
        InputStreamReader in = null;
        try{
            URL url = new URL(requestUrl);
            connection = url.openConnection();
            if(connection != null) connection.setReadTimeout(60*1000);
            if(connection != null && connection.getInputStream() != null){
                in = new InputStreamReader(connection.getInputStream(), Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if(bufferedReader != null){
                    int cp;
                    while((cp = bufferedReader.read()) != -1){
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }

        } catch ( Exception e) {
            throw new RuntimeException("Exception while call URL:" + requestUrl);
        }

        return sb.toString();
    }


    public static Data processData(String JSONbody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //konieczne, aby nie było błędu przy pomijaniu niepotrzebnych pól JSONa

        final ObjectReader r = objectMapper.reader(Data.class);

        Data data = r
           //     .with(UNWRAP_ROOT_VALUE)
          //      .with(USE_JAVA_ARRAY_FOR_JSON_ARRAY)
                .readValue(JSONbody);



        return data;
    }
}
