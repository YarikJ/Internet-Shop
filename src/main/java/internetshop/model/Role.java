package internetshop.model;

import java.util.Objects;

public class Role {
    private final Long id;
    private RoleName roleName;
    private static Long idProducer = 0L;

    public Role() {
        this.id = idProducer++;
    }

    public Role(RoleName rolename) {
        this();
        this.roleName = rolename;
    }

    public Long getId() {
        return id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public static Role of(String roleName) {
        return new Role(RoleName.valueOf(roleName));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return roleName == role.roleName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName);
    }

    public enum RoleName {
        USER, ADMIN;
    }
}
