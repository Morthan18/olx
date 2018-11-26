package com.snokant.projekt;

import com.snokant.projekt.Controller.CategoryController;
import com.snokant.projekt.Controller.ProductController;
import com.snokant.projekt.Domain.Category;
import com.snokant.projekt.Repository.CategoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProductControllerTests {
    @Autowired
    MockMvc mvc;
    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryController categoryController;
    @Before
    public void creaTeFakeCategories(){
        Category category = new Category();
        category.setCategory_name("test");
        Category category2 = new Category();
        category.setCategory_name("test2");
        categoryRepository.save(category);
        categoryRepository.save(category2);
    }
    @Test
    public void shouldReturnCategories(){
        List<Category> categories = categoryRepository.findAll();
        Assert.assertEquals(categories,categoryController.allCategories());
    }
}
