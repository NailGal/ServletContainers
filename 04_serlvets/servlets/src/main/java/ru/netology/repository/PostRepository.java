package ru.netology.repository;

import ru.netology.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.*;

// Stub
public class PostRepository {

  private final AtomicLong counter = new AtomicLong(0);
  private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();


  public List<Post> all() {
    return new ArrayList<>(posts.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      long id = counter.incrementAndGet();
      post.setId(id);
      posts.put(id, post);
      return post;
    }
    return posts.computeIfPresent(post.getId(), (id, oldPost) -> post);
  }

  public boolean removeById(long id) {
    return posts.remove(id) != null;
  }
}
