package fr.charlesfouquet.demospring.repository;

import fr.charlesfouquet.demospring.beans.Article;
import fr.charlesfouquet.demospring.beans.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    @Query(value = "SELECT * FROM article ORDER BY date DESC", nativeQuery = true)
    List<Article> readDateDesc();
}
