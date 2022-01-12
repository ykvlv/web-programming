package ykvlv.lab4.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ykvlv.lab4.data.role.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor @Getter @Setter
@Table(name = "lab4_users")
public class User {
    //TODO валидацию по аннотациям

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private boolean active;
    @CollectionTable(name = "lab4_roles")
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    public User(String username, String password, boolean active, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }
}