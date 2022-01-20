package ykvlv.lab4.data.role;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ROLE_USER(Arrays.asList(
            Operation.OP_CREATE,
            Operation.OP_READ,
            Operation.OP_DELETE_OWN)),
    ROLE_ADMIN(Arrays.asList(
            Operation.OP_CREATE,
            Operation.OP_READ,
            Operation.OP_DELETE_OWN,
            Operation.OP_DELETE_ANY));

    private final List<Operation> allowedOperations;

    Role(List<Operation> allowedOperations) {
        this.allowedOperations = allowedOperations;
    }

    public List<Operation> getAllowedOperations() {
        return allowedOperations;
    }
}
