package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import model.Comment;

public class CommentsDAO {

    private static final String DB_USER = "";
    private static final String DB_PASS = "";
    private static final String DB_URL = "jdbc:mysql://localhost/CommentsDB";

    private Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", DB_USER);
        properties.setProperty("password", DB_PASS);
        Connection conn = DriverManager.getConnection(DB_URL, properties);

        return conn;
    }

    public void store(Comment comment) {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = getConnection();
            statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO comments (alias, message) VALUES ('" + comment.getAlias() + "', '" + comment.getMessage() + "')");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
                if(conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Comment> list() {
        List<Comment> comments = new ArrayList<Comment>();
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT id, alias, message from comments ORDER BY id DESC");
            while(resultSet.next()) {
                comments.add(new Comment(resultSet.getString("alias"), resultSet.getString("message")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(resultSet != null) {
                    resultSet.close();
                }
                if(statement != null) {
                    statement.close();
                }
                if(conn != null) {
                    conn.close();
                }
            } catch (Exception e ){}
        }

        return comments;
    }
}