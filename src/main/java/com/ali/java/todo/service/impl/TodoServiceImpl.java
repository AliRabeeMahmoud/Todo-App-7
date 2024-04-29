package com.ali.java.todo.service.impl;

import com.ali.java.todo.dto.CreateTodoDto;
import com.ali.java.todo.dto.CreateUserDto;
import com.ali.java.todo.exception.NotFoundException;
import com.ali.java.todo.model.Todo;
import com.ali.java.todo.model.User;
import com.ali.java.todo.repository.CategoryRepository;
import com.ali.java.todo.repository.TodoRepository;
import com.ali.java.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;




@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {


    private final ModelMapper mapper;
    private final TodoRepository todoRepository;


    private final CategoryRepository categoryRepository;

    @Override
    public Todo save(CreateTodoDto todo) {

        return todoRepository.save(toTodo(todo));
    }

    @Override
    public Todo update(CreateTodoDto todoDto) {
        Todo todo= toTodo(todoDto);
        Todo oldTodo = todoRepository.findById(todo.getId()).orElse(null);
        oldTodo.setCategory(todo.getCategory());
        oldTodo.setTitle(todo.getTitle());
        oldTodo.setDescription(todo.getDescription());
        oldTodo.setDone(todo.getDone());
        oldTodo.setFavorite(todo.getFavorite());
        return oldTodo;
    }

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public Todo findById(Long id) throws NotFoundException {

        Todo todo= todoRepository.findById(id).orElse(null);

        if(todo!=null){
            return todo ;
        }

        throw new NotFoundException("the requested user does not exist");

    }


    @Override
    public String delete(Long id) {

        todoRepository.deleteById(id);
        return "Deleted";
    }

    private Todo toTodo(CreateTodoDto createUserDto){

        return mapper.map(createUserDto, Todo.class);
    }
}
