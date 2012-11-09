package net.realizeideas.walkingme.authentication

class Role {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_ADVANCED = "ROLE_ADVANCED";
    public static final String ROLE_BASIC = "ROLE_BASIC";


    String authority

    static mapping = {
        cache true
    }

    static constraints = {
        authority blank: false, unique: true
    }
}
