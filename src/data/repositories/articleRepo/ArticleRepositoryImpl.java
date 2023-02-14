package data.repositories.articleRepo;

import data.models.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleRepositoryImpl implements ArticleRepository{

    private ArrayList <Article> articles = new ArrayList<>();
    private int count;

    @Override
    public Article save(Article article) {
        boolean articleHasNotBeenSaved = article.getId() == 0;
        if (articleHasNotBeenSaved) saveNewArticle(article);
        return article;
    }

    private void saveNewArticle(Article article) {
        article.setId(generateId());
        articles.add(article);
        count++;
    }

    private int generateId() {
        return count + 1;
    }

    @Override
    public Article findArticle(int userId, int articleId) {
        for (Article article: articles) if (article.getAuthorId() == userId && article.getId() == articleId) return article;
        return null;
    }

    @Override
    public long count(int authorId) {
        int articleCount = 0;
        for (var article: articles) if (article.getAuthorId() == authorId) articleCount++;
        return articleCount;
    }

    @Override
    public List<Article> findAll() {
        return null;
    }

    @Override
    public void delete(Article article) {
        for (Article each: articles) if (article.getAuthorId() == each.getAuthorId() && article.getId() == each.getId()) {
            articles.remove(article);
            count--;
            break;
        }
    }

    @Override
    public void delete(int userId, int id) {
        for(Article article: articles) if (article.getAuthorId() == userId) {
            articles.remove(article);
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
