package com.rammp.stretchyourbody.service;

import com.rammp.stretchyourbody.domain.*;
import com.rammp.stretchyourbody.repository.*;
import com.rammp.stretchyourbody.config.Constants;
import com.rammp.stretchyourbody.security.AuthoritiesConstants;
import com.rammp.stretchyourbody.security.SecurityUtils;
import com.rammp.stretchyourbody.service.util.RandomUtil;
import com.rammp.stretchyourbody.service.dto.UserDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final SocialService socialService;

    private final AuthorityRepository authorityRepository;

    private final UserAppRepository userAppRepository;

    private  final UserHealthRepository userHealthRepository;

    private final EntityManagerFactory entityManagerFactory;

    private final BodyPartRepository bodyPartRepository;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, SocialService socialService, AuthorityRepository authorityRepository, UserAppRepository userAppRepository, EntityManagerFactory entityManagerFactory, UserHealthRepository userHealthRepository, BodyPartRepository bodyPartRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.socialService = socialService;
        this.authorityRepository = authorityRepository;
        this.userAppRepository = userAppRepository;
        this.entityManagerFactory = entityManagerFactory;
        this.userHealthRepository = userHealthRepository;
        this.bodyPartRepository = bodyPartRepository;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
       log.debug("Reset user password for reset key {}", key);

       return userRepository.findOneByResetKey(key)
            .filter(user -> {
                ZonedDateTime oneDayAgo = ZonedDateTime.now().minusHours(24);
                return user.getResetDate().isAfter(oneDayAgo);
           })
           .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                return user;
           });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmail(mail)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(ZonedDateTime.now());
                return user;
            });
    }

    public Long createUser(String login, String password, String firstName, String lastName, String email,
        String imageUrl, String langKey) {

        User newUser = new User();
        Authority authority = authorityRepository.findOne(AuthoritiesConstants.USER);
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setImageUrl(imageUrl);
        newUser.setLangKey(langKey);
        // new user is not active
        newUser.setActivated(true);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        createUserApp(newUser);
        log.debug("Created Information for User: {}", newUser);
        Long userId = findUserByUserName(newUser.getLogin());
        return userId;
    }

    public void createUserApp(User newUser) {
        UserApp userApp = new UserApp();
        userApp.setName(newUser.getFirstName());
        userApp.setLastName(newUser.getLastName());
        userApp.setUser(newUser);
        userApp.setAge("0");
        userApp.setWeight(0.0);
        userApp.setHeight(0.0);
        userApp.setGender("Otro");
        userAppRepository.save(userApp);
    }

    public void updateUserHealth(Long userId, String age, Double weight, Double height,
                              String gender, Integer workHours, Boolean doesWorkOut,
                              Boolean isSmoker, Boolean isHealthy, String bodyPartName) {

        List<BodyPart> bodyPartList = bodyPartRepository.findAll();

        BodyPart bodyPart = null;
        for (BodyPart tempBodyPart : bodyPartList) {
            if(tempBodyPart.getName().equals(bodyPartName)) {
                bodyPart = tempBodyPart;
            }
        }

        Set<BodyPart> bodyParts = new HashSet<BodyPart>();
        bodyParts.add(bodyPart);

        UserApp userApp = userAppRepository.findOne(userId);
        UserHealth userHealth = new UserHealth();
        userApp.setAge(age);
        userApp.setWeight(weight);
        userApp.setHeight(height);
        userApp.setGender(gender);
        userHealth.setBodyParts(bodyParts);
        userHealth.setDoesWorkOut(doesWorkOut);
        userHealth.setIsHealthFood(isHealthy);
        userHealth.setIsSmoker(isSmoker);
        userHealth.setWorkHours(workHours);
        userHealth = userHealthRepository.save(userHealth);
        userApp.setUserHealth(userHealth);
        userAppRepository.save(userApp);

    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey("es"); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = new HashSet<>();
            userDTO.getAuthorities().forEach(
                authority -> authorities.add(authorityRepository.findOne(authority))
            );
            user.setAuthorities(authorities);
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(ZonedDateTime.now());
        user.setActivated(true);
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user
     * @param lastName last name of user
     * @param email email id of user
     * @param langKey language key
     * @param imageUrl image URL of user
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setLangKey(langKey);
            user.setImageUrl(imageUrl);
            log.debug("Changed Information for User: {}", user);
        });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
            .findOne(userDTO.getId()))
            .map(user -> {
                user.setLogin(userDTO.getLogin());
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                user.setEmail(userDTO.getEmail());
                user.setImageUrl(userDTO.getImageUrl());
                user.setActivated(userDTO.isActivated());
                user.setLangKey(userDTO.getLangKey());
                Set<Authority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userDTO.getAuthorities().stream()
                    .map(authorityRepository::findOne)
                    .forEach(managedAuthorities::add);
                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            socialService.deleteUserSocialConnection(user.getLogin());
            userRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public void changePassword(String password) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            String encryptedPassword = passwordEncoder.encode(password);
            user.setPassword(encryptedPassword);
            log.debug("Changed password for User: {}", user);
        });
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities() {
        return userRepository.findOneWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).orElse(null);
    }


    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        ZonedDateTime now = ZonedDateTime.now();
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusDays(3));
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getLogin());
            userRepository.delete(user);
        }
    }

    static Long idUserLogin = null;
    @Transactional(readOnly = true)
    public Long findUserByUserName(String username){

        userRepository.findOneByLogin(username).ifPresent(user -> {
            idUserLogin = (Long) entityManagerFactory.getPersistenceUnitUtil().getIdentifier(user);
        });

        Long userWebId = findUserApp(idUserLogin);

        return userWebId;
    }

    private Long findUserApp(Long idUserLogin){
        Long idWeb = null;
        List<UserApp> users = userAppRepository.findAll();
        for(int i = 0; i < users.size();i++) {
            if (users.get(i).getUser().getId() == idUserLogin){
                idWeb = users.get(i).getId();
                break;
            }
        }
        return idWeb;
    }
}
