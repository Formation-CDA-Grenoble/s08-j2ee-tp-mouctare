package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;
import com.example.demo.entity.*;

import com.example.demo.repository.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/Articles")
public class ArticleController {

    // Injection de dépendance
    @Autowired
    private ArticleRepository ArticleRepository;
    
    @GetMapping("")
    public List<Article> getAllArticles() {
        return ArticleRepository.findAll();
    }

    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Article createArticle(@Valid @RequestBody Article Article) {
        return ArticleRepository.save(Article);
    }

    @PutMapping("/{id}")
    public Article updateArticle(@Valid @RequestBody Article newArticle) {
        Article article = this.fetchArticle(newArticle.getId());
        article.setTitle(newArticle.getTitle());
        article.setDate(newArticle.getDate());
        article.setAuthor(newArticle.getAuthor());
        article.setContent(newArticle.getContent());
        article.setClaps(newArticle.getClaps());
        article.setUser_id(newArticle.getUser_id());
        return ArticleRepository.save(article);
        
    }

    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable(value = "id") Long ArticleId) {
        return this.fetchArticle(ArticleId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable(value = "id") Long ArticleId) {
        Article Article = this.fetchArticle(ArticleId);
        ArticleRepository.delete(Article);
    }

    public Article fetchArticle(Long ArticleId) {
        Article Article = ArticleRepository.findById(ArticleId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found")
        );
        return Article;
    }
}
