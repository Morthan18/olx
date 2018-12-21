package com.snokant.projekt.ControllersTests;

import com.snokant.projekt.Controller.CategoryController;
import com.snokant.projekt.Model.Category;
import com.snokant.projekt.Repository.CategoryRepository;
import com.snokant.projekt.Repository.ProductRepository;
import com.snokant.projekt.Repository.UserRepository;
import com.snokant.projekt.Service.CategoryService;
import com.snokant.projekt.Service.CategoryServiceImpl;
import com.snokant.projekt.Service.ProductServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTests {
    private MockMvc mockMvc;

    @Mock
    CategoryController categoryController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .build();
    }

    @Test
    public void shouldReturnAllCategories() throws Exception {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Samochody"));
        categories.add(new Category(2L, "Obuwie"));
        categories.add(new Category(3L, "AGD_RTV"));
        String json = "[{\"categoryId\":1,\"categoryName\":\"Samochody\"},\n" +
                "  {\"categoryId\":2,\"categoryName\":\"Obuwie\"},\n" +
                "  {\"categoryId\":3,\"categoryName\":\"AGD_RTV\"}]";
        when(categoryController.allCategories()).thenReturn(categories);


        mockMvc.perform(get("/rest/category/allCategories"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));


    }
}
