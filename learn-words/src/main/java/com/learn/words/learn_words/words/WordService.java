package com.learn.words.learn_words.words;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.learn.words.learn_words.util.Utillity.DATE_TIME_FORMATTER;;
@Service
public class WordService {
	

	private final WordRepository wordRepository;
	
	@Autowired
	public WordService(WordRepository wordRepository) {
		this.wordRepository= wordRepository;
		// TODO Auto-generated constructor stub
	}

	public List<Word> getAllWords(){
		List<Word> words = new ArrayList<>();
		wordRepository.findAll().forEach(words::add);
		return words;
	}
	
	public void addWord(Word word){
		Optional<Word> existingWordOptional = wordRepository.findByWordAndLanguage(word.getWord(), word.getLanguage());
		
		if(!existingWordOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Word: " + word.getWord()+
            		" is already exist for language: " + word.getLanguage());
		}
		
		word.setCreatedAt(word.getCreatedAt()== null ? LocalDateTime.now().format(DATE_TIME_FORMATTER):word.getCreatedAt());
		word.setLastUpdated(word.getLastUpdated()== null ? LocalDateTime.now().format(DATE_TIME_FORMATTER):word.getLastUpdated());
		wordRepository.save(word);
	}
	
	public void deleteWord(String id){
		wordRepository.deleteById(Integer.parseInt(id));
	}
	public void updateWord(String id, Word word){
		Optional<Word> existingWordOptional = wordRepository.findById(Integer.parseInt(id));

        if (existingWordOptional.isEmpty()) {
            throw new RuntimeException("Word not found with id: " + id);
        }

        Word existingWord = existingWordOptional.get();
        // Update fields
        existingWord.setWord(word.getWord());
        existingWord.setMeaning(word.getMeaning());
        existingWord.setLastUpdated(LocalDateTime.now().format(DATE_TIME_FORMATTER));
        existingWord.setLanguage(word.getLanguage());

        // Save updated entity
        wordRepository.save(existingWord);

	}
	
	public void updateWordPut(Word word){
		Optional<Word> existingWordOptional = wordRepository.findById(word.getId());

        if (existingWordOptional.isEmpty()) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Word not found with id: " + word.getId());
        }
       
		word.setLastUpdated(LocalDateTime.now().format(DATE_TIME_FORMATTER));
		wordRepository.save(word);
	}

}
