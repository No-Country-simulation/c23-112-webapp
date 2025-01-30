package Back_end.authentication.enums;

public enum RoleList {
    ROLE_USER_0, ROLE_ADMIN_1;

    public static RoleList getById(int id) {
        RoleList[] roles = RoleList.values();
        if (id >= 0 && id < roles.length) {
            return roles[id];
        }
        throw new IllegalArgumentException("ID de rol no válido: " + id);
    }
}
