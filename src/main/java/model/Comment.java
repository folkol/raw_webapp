package model;

public class Comment {
    private String alias;
    private String message;

    public Comment(String alias, String message) {
        this.alias = alias;
        this.message = message;
    }

    public String getAlias() {
        return alias;
    }

    public String getMessage() {
        return message;
    }
}