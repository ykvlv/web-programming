import table.Hit;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class AreaCheckServlet extends HttpServlet {
    private static final int[] allowedR = {1, 2, 3, 4, 5};
    private static final int[] possibleX = {-4, -3, -2, -1, 0, 1, 2, 3, 4};
    private static final int maxLengthY = 15;
    private static final int minY = -5;
    private static final int maxY = 3;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            LocalTime time = LocalTime.parse((String) req.getAttribute("time"));
            double x = Double.parseDouble((String) req.getAttribute("x"));
            double y = Double.parseDouble(((String) req.getAttribute("y")).replace(",", "."));
            int r = Integer.parseInt((String) req.getAttribute("r"));

            if (validate(x, y, r)) {
                setHitAttribute(req, new Hit(true,
                        x, y, r, time,
                        Duration.between(time, LocalTime.now()).getNano()/1000000000D,
                        checkHit(x, y, r)));
            } else {
                throw new IllegalArgumentException("invalid (x, y, r)");
            }
        } catch (ClassCastException | NullPointerException | NumberFormatException e) {
            setHitAttribute(req, new Hit(false));
        }
    }

    private void setHitAttribute(HttpServletRequest req, Hit hit) {
        req.setAttribute("hit", hit);
    }

    private boolean checkHit(double x, double y, int r) {
        return checkCircle(x, y, r) || checkRectangle(x, y, r) || checkTriangle(x, y, r);
    }

    private boolean checkCircle(double x, double y, double r) {
        return x <= 0 && y <= 0 && Math.sqrt(x * x + y * y) <= r / 2;
    }

    private boolean checkTriangle(double x, double y, int r) {
//переделать...
        return x >= 0 && y <= 0;
    }

    private boolean checkRectangle(double x, double y, int r) {
        return x <= 0 && y >= 0 && x >= -r && y <= r;
    }

    private boolean validate(double x, double y, int r) {
        return validateR(r) && validateX(x, r) && validateY(y, r);
    }

    private boolean validateY(double y, int r) {
        return String.valueOf(y).length() < maxLengthY && ((minY <= y && y <= maxY) || Math.abs(y) <= r);
    }

    private boolean validateX(double x, int r) {
        return Math.abs(x) <= r || Arrays.stream(possibleX).anyMatch(Integer.valueOf((int) x)::equals);
    }

    private boolean validateR(int r) {
        return Arrays.stream(allowedR).anyMatch(Integer.valueOf(r)::equals);
    }

}
