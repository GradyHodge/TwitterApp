package com.tts.MockingBirdApp.model;

import java.util.List;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TweetDisplay {
	private User user;
	private String message;
	private String date;
	private List<Tag> tags;

	// debugger suggestion, setUser not working in TweetService
	public void setUser(com.tts.MockingBirdApp.model.User user2) {
		// TODO Auto-generated method stub

	}
}
