package data.repositories.commentRepo;

import data.models.Comment;

import java.util.List;

public interface CommentRepository {
    Comment save(Comment comment);
    Comment find(int userId, int articleId, int commentId);
    long count(int articleId);
    List<Comment> findAll();
    void delete(Comment comment);
    void delete(int user1Id, int articleId, int commentId);

    void deleteAll();

    long countAll();
}
