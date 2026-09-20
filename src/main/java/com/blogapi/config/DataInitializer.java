package com.blogapi.config;

import com.blogapi.model.entity.Category;
import com.blogapi.model.entity.Comment;
import com.blogapi.model.entity.Post;
import com.blogapi.repository.CategoryRepository;
import com.blogapi.repository.CommentRepository;
import com.blogapi.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Profile("dev")
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public DataInitializer(CategoryRepository categoryRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) {
        if (categoryRepository.count() > 0) {
            log.info("Sample data already exists. Skipping data initialization.");
            return;
        }

        log.info("==================================================");
        log.info("Initializing sample data for development environment");
        log.info("==================================================");

        // 1. Create Categories
        Category tech = Category.builder()
                .name("Technology")
                .description("Latest news, trends, and breakthroughs in tech and gadgets")
                .build();

        Category programming = Category.builder()
                .name("Programming")
                .description("Coding guides, software engineering patterns, and tutorials")
                .build();

        Category webDev = Category.builder()
                .name("Web Development")
                .description("Frontend, backend, APIs, and modern cloud web architectures")
                .build();

        List<Category> savedCategories = categoryRepository.saveAll(Arrays.asList(tech, programming, webDev));
        log.info("✅ Created {} categories:", savedCategories.size());
        savedCategories.forEach(c -> log.info("   {}. {}", c.getId(), c.getName()));

        // 2. Create Sample Posts
        Post post1 = Post.builder()
                .title("Getting Started with Spring Boot 3")
                .content("Spring Boot makes it effortless to create stand-alone, production-grade Spring based Applications that you can 'just run'. It provides auto-configuration, starter POMs, and embedded servers.")
                .author("John Doe")
                .category(programming)
                .build();

        Post post2 = Post.builder()
                .title("Mastering RESTful API Design")
                .content("Building clean, predictable REST APIs requires adherence to HTTP standards, proper status codes, resource-oriented URIs, pagination, and clear error schemas.")
                .author("Jane Smith")
                .category(webDev)
                .build();

        Post post3 = Post.builder()
                .title("The Evolution of Artificial Intelligence in 2026")
                .content("AI agents and advanced LLMs are redefining software development and everyday computing workflows. Autonomous coding assistants are now standard developer companions.")
                .author("Alice Johnson")
                .category(tech)
                .build();

        Post post4 = Post.builder()
                .title("Deep Dive into Spring Data JPA and Hibernate")
                .content("Learn how Spring Data JPA simplifies repository implementations with automatic query generation, custom JPQL queries, pagination, and transactional caching.")
                .author("John Doe")
                .category(programming)
                .build();

        Post post5 = Post.builder()
                .title("Building Modern Single Page Applications")
                .content("Modern frontend frameworks pair seamlessly with Spring Boot REST backends to deliver fast, reactive, and delightful user experiences across web and mobile platforms.")
                .author("Bob Wilson")
                .category(webDev)
                .build();

        List<Post> savedPosts = postRepository.saveAll(Arrays.asList(post1, post2, post3, post4, post5));
        log.info("✅ Created {} sample blog posts", savedPosts.size());

        // 3. Create Sample Comments
        Comment comment1 = Comment.builder()
                .author("TechEnthusiast")
                .content("This is an awesome and clear guide to Spring Boot 3!")
                .post(post1)
                .approved(true)
                .build();

        Comment comment2 = Comment.builder()
                .author("DevExplorer")
                .content("Really helped me understand the difference between @Controller and @RestController.")
                .post(post1)
                .approved(true)
                .build();

        Comment comment3 = Comment.builder()
                .author("ApiArchitect")
                .content("Great tips on RESTful best practices and HTTP status code choices.")
                .post(post2)
                .approved(true)
                .build();

        commentRepository.saveAll(Arrays.asList(comment1, comment2, comment3));
        log.info("✅ Created 3 sample comments");

        log.info("==================================================");
        log.info("🚀 Application Sample Data Loaded Successfully");
        log.info("📚 Swagger UI: http://localhost:8080/swagger-ui.html");
        log.info("📊 H2 Console: http://localhost:8080/h2-console");
        log.info("==================================================");
    }
}
