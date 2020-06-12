package codes.newell.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codes.newell.dto.PostRequest;
import codes.newell.dto.PostResponse;
import codes.newell.exceptions.PostNotFoundException;
import codes.newell.exceptions.SubredditNotFoundException;
import codes.newell.mapper.PostMapper;
import codes.newell.model.Post;
import codes.newell.model.Subreddit;
import codes.newell.model.User;
import codes.newell.repository.PostRepository;
import codes.newell.repository.SubredditRepository;
import codes.newell.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {
	
	private final PostRepository pr;
	private final SubredditRepository sr;
	private final AuthService as;
	private final PostMapper pm;
	private final UserRepository ur;
	
	@Transactional
	public Post save(PostRequest postRequest) {
		Subreddit subreddit = sr.findByName(postRequest.getSubredditName())
			.orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
		User user = as.getCurrentUser();
		return pr.save(pm.map(postRequest, subreddit, user));
	}
	
	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
        Post post = pr.findById(id)
        	.orElseThrow(() -> new PostNotFoundException(id.toString()));
        return pm.mapToDto(post);
	}
	
	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts() {
		return pr.findAll()
			.stream()
			.map(pm::mapToDto)
			.collect(toList());
	}
	
	@Transactional(readOnly = true)
	public List<PostResponse> getPostsBySubreddit(Long id) {
        Subreddit subreddit = sr.findById(id)
        	.orElseThrow(() -> new SubredditNotFoundException(id.toString()));
        List<Post> posts = pr.findAllBySubreddit(subreddit);
        return posts
        	.stream()
        	.map(pm::mapToDto)
        	.collect(toList());
	}
	
	@Transactional(readOnly = true)
	public List<PostResponse> getPostsByUsername(String username) {
        User user = ur.findByUsername(username)
        	.orElseThrow(() -> new UsernameNotFoundException(username));
        return pr.findByUser(user)
        	.stream()
        	.map(pm::mapToDto)
        	.collect(toList());
	}

}
