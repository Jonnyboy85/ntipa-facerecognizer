package com.ipublic.ntipa.facerecognizer.repository;

import com.ipublic.ntipa.facerecognizer.domain.PersistentToken;
import com.ipublic.ntipa.facerecognizer.domain.User;
import org.joda.time.LocalDate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the PersistentToken entity.
 */
public interface PersistentTokenRepository extends MongoRepository<PersistentToken, String> {

    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);

}
