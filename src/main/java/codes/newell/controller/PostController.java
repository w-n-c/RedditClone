package codes.newell.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@GetMapping("{id}")
	public PostResponse getPost(Long id) {
		return ps.getPost(id);
	}

	@GetMapping
	public List<PostResponse> getAllPosts() {
		return ps.getAllPosts();
	}
	
	@GetMapping("by-subreddit/{id}")
	public List<PostResponse> getPostsBySubreddit(Long id) {
		return ps.getPostsBySubreddit(id);
	}
	
	@GetMapping("by-user/{name}")
	public List<PostResponse> getPostsByUsername(String username) {
		return ps.getPostsByUsername(username);
	}
}
