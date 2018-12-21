package com.snokant.projekt.ControllersTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snokant.projekt.Controller.ProductController;
import com.snokant.projekt.Model.Product;
import com.snokant.projekt.Model.User;
import com.snokant.projekt.Service.ProductServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductControllerTests {
    private MockMvc mockMvc;
    @InjectMocks
    private ProductController productController;
    @Mock
    private ProductServiceImpl productService;

    private String productJSONObject = "{\"name\":\"Twoja matka\",\"description\":\"testowe\",\"price\":123,\"categoryId\":3}";
    private String productJSONArray =
            "[{\"id\":40,\"name\":\"Twoja matka\",\"description\":\"testowe\",\"price\":123,\"categoryId\":3,\"owner\":10,\"image\":\"192.168.0.4/erdupkoZdjecia/2018_11_28_19_13_14~GuyMichaels2013200x200.jpg\"}," +
                    "{\"id\":41,\"name\":\"Twoja matka2\",\"description\":\"testowe2\",\"price\":1234,\"categoryId\":3,\"owner\":10,\"image\":\"192.168.0.4/erdupkoZdjecia/2018_11_28_19_13_14~GuyMichaels2013200x200.jpg\"}]";
    private Long categoryId = 3L;
    private Long productId = 40L;

    MockMultipartFile jsonFile = new MockMultipartFile("product", "dfgd", "text/plain", productJSONObject.getBytes());
    MockMultipartFile firstFile = new MockMultipartFile("image", "filename.jpg", "image/jpeg", "imagexdd".getBytes());

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .build();
    }

    @Test
    public void shouldReturnProductById() throws Exception {
        when(productService.getProductById(productId)).thenReturn(new Product(40L, "Twoja matka", "testowe", 123, categoryId, new User(10), "192.168.0.4/erdupkoZdjecia/2018_11_28_19_13_14~GuyMichaels2013200x200.jpg"));
        mockMvc.perform(get("/rest/product/productById/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().json(productJSONObject));
    }

    @Test
    public void shouldReturnAllProductsByCategoryId() throws Exception {

        when(productService.getAllProductsByCategory(categoryId)).thenReturn(
                Arrays.asList(
                        new Product(40L, "Twoja matka", "testowe", 123, categoryId, new User(10), "192.168.0.4/erdupkoZdjecia/2018_11_28_19_13_14~GuyMichaels2013200x200.jpg"),
                        new Product(41L, "Twoja matka2", "testowe2", 1234, categoryId, new User(10), "192.168.0.4/erdupkoZdjecia/2018_11_28_19_13_14~GuyMichaels2013200x200.jpg")));
        mockMvc.perform(get("/rest/product/allProductsByCategory/{categoryId}", categoryId))
                .andExpect(status().isOk())
                .andExpect(content().json(productJSONArray));
    }

    @Test
    public void shouldReturnXProductsFromCategory() throws Exception {
        int numberOfProducts = 2;
        when(productService.getXProductsByCategory(numberOfProducts, categoryId,12)).thenReturn(
                Arrays.asList(
                        new Product(40L, "Twoja matka", "testowe", 123, categoryId, new User(10), "192.168.0.4/erdupkoZdjecia/2018_11_28_19_13_14~GuyMichaels2013200x200.jpg"),
                        new Product(41L, "Twoja matka2", "testowe2", 1234, categoryId, new User(10), "192.168.0.4/erdupkoZdjecia/2018_11_28_19_13_14~GuyMichaels2013200x200.jpg")));
        mockMvc.perform(get("/rest/product/get/{categoryId}/{x}", categoryId, numberOfProducts))
                .andExpect(status().isOk())
                .andExpect(content().json(productJSONArray));
    }

    @Test
    public void shouldMapStringToJSONAddProductAndReturnGIT() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        Product p = mapper.readValue(productJSONObject, Product.class);
        when(productService.addProduct(firstFile, p, "token")).thenReturn(Arrays.asList("GIT", "Dodano produkt"));


        mockMvc.perform(MockMvcRequestBuilders.multipart("/rest/product/addProduct")
                .file(firstFile)
                .file(jsonFile)
                .header("Authorization", "token"))
                .andExpect(status().isOk())
                .andExpect(content().string("[\"GIT\",\"Dodano produkt\"]"));

        mockMvc.perform(get("/rest/product/addProduct"))
                .andExpect(status().is(405));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/rest/product/addProduct"))
                .andExpect(status().is(400));
    }

    @Test
    public void shouldReturnXNewestProducts() throws Exception {

        when(productService.findXNewestProducts(3)).thenReturn(
                Arrays.asList(new Product(40L, "Twoja matka", "testowe", 123, categoryId, new User(10), "192.168.0.4/erdupkoZdjecia/2018_11_28_19_13_14~GuyMichaels2013200x200.jpg"),
                        new Product(41L, "Twoja matka2", "testowe2", 1234, categoryId, new User(10), "192.168.0.4/erdupkoZdjecia/2018_11_28_19_13_14~GuyMichaels2013200x200.jpg")));
        mockMvc.perform(get("/rest/product/getNewestProducts/{x}", 3))
                .andExpect(status().isOk())
                .andExpect(content().json(productJSONArray));
    }


    @Test
    public void shouldReturnMyProducts() throws Exception {
        List<Product> userProducts = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            userProducts.add(new Product(1L, "toyota nr:" + i, "ladny samochod", 50000, 1L, new User(999), null));
        }
        when(productService.findMyProducts("token")).thenReturn(userProducts);

        mockMvc.perform(get("/rest/product/myProducts")
                .header("Authorization", "token"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"toyota nr:0\",\"description\":\"ladny samochod\",\"price\":50000,\"categoryId\":1,\"owner\":999,\"image\":null,\"created\":null},{\"id\":1,\"name\":\"toyota nr:1\",\"description\":\"ladny samochod\",\"price\":50000,\"categoryId\":1,\"owner\":999,\"image\":null,\"created\":null},{\"id\":1,\"name\":\"toyota nr:2\",\"description\":\"ladny samochod\",\"price\":50000,\"categoryId\":1,\"owner\":999,\"image\":null,\"created\":null},{\"id\":1,\"name\":\"toyota nr:3\",\"description\":\"ladny samochod\",\"price\":50000,\"categoryId\":1,\"owner\":999,\"image\":null,\"created\":null}]"));

        mockMvc.perform(get("/rest/product/myProducts"))
                .andExpect(status().is(400));
    }

    @Test
    public void shouldReturnUserIdProducts() throws Exception {
        int ownerId = 999;
        List<Product> userProducts = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            userProducts.add(new Product(1L, "toyota nr:" + i, "ladny samochod", 50000, 1L, new User(ownerId), null));
        }
        when(productService.findUserProducts(ownerId)).thenReturn(userProducts);

        mockMvc.perform(get("/rest/product/getProducts/{id}", ownerId))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"toyota nr:0\",\"description\":\"ladny samochod\",\"price\":50000,\"categoryId\":1,\"owner\":999,\"image\":null,\"created\":null},{\"id\":1,\"name\":\"toyota nr:1\",\"description\":\"ladny samochod\",\"price\":50000,\"categoryId\":1,\"owner\":999,\"image\":null,\"created\":null},{\"id\":1,\"name\":\"toyota nr:2\",\"description\":\"ladny samochod\",\"price\":50000,\"categoryId\":1,\"owner\":999,\"image\":null,\"created\":null},{\"id\":1,\"name\":\"toyota nr:3\",\"description\":\"ladny samochod\",\"price\":50000,\"categoryId\":1,\"owner\":999,\"image\":null,\"created\":null}]"));

        mockMvc.perform(get("/rest/product/getProducts/{id}", ""))
                .andExpect(status().is(404));
        mockMvc.perform(get("/rest/product/getProducts/{id}", "a"))
                .andExpect(status().is(400));
    }

    @Test
    public void shouldCheckIfCanModifyProduct() throws Exception {
        Product product = new Product("opel", "slaby", 1200);
        when(productService.checkIfUserIsProductOwner("token", productId)).thenReturn(product);

        String s = "{\"name\":\"opel\",\"description\":\"slaby\",\"price\":1200}";
        mockMvc.perform(get("/rest/product/getProductDetails/{id}", productId)
                .header("Authorization", "token"))
                .andExpect(status().is(200))
                .andExpect(content().json(s));

        mockMvc.perform(get("/rest/product/getProductDetails/{id}", productId))
                .andExpect(status().is(400));
    }
    @Test
    public void modifyProductTest() throws Exception{
        when(productService.modify("token",new Product("opel", "slaby", 1200),productId))
            .thenReturn(Arrays.asList("GIT", "Zmodyfikowano produkt"));



        mockMvc.perform(put("/rest/product/modifyProduct/{id}", productId)
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"opel\",\"description\":\"slaby\",\"price\":1200}".getBytes()))
                .andExpect(status().isOk())
                .andExpect(content().string("[\"GIT\",\"Zmodyfikowano produkt\"]"));

        mockMvc.perform(get("/rest/product/modifyProduct/{id}", productId))
                .andExpect(status().is(405));
        mockMvc.perform(put("/rest/product/modifyProduct/{id}", productId))
                .andExpect(status().is(400));
    }
}