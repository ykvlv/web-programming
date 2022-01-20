package ykvlv.lab4.data.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "lab4_hits")
public class Hit {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double x;
    private Double y;
    private Double r;
    private Timestamp requestTime;
    private Boolean hit;
    private String owner;

    public Hit(Double x, Double y, Double r, Timestamp requestTime, Boolean hit, String owner) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.requestTime = requestTime;
        this.hit = hit;
        this.owner = owner;
    }

    public Hit() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
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

    public Timestamp getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Timestamp requestTime) {
        this.requestTime = requestTime;
    }

    public void setHit(Boolean hit) {
        this.hit = hit;
    }

    public Boolean getHit() {
        return hit;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
