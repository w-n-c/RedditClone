package codes.newell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import codes.newell.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
