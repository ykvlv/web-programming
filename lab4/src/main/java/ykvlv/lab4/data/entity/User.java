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
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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