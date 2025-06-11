package ru.netology.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
  private final PostService service;

  public PostController(PostService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<Post>> all() {
    return ResponseEntity.ok(service.all());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> getById(@PathVariable long id) {
    return ResponseEntity.ok(service.getById(id));
  }

  @PostMapping
  public ResponseEntity<Post> save(@RequestBody Post post) {
    return ResponseEntity.ok(service.save(post));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> removeById(@PathVariable long id) {
    service.removeById(id);
    return ResponseEntity.ok("Пост с id=" + id + " удален");
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFound(NotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }
}