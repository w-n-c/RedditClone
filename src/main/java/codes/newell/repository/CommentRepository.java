package codes.newell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import codes.newell.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

}
