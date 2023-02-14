package data.repositories.userRepo;

import data.models.User;

import java.util.List;

public interface UserRepository {
    User save(User user);
    User findById(int id);
    long count();
    List <User> findAll();
    void delete(User user);
    void delete(int id);

    void deleteAll();
}
