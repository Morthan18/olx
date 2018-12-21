package com.snokant.projekt.Service;

import com.snokant.projekt.Configuration.JwtConfiguration.JwtConstants;
import com.snokant.projekt.Model.Product;
import com.snokant.projekt.Repository.ProductRepository;
import com.snokant.projekt.Model.User;
import com.snokant.projekt.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    public static final String imagesDirectoryPathToSaveInRepository = "192.168.0.4/erdupkoZdjecia/";
    public static final String imagesDirectoryPathToSaveInDisc = "C:/xampp/htdocs/erdupkoZdjecia/";
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private UserServiceImpl userService;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, UserServiceImpl userService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> p = productRepository.findById(id);
        return p.orElse(null);
    }

    @Override
    public List<Product> getAllProductsByCategory(Long category) {
        return productRepository.findProductsByCategoryId(category);
    }


    @Override
    public List<String> findProductNameByNames(String phrase) {

        List<String> names = productRepository.findProductNamesByContaining(phrase);
        if (names != null) {
            return names;
        }
        return Arrays.asList("Brak wyników!");
    }

    @Override
    public List<Product> getXProductsByCategory(int howMany, Long categoryId,int actual) {
        return productRepository.findXRandomProducts(howMany, categoryId,actual);
    }

    @Override
    public List<Product> findXNewestProducts(int x) {
        return productRepository.findXNewestProducts(x);
    }

    @Override
    public List<Product> findXNewestProductsByCategory(int x, int category) {
        return productRepository.findXNewestProductsByCategory(x, category);
    }

    @Transactional
    @Override
    public List<String> addProduct(MultipartFile file, Product product, String token) {
        if (token == null) {
            return Arrays.asList("BLAD", "Blad z tokenem");
        }
        if (file == null) {
            return Arrays.asList("BLAD", "Blad z plikiem");
        }
        if (product == null) {
            return Arrays.asList("BLAD", "Blad z produktem");
        }
        String filePath = savedFilePath(file);
        if (filePath != null && filePath.startsWith(imagesDirectoryPathToSaveInRepository)) {
            User currentSessionUser = getCurrentSessionUser(token);
            product.setOwner(new User(currentSessionUser.getId()));

            product.setImage(filePath);

            productRepository.save(product);
            return Arrays.asList("GIT", "Dodano produkt");

        }
        return Arrays.asList("BLAD", "Blad ogolny");
    }


    private String savedFilePath(MultipartFile multipartFile) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String filenameWithDate = dateFormat.format(new Date()) + "~" + multipartFile.getOriginalFilename();
        String filePathOnDisc = imagesDirectoryPathToSaveInDisc + filenameWithDate;

        try {
            File file = new File(filePathOnDisc);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();

        } catch (IOException e) {
            return e.getMessage();
        }
        return imagesDirectoryPathToSaveInRepository + filenameWithDate;
    }

    private User getCurrentSessionUser(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JwtConstants.SECRET)
                .parseClaimsJws(token).getBody();
        return userRepository.findUserByEmail(claims.get("email").toString());
    }

    @Transactional
    @Override
    public List<Product> findMyProducts(String token) {
        User user = getCurrentSessionUser(token);
        List<Product> fullDetailsProducts = productRepository.findProductsByOwnerIdOrderByCreatedDesc(user.getId());
        List<Product> someDetailsProducts = new ArrayList<>();


        fullDetailsProducts.forEach(
                product -> someDetailsProducts.add(
                        new Product(product.getName(), product.getId())));


        return someDetailsProducts;
    }

    @Transactional
    @Override
    public List<Product> findUserProducts(int userId) {
        List<Product> fullDetailsProducts = productRepository.findProductsByOwnerIdOrderByCreatedDesc(userId);
        List<Product> someDetailsProducts = new ArrayList<>();

        fullDetailsProducts.forEach(
                product -> someDetailsProducts.add(
                        new Product(product.getName(), product.getId(), product.getImage())));


        return someDetailsProducts;
    }

    @Transactional
    @Override
    public Object checkIfUserIsProductOwner(String token, long productId) {
        Product product = getOwnersProduct(token, productId);
        if (product != null) {
            return new Product(product.getName(),product.getDescription(),product.getPrice());
        }
        return Arrays.asList("BLAD", "Produkt nie nalezy do goscia");
    }

    private Product getOwnersProduct(String token, long productId) {
        User u = getCurrentSessionUser(token);
        List<Product> userProducts = productRepository.findProductsByOwnerIdOrderByCreatedDesc(u.getId());
        for (Product p : userProducts) {
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

    @Transactional
    @Override
    public List<String> modify(String token, Product productFromRequest, long productId) {
        if (checkIfUserIsProductOwner(token, productId) instanceof Product) {
            Product productInRepo = productRepository.getOne(productId);
            productInRepo.setName(productFromRequest.getName());
            productInRepo.setDescription(productFromRequest.getDescription());
            productInRepo.setPrice(productFromRequest.getPrice());
            productRepository.save(productInRepo);
            return Arrays.asList("GIT", "Zmodyfikowano produkt");
        }
        return Arrays.asList("BLAD", "Kurka wodna");
    }

    @Override
    public Long getProductCountByCategory(Long categoryId) {
        return productRepository.countByCategoryId(categoryId);
    }

    @Override
    public List<String> deleteProduct(long productId, String token) {
        Product product = getOwnersProduct(token, productId);
        if (product != null) {
            productRepository.delete(product);
            return Arrays.asList("GIT", "Usunięto produkt");
        }
        return Arrays.asList("BLAD", "Nie usunięto produktu");
    }

    @Override
    public List<Product> findByName(String name) {
        return productRepository.findProductsByName(name);
    }

    @Override
    public int getProductCount() {
        return productRepository.findProductCount();
    }

    @Override
    public boolean getProductByIdWithToken(Long id, String token) {
        User user = getCurrentSessionUser(token);
        List<Integer> list = getAllowedUserListAsIntegerList(id);
        if(list!=null) {
            for (int i : list) {
                if (user.getId() == list.get(i-1)) {
                    return true;
                }
            }
        }
        return false;
    }
    public List<Integer> getAllowedUserListAsIntegerList(Long id) {

        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            User u = product.get().getOwner();
            String listWithCommas = u.getAllowedUserListToShowThemPhoneNumber();
            if (listWithCommas == null) {
                return null;
            }
            String[] allowedUsersListAsString = listWithCommas
                    .split(",");
            int[] awaitingUsersListAsInt = Arrays.stream(allowedUsersListAsString).mapToInt(Integer::parseInt).toArray();

            Integer[] allowedUsersListAsInteger = Arrays.stream(awaitingUsersListAsInt).boxed().toArray(Integer[]::new);
            return Arrays.asList(allowedUsersListAsInteger);
        }
        return null;
    }
}