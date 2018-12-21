package com.snokant.projekt.ControllersTests;

import com.snokant.projekt.Controller.CategoryController;
import com.snokant.projekt.Model.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
