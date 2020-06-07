package codes.newell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import codes.newell.model.Subreddit;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long>{

}
