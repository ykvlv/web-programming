package beans;

import hit.Hit;
import hit.HitChecker;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.*;
import java.io.Serializable;

@Named
@ApplicationScoped
public class HitBean implements Serializable {
    @Inject
    private HitHistory hitHistory = new HitHistory();
    private final int[] possibleX = new int[]{-4, -3, -2, -1, 0, 1, 2, 3, 4};
    private final String MIN_R = HitChecker.MIN_R, MAX_R = HitChecker.MAX_R;

    @NotNull(message = "Тыкните это поле")
    @DecimalMax(value = HitChecker.MAX_X, message = "Выберите значение от " + HitChecker.MIN_Y + " до " + HitChecker.MAX_Y)
    @DecimalMin(value = HitChecker.MIN_X, message = "Выберите значение от " + HitChecker.MIN_Y + " до " + HitChecker.MAX_Y)
    private Integer x;

    @NotNull(message = "Заполните это поле")
    @DecimalMax(value = HitChecker.MAX_Y, message = "Введите число от " + HitChecker.MIN_Y + " до " + HitChecker.MAX_Y)
    @DecimalMin(value = HitChecker.MIN_Y, message = "Введите число от " + HitChecker.MIN_Y + " до " + HitChecker.MAX_Y)
    @Digits(integer = HitChecker.MAX_DIGITS, fraction = HitChecker.MAX_DIGITS, message = "Максимум " + HitChecker.MAX_DIGITS + " символов после точки")
    private Double y;

    @NotNull(message = "Заполните это поле")
    @DecimalMax(value = HitChecker.MAX_R, message = "Введите число от " + HitChecker.MIN_R + " до " + HitChecker.MAX_R)
    @DecimalMin(value = HitChecker.MIN_R, message = "Введите число от " + HitChecker.MIN_R + " до " + HitChecker.MAX_R)
    @Digits(integer = HitChecker.MAX_DIGITS, fraction = HitChecker.MAX_DIGITS, message = "Максимум " + HitChecker.MAX_DIGITS + " символов после точки")
    private Double r;

    @NotNull()
    @DecimalMax(value = HitChecker.MAX_X)
    @DecimalMin(value = HitChecker.MIN_X)
    @Digits(integer = HitChecker.MAX_DIGITS, fraction = HitChecker.MAX_DIGITS)
    private double canvasX;

    @NotNull()
    @DecimalMax(value = HitChecker.MAX_Y)
    @DecimalMin(value = HitChecker.MIN_Y)
    @Digits(integer = HitChecker.MAX_DIGITS, fraction = HitChecker.MAX_DIGITS)
    private double canvasY;

    @NotNull()
    @DecimalMax(value = HitChecker.MAX_R)
    @DecimalMin(value = HitChecker.MIN_R)
    @Digits(integer = HitChecker.MAX_DIGITS, fraction = HitChecker.MAX_DIGITS)
    private double canvasR = 2D;

    public HitBean() { }

    public void addHit() {
        Hit hit = new Hit(x, y, r);
        hitHistory.addHit(hit);
    }

    public void addHitCanvas() {
        Hit hit = new Hit(canvasX, canvasY, canvasR);
        hitHistory.addHit(hit);
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public int[] getPossibleX() {
        return possibleX;
    }



    public double getCanvasX() {
        return canvasX;
    }

    public void setCanvasX(double canvasX) {
        this.canvasX = canvasX;
    }

    public double getCanvasY() {
        return canvasY;
    }

    public void setCanvasY(double canvasY) {
        this.canvasY = canvasY;
    }

    public double getCanvasR() {
        return canvasR;
    }

    public void setCanvasR(double canvasR) {
        this.canvasR = canvasR;
    }

    public String getMIN_R() {
        return MIN_R;
    }

    public String getMAX_R() {
        return MAX_R;
    }
}
