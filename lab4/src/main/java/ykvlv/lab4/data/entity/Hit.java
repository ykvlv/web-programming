package ykvlv.lab4.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor @Getter @Setter
@Table(name = "lab4_hits")
public class Hit {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double x;
    private double y;
    private double r;
    private Timestamp requestTime;
    private double executionTime;
    private boolean isHit;
}
