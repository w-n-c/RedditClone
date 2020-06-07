package codes.newell.model;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vote {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	private VoteType voteType;

	@NotNull
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "postId")
	private Post post;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "userId")
	private User user;
}
