package codes.newell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import codes.newell.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

}
