package org.example.repository;

import org.bson.types.ObjectId;
import org.example.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByEmail(String email);
}