package cryptochart;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.LongNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DataDeserializer extends StdDeserializer<Data> {

    public DataDeserializer() {
        this(null);
    }

    public DataDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Data deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode dataNode = node.path("Data");

        List<Long> time = new ArrayList<Long>();
        List<Double> open = new ArrayList<Double>();

        for(JsonNode valuesNode : dataNode){
            time.add(valuesNode.path("time").asLong());
            open.add(valuesNode.path("open").asDouble());
        }

        return new Data(time, open);
    }
}