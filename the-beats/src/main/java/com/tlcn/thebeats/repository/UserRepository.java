package com.tlcn.thebeats.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tlcn.thebeats.models.Song;
import com.tlcn.thebeats.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	@Modifying
    @Transactional
	@Query(value = "INSERT INTO user_song (user_id, song_id) VALUES (:userId,:songId)", nativeQuery = true)
	public void saveSong(long userId, long songId);
	
	public User findByCode(String code);

	User findByPasswordAndUsername(String encryptPass, String username);
	
	@Query(value = "Select roles.name from roles, user_roles where user_roles.user_id=:userId and user_roles.role_id= roles.id",nativeQuery = true)
	public List<String> findRolesOfUser(long userId);
	
	
}
