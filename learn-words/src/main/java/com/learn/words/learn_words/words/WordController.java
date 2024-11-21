package com.learn.words.learn_words.words;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class WordController {
	
	List<Word> wordList = new ArrayList<Word>();
	
	private final WordService wordService;
	
	@Autowired
	public WordController(WordService wordService) {
		this.wordService = wordService;
	}
	
	@RequestMapping("/words/")
	public List<Word> getWords(){
		return wordService.getAllWords();
		
	}
	
	@RequestMapping( value="/words/", method = RequestMethod.POST)
	public void addWord(@RequestBody Word word){
		wordService.addWord(word);
		
	}
	
	@RequestMapping( value="/words/", method = RequestMethod.DELETE)
	public void deleteWord(@RequestParam(name="id") String id){
		wordService.deleteWord(id);
		
	}
	
	@RequestMapping( value="/words/", method = RequestMethod.PATCH)
	public void updateWord(@RequestParam(name="id") String id, @RequestBody Word word){
		wordService.updateWord(id,word);
		
	}
	@RequestMapping( value="/words/", method = RequestMethod.PUT)
	public void updateWordPut(@RequestBody Word word){
		wordService.updateWordPut(word);
		
	}
}
