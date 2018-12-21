package com.snokant.projekt.ServicesTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import com.snokant.projekt.Configuration.JwtConfiguration.JwtGen;
import com.snokant.projekt.Model.Product;
import com.snokant.projekt.Model.User;
import com.snokant.projekt.Repository.UserRepository;
import com.snokant.projekt.Service.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTests {
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private UserRepository userRepository;


    private Long categoryId = 3L;
    List<Product> productList = Arrays.asList(
            new Product(40L, "Twoja matka", "testowe", 123, categoryId, new User(10), "192.168.0.4/erdupkoZdjecia/2018_11_28_19_13_14~GuyMichaels2013200x200.jpg"),
            new Product(41L, "Twoja matka2", "testowe2", 1234, categoryId, new User(10), "192.168.0.4/erdupkoZdjecia/2018_11_28_19_13_14~GuyMichaels2013200x200.jpg"));

    @Test
    public void addProductTest(){
        User user = new User("krzys.snopko@wp.pl","qwertyuiop");
        MockMultipartFile image = new MockMultipartFile("image", "filename.jpg", "image/jpeg", "imagexdd".getBytes());

        JwtGen jwtGen = new JwtGen();
        String token = jwtGen.generateToken(user);

        Product product =new Product(40L, "Twoja matka", "testowe", 123, categoryId, new User(10), "192.168.0.4/erdupkoZdjecia/2018_11_28_19_13_14~GuyMichaels2013200x200.jpg");

        //when(productRepository.findProductsByOwnerIdOrderByCreatedDesc(10)).thenReturn(productList);
        when(userRepository.findUserByEmail("krzys.snopko@wp.pl")).thenReturn(user);

        assertEquals(Arrays.asList("GIT", "Dodano produkt"),productService.addProduct(image,product,token));

        assertEquals(Arrays.asList("BLAD", "Blad z tokenem"),productService.addProduct(image,product,null));
        assertEquals(Arrays.asList("BLAD", "Blad z plikiem"),productService.addProduct(null,product,token));
        assertEquals(Arrays.asList("BLAD", "Blad z produktem"),productService.addProduct(image,null,token));
    }

}
