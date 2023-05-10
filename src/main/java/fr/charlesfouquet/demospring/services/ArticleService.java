package fr.charlesfouquet.demospring.services;

import fr.charlesfouquet.demospring.beans.Article;
import fr.charlesfouquet.demospring.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public void create(Article article) {
        articleRepository.save(article);
    }

    public List<Article> read() {
        return articleRepository.findAll();
    }

    public List<Article> readDateDesc() {
        return articleRepository.readDateDesc();
    }

    public Optional<Article> findByID(int id) {
        return articleRepository.findById(id);
    }

    public void update(int id, Article article) {
        Optional<Article> request = articleRepository.findById(id);
        if (request.isPresent()) {
            Article baseArticle = request.get();
            baseArticle.setTitle(article.getTitle());
            baseArticle.setContent(article.getContent());
            if ((!article.getPhoto().equals(baseArticle.getPhoto())) && (!article.getPhoto().isEmpty())) {
                baseArticle.setPhoto(article.getPhoto());
            }
            articleRepository.save(baseArticle);
        }
    }

    public void delete(Article article) {
        articleRepository.delete(article);
    }

    public void uploadImage(String uploadDir, String imgFilename, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try  {
            Path filePath = uploadPath.resolve(imgFilename);
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Erreur : " + imgFilename, ioe);
        }
    }
}