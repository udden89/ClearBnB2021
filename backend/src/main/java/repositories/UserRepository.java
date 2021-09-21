package repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import models.User;

import java.util.List;
import java.util.Optional;

public class UserRepository {
    private EntityManager entityManager;

    public UserRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public Optional<User> findById(Integer id){
        User user = entityManager.find(User.class, id);
        System.out.println(user);
        return user != null ? Optional.of(user) : Optional.empty();
    }

    public List<User> findByFullName(String firstName, String lastName){
        return entityManager.createQuery("SELECT u FROM User u WHERE u.firstName = :firstName AND u.lastName = :lastName", User.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    public List<User> findByFullNameQuery(String firstName, String lastName){
        return entityManager.createNamedQuery("User.findByName", User.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    public List<User> findAllUsers(){
        return entityManager.createQuery("from User").getResultList();
    }

    public Optional<User> addUser(User user){
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            return Optional.of(user);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return user != null ? Optional.of(user):Optional.empty();
    }


    public List<User> findAll(){
        return entityManager.createQuery("from User").getResultList();
    }

    public Optional<User> findByName(String name){
        User user = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class)
                .setParameter("name", name)
                .getSingleResult();
        return user != null ? Optional.of(user) : Optional.empty();
        }

    public Optional<User> findByNameNamedQuery(String name){
        User user = entityManager.createNamedQuery("User.findByName", User.class)
                .setParameter("name", name)
                .getSingleResult();
        return user !=null ? Optional.of(user) : Optional.empty();
    }

    public Optional<User> findByEmail(String email) {
        User user = null;
        try{
        user = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();}

        catch (NoResultException e){
        //Do nothing Dennis
        }
        return user != null ? Optional.of(user) : Optional.empty();
    }

    public User save(User user){
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public void updateName(String name, User user){
        

    }

}
