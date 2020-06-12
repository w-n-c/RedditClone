package codes.newell.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import codes.newell.dto.PostRequest;
import codes.newell.dto.PostResponse;
import codes.newell.model.Post;
import codes.newell.model.Subreddit;
import codes.newell.model.User;

@Mapper(componentModel = "spring")
public interface PostMapper {
	@Mapping(target = "createdDate", expression= "java(java.time.Instant.now())")
	@Mapping(target = "description", source = "postRequest.description")
	@Mapping(target = "id", source="postRequest.id")
	@Mapping(target = "subreddit", source="subreddit") // not optional (possible bug in mapstruct)
	@Mapping(target = "user", source="user") // not optional (possible bug in mapstruct)
	Post map(PostRequest postRequest, Subreddit subreddit, User user);
	

	@Mapping(target = "subredditName", source = "subreddit.name")
	@Mapping(target = "username", source = "user.username")
	PostResponse mapToDto(Post post);
	
	
}
