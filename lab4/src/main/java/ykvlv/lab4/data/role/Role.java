package ykvlv.lab4.data.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor @Getter
public enum Role implements GrantedAuthority {
    ROLE_USER(Arrays.asList(
            Operation.OP_READ,
            Operation.OP_CREATE,
            Operation.OP_DELETE_OWN)),
    ROLE_ADMIN(Arrays.asList(
            Operation.OP_READ,
            Operation.OP_CREATE,
            Operation.OP_DELETE_OWN,
            Operation.OP_DELETE_ANY));

    private final List<Operation> allowedOperations;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
