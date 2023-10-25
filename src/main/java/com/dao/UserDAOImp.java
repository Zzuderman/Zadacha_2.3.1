package com.dao;

import com.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImp implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u",User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {

        if (user.getId() == 0) {
            entityManager.persist(user);
        }
        entityManager.merge(user);
    }

    @Override
    public User getUserId(int id)  {
        return entityManager.find(User.class, id);
    }

    @Override
    public void deleteUser(int id) {
        entityManager.remove(entityManager.find(User.class, id));
    }


}
