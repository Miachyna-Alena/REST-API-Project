package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostModel {
    private int userId;
    private int id;
    private String title;
    private String body;

    public PostModel(int userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }
}
