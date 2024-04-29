package com.ali.java.todo.service.impl;

import com.ali.java.todo.dto.CreateCategoryDto;
import com.ali.java.todo.dto.CreateTodoDto;
import com.ali.java.todo.dto.CreateUserDto;
import com.ali.java.todo.exception.NotFoundException;
import com.ali.java.todo.model.Category;
import com.ali.java.todo.model.User;
import com.ali.java.todo.repository.CategoryRepository;
import com.ali.java.todo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;



@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final ModelMapper mapper;
    private final CategoryRepository categoryRepository;

    @Override
    public Category save(CreateCategoryDto category) {

        return categoryRepository.save(toCategory(category));
    }

    @Override
    public Category update(CreateCategoryDto categoryDto) {
        Category category= toCategory(categoryDto);
        Category oldCategory = categoryRepository.findById(category.getId()).orElse(null);
        oldCategory.setDescription(category.getDescription());
        oldCategory.setName(category.getName());

        return oldCategory;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();

    }

    @Override
    public Category findById(Long id) throws NotFoundException {

        Category category= categoryRepository.findById(id).orElse(null);
        if(category!=null){
            return category ;
        }

        throw new NotFoundException("the requested user does not exist");

    }



    @Override
    public String delete(Long id) {

        categoryRepository.deleteById(id);
        return "Deleted";
    }

    private Category toCategory(CreateCategoryDto createUserDto){

        return mapper.map(createUserDto, Category.class);
    }


}
