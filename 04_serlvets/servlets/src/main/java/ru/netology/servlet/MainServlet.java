package ru.netology.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.config.AppConfig;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  private static final String API_POSTS_PATH = "/api/posts";
  private static final String API_POSTS_ID_REGEX = API_POSTS_PATH + "/\\d+";

  private PostController controller;

  @Override
  public void init() {
    // Создаём контекст Spring и получаем бин контроллера
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    controller = context.getBean(PostController.class);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    // если деплоились в root context, то достаточно этого
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();
      // primitive routing
      if (method.equals("GET") && path.equals(API_POSTS_PATH)) {
        controller.all(resp);
        return;
      }
      if (method.equals("GET") && path.matches(API_POSTS_ID_REGEX)) {

        //TODO (delete after done)
        // final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));

        final var id = extractId(path);
        controller.getById(id, resp);
        return;
      }
      if (method.equals("POST") && path.equals(API_POSTS_PATH)) {
        controller.save(req.getReader(), resp);
        return;
      }
      if (method.equals("DELETE") && path.matches(API_POSTS_ID_REGEX)) {
        // easy way
        //final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
        final var id = extractId(path);
        controller.removeById(id, resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
  private Long extractId(String path) {
    return Long.parseLong(path.substring(path.lastIndexOf("/")+1));
  }
}

