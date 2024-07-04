package com.prajna.mentor_extension.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.prajna.mentor_extension.Entity.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    @NonNull
    Optional<User> findById(@NonNull String id);

    @NonNull
    Optional<User> findByEmail(@NonNull String email);

    @NonNull
    Optional<User> findByName(@NonNull String name);

    @NonNull
    List<User> findAll();

}
