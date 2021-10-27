import table.Hit;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class AreaCheckServlet extends HttpServlet {
    public static final String TIME_ATTR = "time", X_ATTR = "x", Y_ATTR = "y", R_ATTR = "r";
    public static final String HIT_ATTRIBUTE = "hit";
    private static final Hit invalidHit = new Hit(false);
    private static final int[] allowedR = {1, 2, 3, 4, 5};
    private static final int maxLengthY = 15;
    private static final int minY = -5, maxY = 3;
    private static final int minX = -4, maxX = 4;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Hit hit = invalidHit;

        try {
            LocalTime time = LocalTime.parse((String) req.getAttribute(TIME_ATTR));
            double x = Double.parseDouble((String) req.getAttribute(X_ATTR));
            double y = Double.parseDouble(((String) req.getAttribute(Y_ATTR)).replace(",", "."));
            int r = Integer.parseInt((String) req.getAttribute(R_ATTR));

            if (validate(x, y, r)) {
                hit = new Hit(true,
                        x, y, r, time,
                        Duration.between(time, LocalTime.now()).getNano() / 1000000000D,
                        checkHit(x, y, r));
            }
        } catch (ClassCastException | NullPointerException | NumberFormatException e) {
            // невалидные значения
        } finally {
            req.setAttribute(HIT_ATTRIBUTE, hit);
        }
    }

    private boolean checkHit(double x, double y, int r) {
        return checkCircle(x, y, r) || checkRectangle(x, y, r) || checkTriangle(x, y, r);
    }

    private boolean checkCircle(double x, double y, double r) {
        return x <= 0 && y <= 0 && Math.sqrt(x * x + y * y) <= r / 2;
    }

    private boolean checkTriangle(double x, double y, int r) {
        return x >= 0 && y <= 0 && y >= 2 * x - r;
    }

    private boolean checkRectangle(double x, double y, int r) {
        return x <= 0 && y >= 0 && x >= -r && y <= r;
    }

    private boolean validate(double x, double y, int r) {
        return validateR(r) && validateX(x) && validateY(y);
    }

    private boolean validateY(double y) {
        return String.valueOf(y).length() < maxLengthY && minY <= y && y <= maxY;
    }

    private boolean validateX(double x) {
        return minX <= x && x <= maxX;
    }

    private boolean validateR(int r) {
        return Arrays.stream(allowedR).anyMatch(Integer.valueOf(r)::equals);
    }

}
