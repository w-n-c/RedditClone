package codes.newell.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codes.newell.dto.VoteDto;
import codes.newell.exceptions.PostNotFoundException;
import codes.newell.exceptions.SpringRedditException;
import codes.newell.model.Post;
import codes.newell.model.Vote;
import codes.newell.repository.PostRepository;
import codes.newell.repository.VoteRepository;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class VoteService {

	private final VoteRepository vr;
	private final PostRepository pr;
	private final AuthService as;

	@Transactional
	public void vote(VoteDto dto) {
		Post post = pr.findById(dto.getPostId())
				.orElseThrow(() -> new PostNotFoundException("Post not found with ID: " + dto.getPostId()));
		Optional<Vote> vote = vr.findTopByPostAndUserOrderByIdDesc(post, as.getCurrentUser());
		if (vote.isPresent() && vote.get().getVoteType().equals(dto.getVoteType()))
			throw new SpringRedditException("You have already " + dto.getVoteType() +"ed this post");
		else if (vote.isPresent()) // if exists and not the same, remove the vote and continue
			post.setVoteCount(post.getVoteCount() - vote.get().getVoteType().getDirection());

		post.setVoteCount(post.getVoteCount() + dto.getVoteType().getDirection());
		vr.save(map(dto, post));
		pr.save(post);
	}

	private Vote map(VoteDto dto, Post post) {
		return Vote.builder()
				.voteType(dto.getVoteType())
				.post(post)
				.user(as.getCurrentUser())
				.build();
	}

}
