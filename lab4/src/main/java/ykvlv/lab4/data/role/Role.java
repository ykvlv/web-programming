package ykvlv.lab4.data.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor @Getter
public enum Role implements GrantedAuthority {
    ROLE_USER(Arrays.asList(
            Operation.OP_CREATE,
            Operation.OP_READ)),
    ROLE_ADMIN(Arrays.asList(
            Operation.OP_CREATE,
            Operation.OP_READ,
            Operation.OP_UPDATE,
            Operation.OP_DELETE));

    private final List<Operation> allowedOperations;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
