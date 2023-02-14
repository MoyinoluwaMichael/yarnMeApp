package data.repositories.articleRepo;

import data.models.Article;
import data.models.User;
import data.repositories.userRepo.UserRepository;
import data.repositories.userRepo.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArticleRepositoryImplTest {
    private ArticleRepository articleRepository;
    private UserRepository userRepository;
    private Article article1;
    private User user1;

    @BeforeEach
    public void setUp() {

        articleRepository = new ArticleRepositoryImpl();
        article1 = new Article();
        userRepository = new UserRepositoryImpl();
        user1 = new User();
        userRepository.save(user1);
        article1.setAuthorId(user1.getId());
        article1.setTitle("Summary of Life");
        article1.setBody("Life is a spoon");
        articleRepository.save(article1);
    }
    @Test
    public void saveOneUserArticle_userArticleCountIsOneTest() {
        articleRepository.save(article1);
        assertEquals(1, articleRepository.count(user1.getId()));
    }

    @Test
    public void saveOneUserArticle_idOfUserArticleIsOneTest() {
        Article savedArticle = articleRepository.save(article1);
        assertEquals(1, savedArticle.getId());
    }

    @DisplayName("Update article test")
    @Test
    public void saveAUserTwoArticlesWithSameId_countIsOneTest() {
        Article savedArticle = articleRepository.save(article1);
        assertEquals(1, articleRepository.count(user1.getId()));
        savedArticle.setBody("Ile Aye, Ile Asan");
        articleRepository.save(savedArticle);
        assertEquals(1, articleRepository.count(user1.getId()));
    }

    @Test
    public void saveOneUserArticle_findArticleByUserIdAndArticleIdTest() {
        Article savedArticle = articleRepository.save(article1);
        assertEquals(1, savedArticle.getId());
        Article foundArticle = articleRepository.findArticle(user1.getId(), article1.getId());
        assertEquals(foundArticle, savedArticle);
    }

    @Test public void saveOneUserArticle_deleteOneUserArticle_countIsZeroTest(){
        articleRepository.save(article1);
        assertEquals(1, articleRepository.count(user1.getId()));
        articleRepository.delete(1, article1.getId());
        assertEquals(0, articleRepository.count(user1.getId()));
    }

    @Test public void saveOneArticleForUserX_And_Y_eachUserArticleCountIsOneTest(){
        User user2 = new User();
        userRepository.save(user2);
        Article article2 = new Article();
        article2.setAuthorId(user2.getId());
        articleRepository.save(article2);
        assertEquals(1, articleRepository.count(user1.getId()));
        assertEquals(1, articleRepository.count(user2.getId()));
    }
    @Test public void saveOneArticleForUserX_and_Y_totalArticleCountIsTwoTest(){
        User user2 = new User();
        userRepository.save(user2);
        Article article2 = new Article();
        article2.setAuthorId(user2.getId());
        articleRepository.save(article2);
        assertEquals(1, articleRepository.count(user1.getId()));
        assertEquals(1, articleRepository.count(user2.getId()));
        assertEquals(2, articleRepository.countAll());
    }

    @Test public void saveTwoArticlesForUserX_andSaveOneArticleForUserY_deleteOneArticleForUserX_userXArticleCountIsOneTest(){
        Article article2 = new Article();
        article2.setAuthorId(user1.getId());
        articleRepository.save(article2);
        assertEquals(2, articleRepository.count(user1.getId()));

        User user2 = new User();
        userRepository.save(user2);
        Article article3 = new Article();
        article3.setAuthorId(user2.getId());
        articleRepository.save(article3);
        assertEquals(1, articleRepository.count(user2.getId()));
        assertEquals(3, articleRepository.countAll());

        articleRepository.delete(user1.getId(), article1.getId());
        assertEquals(1, articleRepository.count(user1.getId()));
        assertEquals(2, articleRepository.countAll());
    }




}