package codes.newell.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import codes.newell.dto.SubredditDto;
import codes.newell.model.Post;
import codes.newell.model.Subreddit;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
	@Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
	SubredditDto mapSubredditToDto(Subreddit subreddit);

	default Integer mapPosts(List<Post> posts) {
		return posts.size();
	}

	@InheritInverseConfiguration
	@Mapping(target = "posts", ignore = true)
	Subreddit mapDtoToSubreddit(SubredditDto dto);
}
