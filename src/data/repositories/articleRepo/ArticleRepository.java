package data.repositories.articleRepo;

import data.models.Article;

import java.util.List;

public interface ArticleRepository {
    Article save(Article article);
    Article findArticle(int userId, int articleId);
    long count(int authorId);
    List<Article> findAll();
    void delete(Article article);
    void delete(int userId, int articleId);

    void deleteAll();

    long countAll();

}
