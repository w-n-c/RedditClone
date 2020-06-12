package codes.newell.controller;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codes.newell.dto.VoteDto;
import codes.newell.service.VoteService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {
	private final VoteService vs;

	@PostMapping
	public ResponseEntity<Void> vote(@RequestBody VoteDto dto) {
		System.out.println(dto.toString());
		vs.vote(dto);
		return new ResponseEntity<>(OK);
	}
}
