package ykvlv.lab4.data.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lab4_roles")
public class Role implements GrantedAuthority {
    @Id
    private String id;
    @ManyToMany
    private List<Operation> allowedOperations = new ArrayList<>();

    @Override
    public String getAuthority() {
        return id;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Operation> getAllowedOperations() {
        return allowedOperations;
    }

    public void setAllowedOperations(List<Operation> allowedOperations) {
        this.allowedOperations = allowedOperations;
    }
}
