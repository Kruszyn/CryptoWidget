package cryptochart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;


/*
https://github.com/FasterXML/jackson-annotations/wiki/Jackson-Annotations
*/


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = DataDeserializer.class)
public class Data {

    private List<Long> time;
    private List<Double> open;

    public Data(List<Long> time, List<Double> open) {
        this.time = time;
        this.open = open;
    }

    public List<Long> getTime() {
        return time;
    }

    public void setTime(List<Long> time) {
        this.time = time;
    }

    public List<Double> getOpen() {
        return open;
    }

    public void setOpen(List<Double> open) {
        this.open = open;
    }
}
