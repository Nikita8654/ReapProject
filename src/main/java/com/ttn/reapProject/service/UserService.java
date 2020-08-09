package com.ttn.reapProject.service;

import com.ttn.reapProject.entity.Role;
import com.ttn.reapProject.entity.User;
import com.ttn.reapProject.exception.UserNotFoundException;
import com.ttn.reapProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    // Save new user with badges and points based on user's roles
    public User save(User user) {
        User userToSave = setBadges(user);
        userToSave.setPoints(calculatePoints(userToSave));
        userToSave.setFullName(userToSave.getFirstName() + " " + userToSave.getLastName());
        return userRepository.save(userToSave);
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Integer id) {
        return userRepository.findById(id);
    }

    public User findUserById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent())
        {
            throw new UserNotFoundException("User not found with id : "+id);
        }
        return optionalUser.get();
    }

    User setBadges(User user) {
            user.setGoldShareable(3);
            user.setSilverShareable(2);
            user.setBronzeShareable(1);
        return user;
    }

    public Integer calculatePoints(User user) {
        Integer points;
        points = user.getGoldRedeemable() * 30
                + user.getSilverRedeemable() * 20
                + user.getBronzeRedeemable() * 10;
        return points;
    }

    public User getUserByFullName(String fullName) {
        return userRepository.findByFullName(fullName);
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPasswordAndActive(email, password, true);
    }

    public List<User> findUserByFullNamePattern(String fullNamePattern) {
        return userRepository.findByFullNameLike(fullNamePattern);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    public void updateUser(User user) {
        System.out.println("Updated User:"+user);
        userRepository.save(user);
    }

    // Update user's badges and points based on admin's modifications
    public void adminEditUser(User user) {
        User userToSave = setBadges(user);
        userToSave.setPoints(calculatePoints(userToSave));
        userRepository.save(userToSave);
    }

    public List<String> findAllEmails() {
        return userRepository.findAllEmail();
    }

    // Update user's roles based on admin's modifications
    public Set<Role> roleModifier(Set<Role> roleSet, String value, Role role) {
        if (value == null) {
            roleSet.remove(role);
        } else roleSet.add(role);
        return roleSet;
    }

    // Update recognition receiver's badges on revocation of recognition
    public void revokeReceivingUserBadge(User user, String badge) {
        if (badge.equals("gold")) {
            user.setGoldRedeemable(user.getGoldRedeemable() - 1);
        } else if (badge.equals("silver")) {
            user.setSilverRedeemable(user.getSilverRedeemable() - 1);
        } else {
            user.setBronzeRedeemable(user.getBronzeRedeemable() - 1);
        }
        user.setPoints(calculatePoints(user));
        userRepository.save(user);
    }

    // Update recognition sender's badges on revocation of recognition
    public void updateSendingUserBadge(User user, String badge) {
        if (badge.equals("gold")) {
            user.setGoldShareable(user.getGoldShareable() + 1);
        } else if (badge.equals("silver")) {
            user.setSilverShareable(user.getSilverShareable() + 1);
        } else {
            user.setBronzeShareable(user.getBronzeShareable() + 1);
        }
        user.setPoints(calculatePoints(user));
        userRepository.save(user);
    }

    // Deduct user's points when cart is checked out
    public void deductPointsOnCheckout(User user, Integer deductiblePoints) {
        Integer currentPoints = user.getPoints();
        user.setPoints(currentPoints - deductiblePoints);
        userRepository.save(user);
    }

    public List<User> getTopThreeUsers() {
        return userRepository.findTop3ByRoleSetContainingOrderByPointsDesc(Role.USER);
    }
}
