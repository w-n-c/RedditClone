package codes.newell.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codes.newell.dto.CommentDto;
import codes.newell.service.CommentService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

	private final CommentService cs;

	@PostMapping
	public ResponseEntity<Void> createComment(@RequestBody CommentDto comment) {
		cs.save(comment);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/by-post/{postId}")
	public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long postId) {
		return ResponseEntity.status(OK).body(cs.getAllCommentsForPost(postId));
	}

	@GetMapping("/by-user/{username}")
	public ResponseEntity<List<CommentDto>> getAllCommentsForUser(@PathVariable String username) {
		return ResponseEntity.status(OK).body(cs.getAllCommentsForUser(username));
	}
}
