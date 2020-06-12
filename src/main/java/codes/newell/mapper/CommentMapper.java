package codes.newell.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import codes.newell.dto.CommentDto;
import codes.newell.model.Comment;
import codes.newell.model.Post;
import codes.newell.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "text", source = "dto.text")
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "user", source = "user") // not optional (otherwise uses post user)
	Comment map(CommentDto dto, Post post, User user);

	@Mapping(target = "postId", expression = "java(comment.getPost().getId())")
	@Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
	CommentDto map(Comment comment);
}
