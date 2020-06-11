package codes.newell.service;

import java.util.List;

import org.springframework.stereotype.Service;

import codes.newell.dto.PostRequest;
import codes.newell.dto.PostResponse;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

	public List<PostResponse> getAllPosts() {
		return null;
	}

	public List<PostResponse> getPostsBySubreddit(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PostResponse> getPostsByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(PostRequest postRequest) {
		// TODO Auto-generated method stub
		
	}

	public PostResponse getPost(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
