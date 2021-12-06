package hit;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Hit implements Comparable<Hit> {
    private final String id;
    private final double x;
    private final double y;
    private final double r;
    private final LocalDateTime currentDateTime;
    private final String formattedTime;
    private final Double executionTime;
    private final boolean result;

    public Hit(double x, double y, double r) {
        this.id = UUID.randomUUID().toString();
        this.x = x;
        this.y = y;
        this.r = r;
        this.currentDateTime = LocalDateTime.now();
        this.formattedTime = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.result = HitChecker.check(x, y, r);
        this.executionTime = Duration.between(currentDateTime, LocalDateTime.now()).getNano() / 1000000000D;
    }

    public Hit(String id, double x, double y, double r, LocalDateTime currentDateTime, Double executionTime, boolean result) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.r = r;
        this.currentDateTime = currentDateTime;
        this.formattedTime = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.executionTime = executionTime;
        this.result = result;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public LocalDateTime getCurrentDateTime() {
        return currentDateTime;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public Double getExecutionTime() {
        return executionTime;
    }

    public boolean isResult() {
        return result;
    }

    public String getId() {
        return id;
    }

    @Override
    public int compareTo(Hit o) {
        if (this.getCurrentDateTime().isBefore(o.currentDateTime)) {
            return -1;
        } else if (this.getCurrentDateTime().isAfter(o.currentDateTime)) {
            return 1;
        } else {
            return 0;
        }
    }
}
