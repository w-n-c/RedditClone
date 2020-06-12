package codes.newell.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.marlonlom.utilities.timeago.TimeAgo;

import codes.newell.dto.PostRequest;
import codes.newell.dto.PostResponse;
import codes.newell.model.Post;
import codes.newell.model.Subreddit;
import codes.newell.model.User;
import codes.newell.repository.CommentRepository;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

	private CommentRepository cr;
	//	private final VoteRepository vr;
	//	private final AuthService as;

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

}
