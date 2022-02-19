package com.companyhowitworks.springaopexample;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.sql.DataSource;
import javax.validation.constraints.Max;
import java.util.List;

@SpringBootApplication
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "10m")
public class AopExamplesApplication {

    @Bean
    public CommandLineRunner clr(JdbcTemplate jt) {
        return args -> {
            jt.execute("CREATE TABLE shedlock(name VARCHAR(64) NOT NULL PRIMARY KEY, lock_until TIMESTAMP NOT NULL," +
                "    locked_at TIMESTAMP NOT NULL, locked_by VARCHAR(255) NOT NULL);");
        };
    }

    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(
            JdbcTemplateLockProvider.Configuration.builder()
                .withJdbcTemplate(new JdbcTemplate(dataSource))
                .usingDbTime()
                .build()
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(AopExamplesApplication.class, args);
    }
}

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
class Book {
    @Id
    private Long id;
    private String name;
}

interface BookRepository extends JpaRepository<Book, Long> {

}

@Service
@RequiredArgsConstructor
@Slf4j
class BookService {
    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public Book save(Book book) {
        final Book savedBook = bookRepository.save(book);
        if (Math.random() > 0.5) {
            throw new RuntimeException("Oops...");
        }
        return savedBook;
    }

    public Book findById(long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Scheduled(fixedRateString = "PT5S")
//    @SchedulerLock(name = "countBooks", lockAtMostFor = "13s", lockAtLeastFor = "14s")
    @SchedulerLock(name = "countBooks", lockAtMostFor = "13s", lockAtLeastFor = "12s")
    public void countBooks() {
        log.info("Books count = {}", bookRepository.count());
    }
}

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
class BookController {
    private final BookService bookService;

    @GetMapping
    public List<Book> books() {
        return bookService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.save(book));
    }

    @GetMapping(path = "/{id}")
    public Book findBook(@PathVariable @Max(10) long id) {
        return bookService.findById(id);
    }
}