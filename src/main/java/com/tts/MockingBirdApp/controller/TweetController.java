package com.tts.MockingBirdApp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tts.MockingBirdApp.model.Tweet;
import com.tts.MockingBirdApp.model.TweetDisplay;
import com.tts.MockingBirdApp.model.User;
import com.tts.MockingBirdApp.service.TweetService;
import com.tts.MockingBirdApp.service.UserService;

@Controller
public class TweetController {
	@Autowired
	private UserService userService;

	@Autowired
	private TweetService tweetService;

	@GetMapping({ "/tweets", "/" })
	public String getFeed(@RequestParam(value = "filter", required = false) String filter, Model model) {
		User loggedInUser = userService.getLoggedInUser();
		List<TweetDisplay> tweets = new ArrayList<>();
		// List<TweetDisplay> tweets = tweetService.findAll();
		if (filter == null) {
			filter = "all";
		}
		if (filter.equalsIgnoreCase("following")) {
			List<User> following = loggedInUser.getFollowing();
			tweets = tweetService.findAllByUsers(following);
			model.addAttribute("filter", "following");
		} else {
			tweets = tweetService.findAll();
			model.addAttribute("filter", "all");
		}
		model.addAttribute("tweetList", tweets);
		return "feed";
	}

	@GetMapping("/tweets/new")
	public String getTweetForm(Model model) {
		model.addAttribute("tweet", new Tweet());
		return "newTweet";
	}

	@GetMapping("/tweets/{tag}")
	public String getTweetsByTag(@PathVariable(value = "tag") String tag, Model model) {
		System.out.println("getsTweetByTag, this is tag: " + tag);
		List<TweetDisplay> tweets = tweetService.findAllWithTag(tag);
		model.addAttribute("tweetList", tweets);
		model.addAttribute("tag", tag);
		return "taggedTweets";
	}

	@PostMapping("/tweets")
	public String submitTweetForm(@Valid Tweet tweet, BindingResult bindingResult, Model model) {
		System.out.println("hashtag maybe?");
		User user = userService.getLoggedInUser();
		if (!bindingResult.hasErrors()) {
			tweet.setUser(user);
			System.out.println("this is user in if statement " + user);
			tweetService.save(tweet);
			System.out.println("this is user in if statement2 " + user);
			model.addAttribute("successMessage", "Tweet successfully created!");
			System.out.println("this is user in if statement3 " + user);
			model.addAttribute("tweet", new Tweet());
			System.out.println("this is user in if statement4 " + user);
		}
		System.out.println("hashy return");
		return "newTweet";
	}

}
