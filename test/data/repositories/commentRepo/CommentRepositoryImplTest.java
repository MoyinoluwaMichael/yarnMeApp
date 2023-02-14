package data.repositories.commentRepo;


import data.models.Article;
import data.models.Comment;
import data.models.User;
import data.repositories.articleRepo.ArticleRepository;
import data.repositories.articleRepo.ArticleRepositoryImpl;
import data.repositories.userRepo.UserRepository;
import data.repositories.userRepo.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentRepositoryImplTest {
    private CommentRepository commentRepository;
    private Comment comment1;
    User user1;
    UserRepository userRepository;
    Article article1;
    ArticleRepository articleRepository;

    @BeforeEach
    public void setUp() {
        user1 = new User();
        userRepository = new UserRepositoryImpl();
        userRepository.save(user1);
        article1 = new Article();
        articleRepository = new ArticleRepositoryImpl();
        article1.setAuthorId(user1.getId());
        articleRepository.save(article1);
        commentRepository = new CommentRepositoryImpl();
        comment1 = new Comment();
        comment1.setComment("This life eh...");
        comment1.setUserId(user1.getId());
        comment1.setArticleId(article1.getId());
        commentRepository.save(comment1);
    }
    @Test
    public void saveOneComment_countIsOneTest() {
        commentRepository.save(comment1);
        assertEquals(1, commentRepository.count(article1.getId()));
    }

    @Test
    public void saveOneComment_idOfCommentIsOneTest() {
        Comment savedComment = commentRepository.save(comment1);
        assertEquals(1, savedComment.getId());
    }

    @DisplayName("Update comment test")
    @Test public void saveTwoCommentsWithSameId_countIsOneTest() {
        Comment savedComment = commentRepository.save(comment1);
        assertEquals(1, commentRepository.count(article1.getId()));
        savedComment.setComment("I don give up jare");
        commentRepository.save(savedComment);
        assertEquals(1, commentRepository.count(article1.getId()));
    }

    @Test public void saveOneCommentForArticleXAndOneCommentForArticleY_findArticleYCommentByUserId_ArticleIdAndCommentIdTest() {
        Article article2 = new Article();
        article2.setAuthorId(user1.getId());
        Comment comment2 = new Comment();
        comment2.setUserId(user1.getId());
        comment2.setArticleId(article2.getId());
        commentRepository.save(comment2);

        assertEquals(comment2, commentRepository.find(user1.getId(), article2.getId(), 2));
    }

    @Test public void saveOneComment_deleteOneComment_countIsZeroTest(){
        commentRepository.save(comment1);
        assertEquals(1, commentRepository.count(article1.getId()));
        commentRepository.delete(user1.getId(), article1.getId(), 1);
        assertEquals(0, commentRepository.count(article1.getId()));
    }
    @Test public void saveOneCommentForArticle_A_byArticleX_And_anotherForArticleY_eachArticleCommentCountIsOneTest(){
        User user2 = new User();
        userRepository.save(user2);
        Article article2 = new Article();
        article2.setAuthorId(user2.getId());
        articleRepository.save(article2);
        Comment comment2 = new Comment();
        comment2.setUserId(user2.getId());
        comment2.setArticleId(article2.getId());
        commentRepository.save(comment2);

        assertEquals(1, commentRepository.count(article1.getId()));
        assertEquals(1, commentRepository.count(article2.getId()));
        assertEquals(2, commentRepository.countAll());
    }

    @Test public void saveTwoCommentsForArticleX_andSaveOneCommentForArticleY_deleteOneCommentForArticleX_articleXCommentCountIsOneTest(){
        Comment comment2 = new Comment();
        comment2.setUserId(user1.getId());
        comment2.setArticleId(article1.getId());
        commentRepository.save(comment2);
        assertEquals(2, commentRepository.count(article1.getId()));

        Article article2 = new Article();
        article2.setAuthorId(user1.getId());
        articleRepository.save(article2);
        Comment comment3 = new Comment();
        comment3.setUserId(user1.getId());
        comment3.setArticleId(article2.getId());
        commentRepository.save(comment3);
        assertEquals(1, commentRepository.count(article2.getId()));

        assertEquals(3, commentRepository.countAll());

        commentRepository.delete(user1.getId(), article1.getId(), 1);
        assertEquals(1, commentRepository.count(article1.getId()));
        assertEquals(1, commentRepository.count(article2.getId()));
        assertEquals(2, commentRepository.countAll());
    }


}