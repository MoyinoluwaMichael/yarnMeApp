package data.repositories.commentRepo;

import data.models.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentRepositoryImpl implements CommentRepository{

    private ArrayList <Comment> comments = new ArrayList<>();
    private long count;
    @Override
    public Comment save(Comment comment) {
        boolean commentHasNotBeenSaved = comment.getId() == 0;
        if (commentHasNotBeenSaved) saveNewComment(comment);
        return comment;
    }

    private void saveNewComment(Comment comment) {
        comment.setId(generateId());
        comments.add(comment);
        count++;
    }

    private long generateId() {
        return count+1;
    }

    @Override
    public Comment find(int userId, int articleId, int commentId) {
        for (Comment comment: comments) {
            if (userId == comment.getUserId() && articleId == comment.getArticleId() && commentId == comment.getId()) {
                return comment;
            }
        }
        return null;
    }

    @Override
    public long count(int articleId) {
        int commentCount = 0;
        for (var comment: comments) if (comment.getArticleId() == articleId) commentCount++;
        return commentCount;
    }

    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public void delete(Comment comment) {
        for (var each: comments) if (comment.equals(each)){
            comments.remove(comment);
            count--;
            break;
        }
    }

    @Override
    public void delete(int user1Id, int id, int i) {
        for (Comment comment: comments) if (comment.getId() == id) {
            comments.remove(comment);
            count--;
            break;
        }

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public long countAll() {
        return count;
    }
}
