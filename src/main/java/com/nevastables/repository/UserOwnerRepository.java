package com.nevastables.repository;

import com.nevastables.domain.UserOwner;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link UserOwner} entity.
 */
@Repository
public interface UserOwnerRepository extends JpaRepository<UserOwner, Long> {

    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<UserOwner> findOneByActivationKey(String activationKey);

    List<UserOwner> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    Optional<UserOwner> findOneByResetKey(String resetKey);

    Optional<UserOwner> findOneByEmailIgnoreCase(String email);

    Optional<UserOwner> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<UserOwner> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<UserOwner> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<UserOwner> findAllByLoginNot(Pageable pageable, String login);
}
