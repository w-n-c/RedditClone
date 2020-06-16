package codes.newell.mapper;

import static codes.newell.model.VoteType.DOWNVOTE;
import static codes.newell.model.VoteType.UPVOTE;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.marlonlom.utilities.timeago.TimeAgo;

import codes.newell.dto.PostRequest;
import codes.newell.dto.PostResponse;
import codes.newell.model.Post;
import codes.newell.model.Subreddit;
import codes.newell.model.User;
import codes.newell.model.Vote;
import codes.newell.model.VoteType;
import codes.newell.repository.CommentRepository;
import codes.newell.repository.VoteRepository;
import codes.newell.service.AuthService;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

	@Autowired
	private CommentRepository cr;
	@Autowired
	private AuthService as;
	@Autowired
	private VoteRepository vr;

	@Mapping(target = "createdDate", expression= "java(java.time.Instant.now())")
	@Mapping(target = "description", source = "postRequest.description")
	@Mapping(target = "id", source="postRequest.id")
	@Mapping(target = "user", source="user") // not optional, otherwise uses subreddit user (possible bug in mapstruct)
	@Mapping(target = "voteCount", constant = "0")
	public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);


	@Mapping(target = "subredditName", source = "subreddit.name")
	@Mapping(target = "username", source = "user.username")
	@Mapping(target = "commentCount", expression = "java(commentCount(post))")
	@Mapping(target = "duration", expression = "java(getDuration(post))")
	@Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
	@Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
	public abstract PostResponse mapToDto(Post post);

	Integer commentCount(Post post) {
		return cr.findByPost(post).size();
	}

	String getDuration(Post post) {
		return TimeAgo.using(post.getCreatedDate().toEpochMilli());
	}

	@Autowired
	public void setCommentRepository(CommentRepository cr) {
		this.cr = cr;
	}

	boolean isPostUpVoted(Post post) { return checkVoteType(post, UPVOTE); }
	boolean isPostDownVoted(Post post) { return checkVoteType(post, DOWNVOTE); }
	boolean checkVoteType(Post post, VoteType voteType) {
		if (as.isLoggedIn()) {
			Optional<Vote> usersVote = vr.findTopByPostAndUserOrderByIdDesc(post, as.getCurrentUser());
			return usersVote.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
		}
		return false;
	}

}
