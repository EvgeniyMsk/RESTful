package edu.school21.restful.repository;

import edu.school21.restful.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(Long id);
    User getUserByUsername(String username);
    void deleteUsersById(Long id);
}
