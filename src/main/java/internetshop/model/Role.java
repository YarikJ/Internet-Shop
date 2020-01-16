package internetshop.model;

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

    public enum RoleName {
        USER, ADMIN;
    }
}
