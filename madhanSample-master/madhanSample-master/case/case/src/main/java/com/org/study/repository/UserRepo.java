package com.org.study.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.org.study.model.UserModel;

public interface UserRepo extends MongoRepository<UserModel, String> {

}
