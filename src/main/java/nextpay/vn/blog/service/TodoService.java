package nextpay.vn.blog.service;

import nextpay.vn.blog.entity.model.Todo;
import nextpay.vn.blog.payload.ApiResponse;
import nextpay.vn.blog.payload.PagedResponse;
import nextpay.vn.blog.security.UserPrincipal;

public interface TodoService {

    Todo completeTodo(Long id, UserPrincipal currentUser);

    Todo unCompleteTodo(Long id, UserPrincipal currentUser);

    PagedResponse<Todo> getAllTodos(UserPrincipal currentUser, int page, int size);

    Todo addTodo(Todo todo, UserPrincipal currentUser);

    Todo getTodo(Long id, UserPrincipal currentUser);

    Todo updateTodo(Long id, Todo newTodo, UserPrincipal currentUser);

    ApiResponse deleteTodo(Long id, UserPrincipal currentUser);

}
