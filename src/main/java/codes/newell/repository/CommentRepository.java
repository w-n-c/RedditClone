package codes.newell.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import codes.newell.model.Comment;
import codes.newell.model.Post;
import codes.newell.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	List<Comment> findByPost(Post post);
	List<Comment> findByUser(User user);
}
