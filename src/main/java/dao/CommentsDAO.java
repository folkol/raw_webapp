package dao;

import model.Comment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement("INSERT INTO comments (alias, message) VALUES (?, ?)")) {
            statement.setString(1, comment.getAlias());
            statement.setString(2, comment.getMessage());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Comment> list() {
        List<Comment> comments = new ArrayList<Comment>();
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, alias, message from comments ORDER BY id DESC")) {
            while (resultSet.next()) {
                comments.add(new Comment(resultSet.getString("alias"), resultSet.getString("message")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return comments;
    }
}