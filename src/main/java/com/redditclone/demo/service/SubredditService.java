package com.redditclone.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redditclone.demo.dto.SubredditDto;
import com.redditclone.demo.model.Subreddit;
import com.redditclone.demo.repository.SubredditRepository;

import lombok.AllArgsConstructor;

/**
 * SubredditService which provides service methods to perform all kind of
 * operations on subreddits such as creation and fetch.
 */
@Service
@AllArgsConstructor
public class SubredditService {

	/**
	 * The subreddit repository which is used to perform CRUD operations on data
	 * from the subreddit table in the database.
	 */
	private final SubredditRepository subredditRepository;

	/**
	 * createandSaveSubreddit method create and save subreddit information into
	 * subreddit table in the database.It returns the the created subreddit info if
	 * creation is successful.
	 *
	 * @param subredditDto the subreddit dto information which needs to be saved.
	 * @return the subreddit dto after saved into the table.
	 */
	@Transactional
	public SubredditDto createandSaveSubreddit(SubredditDto subredditDto) {
		Subreddit subreddit = SubredditDto.formSubRedditFromDto(subredditDto);
		Subreddit subredditAfterSave = subredditRepository.save(subreddit);
		subredditDto.setId(subredditAfterSave.getId());
		return subredditDto;
	}

	/**
	 * getAllSubreddits method fetches and returns all the subreddits from the
	 * subreddit table.
	 *
	 * @return List<SubredditDto> the all subreddits from the subreddit table.
	 */
	@Transactional(readOnly = true)
	public List<SubredditDto> getAllSubreddits() {
		return subredditRepository.findAll().stream().map(SubredditDto::formSubRedditDto).collect(Collectors.toList());
	}

}
