package com.learn.words.learn_words.words;

import java.time.LocalDateTime;
import static com.learn.words.learn_words.util.Utillity.DATE_TIME_FORMATTER;

import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Word {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String word;
	private String meaning;
	private String language;
	private String createdAt;
	private String lastUpdated;
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getMeaning() {
		return meaning;
	}
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public int getId() {
		return id;
	}
	public Word(int id, String word, String meaning, String language, String createdAt, String lastUpdated) {
		super();
		
		this.id = id;
		this.word = word;
		this.meaning = meaning;
		this.language = language;
		this.createdAt = createdAt == null ? LocalDateTime.now().format(DATE_TIME_FORMATTER): createdAt ;
		this.lastUpdated = lastUpdated  == null ? LocalDateTime.now().format(DATE_TIME_FORMATTER): lastUpdated;
	}
	public Word() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Word(int id, String word, String meaning, String language) {
		super();
		this.id = id;
		this.word = word;
		this.meaning = meaning;
		this.language = language;
        // Define the desired format
        // Format the current date and time
        this.createdAt = LocalDateTime.now().format(DATE_TIME_FORMATTER);
		this.lastUpdated = LocalDateTime.now().format(DATE_TIME_FORMATTER);
	}
	
	

}
