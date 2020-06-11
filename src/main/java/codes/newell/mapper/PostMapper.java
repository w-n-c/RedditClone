package codes.newell.mapper;

import org.mapstruct.Mapper;

import codes.newell.dto.PostRequest;
import codes.newell.model.Post;
import codes.newell.model.Subreddit;

@Mapper(componentModel = "spring")
public interface PostMapper {
	@Mapping(target = "create", expression= "")
	Post map(PostRequest postRequest, Subreddit subreddit, User user);
}
