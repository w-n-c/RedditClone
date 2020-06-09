package codes.newell.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codes.newell.dto.SubredditDto;
import codes.newell.exceptions.SpringRedditException;
import codes.newell.mapper.SubredditMapper;
import codes.newell.model.Subreddit;
import codes.newell.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {
	
	private final SubredditRepository sr;
	private final SubredditMapper sm;
	
	@Transactional
	public SubredditDto save(SubredditDto dto) {
		Subreddit subreddit = sr.save(sm.mapDtoToSubreddit(dto));
		dto.setId(subreddit.getId());
		return dto;
	}

	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		return sr.findAll()
			.stream()
			.map(sm::mapSubredditToDto)
			.collect(toList());
	}

	public SubredditDto getSubreddit(Long id) {
		Subreddit s = sr.findById(id)
				.orElseThrow(() -> new SpringRedditException("No subreddit found with ID " + id));
		return sm.mapSubredditToDto(s);
	}
}
