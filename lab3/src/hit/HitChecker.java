package hit;

import java.util.Random;

public final class HitChecker {
    public static final String MIN_X = "-4", MAX_X = "4";
    public static final String MIN_Y = "-3", MAX_Y = "5";
    public static final String MIN_R = "2", MAX_R = "5";
    public static final int MAX_DIGITS = 10;

    public static boolean check(double x, double y, double r) {
        // хаХ[Аf[ха решил поспать чтобы время выполнения скрипта было разное.....
        try {
            Thread.sleep(new Random().nextInt(50));
        } catch (InterruptedException ignored) { }

        return checkCircle(x, y, r) || checkRectangle(x, y, r) || checkTriangle(x, y, r);
    }

    private static boolean checkCircle(double x, double y, double r) {
        return x >= 0 && y >= 0 && Math.sqrt(x * x + y * y) <= r / 2;
    }

    private static boolean checkTriangle(double x, double y, double r) {
        return x >= 0 && y <= 0 && y >= x / 2 + -r / 2;
    }

    private static boolean checkRectangle(double x, double y, double r) {
        return x <= 0 && y <= 0 && x >= -r && y >= -r;
    }

}
