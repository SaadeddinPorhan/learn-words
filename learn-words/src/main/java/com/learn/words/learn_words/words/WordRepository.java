package com.learn.words.learn_words.words;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word,Integer>  {
	Optional<Word> findByWordAndLanguage(String word, String language);
}
