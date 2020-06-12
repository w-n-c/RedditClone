package codes.newell.controller;

import static org.springframework.http.ResponseEntity.status;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codes.newell.dto.PostRequest;
import codes.newell.dto.PostResponse;
import codes.newell.service.PostService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
	
	private final PostService ps;
	
	@PostMapping
	public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
		ps.save(postRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
		return status(HttpStatus.OK).body(ps.getPost(id));
	}

	@GetMapping
	public ResponseEntity<List<PostResponse>> getAllPosts() {
		return status(HttpStatus.OK).body(ps.getAllPosts());
	}
	
	@GetMapping("by-subreddit/{id}")
	public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable Long id) {
		return status(HttpStatus.OK).body(ps.getPostsBySubreddit(id));
	}
	
	@GetMapping("by-user/{name}")
	public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username) {
		return status(HttpStatus.OK).body(ps.getPostsByUsername(username));
	}
}
