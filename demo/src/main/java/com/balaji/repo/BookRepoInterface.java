package com.balaji.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.balaji.Model.BookModel;

public interface BookRepoInterface extends MongoRepository<BookModel, String>{

	Optional<BookModel> findByUserId();

}