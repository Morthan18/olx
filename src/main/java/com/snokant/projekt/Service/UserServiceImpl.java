package com.snokant.projekt.Service;

import com.snokant.projekt.Configuration.JwtConfiguration.JwtConstants;
import com.snokant.projekt.Configuration.JwtConfiguration.JwtGen;
import com.snokant.projekt.Model.User;
import com.snokant.projekt.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private BCryptPasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private JwtGen generator;


    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, JwtGen generator) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.generator = generator;
    }

    @Transactional
    @Override
    public List<String> addNewUser(User user) {
        if (userEmailExists(user)) {
            return Arrays.asList("BLAD", "Podany email jest już zajęty");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return Arrays.asList("GIT", "Zarejestrowano", generator.generateToken(user));
    }

    private boolean userEmailExists(User user) {
        return userRepository.findUserByEmail(user.getEmail()) != null;
    }

    @Transactional
    @Override
    public List<String> logIn(User user) {
        User userInDb = userRepository.findUserByEmail(user.getEmail());

        if (userInDb != null && (passwordsAreTheSame(user.getPassword(), userInDb.getPassword()))) {

            return Arrays.asList("GIT", generator.generateToken(user));
        }
        return Arrays.asList("BLAD", "Podaj prawidłowy email lub hasło");
    }


    private boolean passwordsAreTheSame(String password, String password2) {

        return passwordEncoder.matches(password, password2);
    }

    @Override
    public List<String> modifyProfile(User u, String token) {
        User user = userRepository.findUserByEmail(getCurrentSessionUser(token).getEmail());
        if (u.getPassword() != null) {
            user.setPassword(u.getPassword());
            userRepository.save(user);
            return Arrays.asList("GIT", "Hasło zostało zmienione");
        } else {
            if (user != null) {
                user.setEmail(u.getEmail());
                user.setFirstName(u.getFirstName());
                user.setLastName(u.getLastName());
                user.setPlace(u.getPlace());
                user.setZipCode(u.getZipCode());
                user.setPhoneNumber(u.getPhoneNumber());
                userRepository.save(user);
                return Arrays.asList("GIT", "Zmodyfikowano profil",generator.generateToken(user));
            }
        }
        return Arrays.asList("BLAD", "Nie udało się zmodyfikować profilu");
    }

    @Override
    public User getUserDetails(String token) {
        User user = getCurrentSessionUser(token);
        user.setPassword(null);
        return userRepository.findUserByEmail(user.getEmail());
    }

    @Transactional
    @Override
    public List<String> sendRequestForPhoneNumber(int userIdThatPhoneNumberIWant, String token) {
        User buyer = getCurrentSessionUser(token);
        Optional<User> optionalSeller = userRepository.findById(userIdThatPhoneNumberIWant);

        if (optionalSeller.isPresent() && buyer != null) {
            User seller = optionalSeller.get();
            String awaitingUsersList = seller.getAwaitingUserListToShowThemPhoneNumber();

            if (awaitingUsersList == null) {
                seller.setAwaitingUserListToShowThemPhoneNumber(String.valueOf(buyer.getId()));
                userRepository.save(seller);
            } else if (awaitingUsersList.contains(String.valueOf(buyer.getId()))) {
                return Arrays.asList("BLAD","Gosc juz dodany do tela");
            } else {
                seller.setAwaitingUserListToShowThemPhoneNumber(
                        awaitingUsersList + "," + buyer.getId());
                userRepository.save(seller);
            }
            return Arrays.asList("GIT", "wszystko ok");
        }
        return Arrays.asList("BLAD", "Wystąpił błąd podczas proby wyslania prośby o numer telefonu");
    }

    @Transactional
    @Override
    public List<User> getMyRequestList(String token) {

        List<Integer> list = getAwaitingUserListAsIntegerList(token);
        if(list!=null) {
            return userRepository.findAllById(list);
        }
        return null;
    }

    @Transactional
    @Override
    public List<String> acceptRequestPhone(String token, int userId) {
        Integer idFromMyAwaitingList = 0;
        User seller = getCurrentSessionUser(token);

        List<Integer> awaitingUsersList = new ArrayList<>();
        List<Integer> awaitingUsersList2 = getAwaitingUserListAsIntegerList(token);

        if (awaitingUsersList2 != null) {
            awaitingUsersList.addAll(awaitingUsersList2);
        }
        try {
             idFromMyAwaitingList = awaitingUsersList.stream()
                    .filter(id -> id.equals(userId)).collect(Collectors.toList()).get(0);
        }catch (IndexOutOfBoundsException e){
            return Arrays.asList("BLAD","Brak ludzi na liscie");
        }


        if(idFromMyAwaitingList!=0) {

            awaitingUsersList.removeIf(id -> id == userId);

            List<Integer> allowedUsersList = new ArrayList<>();
            List<Integer> allowedUsersList2 = getAllowedUserListAsIntegerList(token);

            if (allowedUsersList2 != null) {
                allowedUsersList.addAll(allowedUsersList2);
            }

            allowedUsersList.add(userId);


            seller.setAwaitingUserListToShowThemPhoneNumber(convertIntegerListToString(awaitingUsersList));
            seller.setAllowedUserListToShowThemPhoneNumber(convertIntegerListToString(allowedUsersList));
            userRepository.save(seller);
            return Arrays.asList("GIT","Udzielono pozwolenia na pokazanie numeru tela");
        }
        return Arrays.asList("BLAD","Chyba nie ma goscia albo cos, no blad");
    }
    private String convertIntegerListToString(List<Integer> list){
        StringBuilder allowedUsersStringBuilderToSaveInRep  = new StringBuilder();
        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext())
        {
            allowedUsersStringBuilderToSaveInRep.append(iterator.next());
            if(iterator.hasNext()){
                allowedUsersStringBuilderToSaveInRep.append(",");
            }
        }
        return allowedUsersStringBuilderToSaveInRep.toString();
    }

    private List<Integer> getAwaitingUserListAsIntegerList(String token) {

        String listWithCommas = getCurrentSessionUser(token).getAwaitingUserListToShowThemPhoneNumber();
        if (listWithCommas == null) {
            return null;
        }
        String[] awaitingUsersListAsString = listWithCommas
                .split(",");
        if(awaitingUsersListAsString[0].equals("")){
            return null;
        }
        int[] awaitingUsersListAsInt =
                Arrays.stream(awaitingUsersListAsString)
                .mapToInt(Integer::parseInt).toArray();

        Integer[] awaitingUsersListAsInteger =
                Arrays.stream(awaitingUsersListAsInt)
                        .boxed()
                        .toArray(Integer[]::new);
        return Arrays.asList(awaitingUsersListAsInteger);
    }
    public List<Integer> getAllowedUserListAsIntegerList(String token) {

        String listWithCommas = getCurrentSessionUser(token).getAllowedUserListToShowThemPhoneNumber();
        if (listWithCommas == null) {
            return null;
        }
        String[] allowedUsersListAsString = listWithCommas
                .split(",");
        int[] awaitingUsersListAsInt = Arrays.stream(allowedUsersListAsString).mapToInt(Integer::parseInt).toArray();

        Integer[] allowedUsersListAsInteger = Arrays.stream(awaitingUsersListAsInt).boxed().toArray(Integer[]::new);
        return Arrays.asList(allowedUsersListAsInteger);
    }

    private User getCurrentSessionUser(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JwtConstants.SECRET)
                .parseClaimsJws(token).getBody();
        return userRepository.findUserByEmail(claims.get("email").toString());
    }
}
