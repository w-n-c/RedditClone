package codes.newell.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import codes.newell.dto.CommentDto;
import codes.newell.exceptions.PostNotFoundException;
import codes.newell.mapper.CommentMapper;
import codes.newell.model.Comment;
import codes.newell.model.NotificationEmail;
import codes.newell.model.Post;
import codes.newell.model.User;
import codes.newell.repository.CommentRepository;
import codes.newell.repository.PostRepository;
import codes.newell.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {

	private static final String POST_URL = "http://localhost:8080/post/";

	private final CommentMapper cm;
	private final CommentRepository cr;
	private final PostRepository pr;
	private final UserRepository ur;
	private final AuthService as;

	private final MailContentBuilder mb;
	private final MailService ms;


	public void save(CommentDto dto) {
		Post post = pr.findById(dto.getPostId())
				.orElseThrow(() -> new PostNotFoundException("Could not find post with ID: " + dto.getPostId()));
		Comment comment = cm.map(dto, post, as.getCurrentUser());
		cr.save(comment);

		String message = mb.build(post.getUser().getUsername() + " commented on your post. " + POST_URL + post.getId());
		sendCommentNotification(message, post.getUser());
	}


	private void sendCommentNotification(String message, User user) {
		ms.sendMail(new NotificationEmail(user.getUsername() + " commented on your post", user.getEmail(), message));
	}

	public List<CommentDto> getAllCommentsForPost(Long postId) {
		Post post = pr.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("Could not find post with ID: " + postId));
		return mapList(cr.findByPost(post));

	}

	public List<CommentDto> getAllCommentsForUser(String username) {
		User user = ur.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return mapList(cr.findByUser(user));
	}

	private List<CommentDto> mapList(List<Comment> comments) {
		return comments.stream().map(cm::map).collect(toList());
	}
}
