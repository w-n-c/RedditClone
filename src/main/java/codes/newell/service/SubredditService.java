package codes.newell.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codes.newell.dto.SubredditDto;
import codes.newell.model.Subreddit;
import codes.newell.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {
	
	private final SubredditRepository sr;
	
	@Transactional
	public SubredditDto save(SubredditDto dto) {
		Subreddit subreddit = sr.save(mapSubredditDto(dto));
		dto.setId(subreddit.getId());
		return dto;
	}

	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		return sr.findAll()
			.stream()
			.map(this::mapToDto)
			.collect(toList());
	}
	
	private SubredditDto mapToDto(Subreddit s) {
		return SubredditDto.builder()
			.id(s.getId())
			.numberOfPosts(s.getPosts().size())
			.build();
	}

	private Subreddit mapSubredditDto(SubredditDto dto) {
		return Subreddit.builder()
			.name(dto.getName())
			.description(dto.getDescription())
			.build();
	}
}
