package ykvlv.lab3.beans;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Named
@ApplicationScoped
public class ClockBean implements Serializable {
    private int interval = 8;
    private String convertedTime;

    public ClockBean() { }

    private void updateConvertedTime() {
        LocalTime time = LocalTime.now();
        convertedTime = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getConvertedTime() {
        updateConvertedTime();
        return convertedTime;
    }

    public void setConvertedTime(String convertedTime) {
        this.convertedTime = convertedTime;
    }
}
