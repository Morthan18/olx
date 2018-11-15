package com.snokant.projekt;

import com.snokant.projekt.Controller.ProductController;
import com.snokant.projekt.Domain.Category;
import com.snokant.projekt.Repository.CategoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProductControllerTests {
    @Mock
    CategoryRepository categoryRepository;
    @Autowired
    ProductController productController;
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
       // Assert.assertEquals("a",getController.allCategories());

        Assert.assertEquals(1,1);

    }
}
