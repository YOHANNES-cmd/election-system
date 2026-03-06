package com.election.election_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.election.election_system.entity.Vote;

/*public interface VoteRepository extends JpaRepository<Vote, Long> {

    // Get all votes by a username
    List<Vote> findByUsername(String username);

    // Sum votes per username for all users
    @Query("SELECT v.username, SUM(v.count) FROM Vote v GROUP BY v.username")
    List<Object[]> getTotalVotesPerUser();

    // Get total votes by username
    @Query("SELECT COALESCE(SUM(v.count),0) FROM Vote v WHERE v.username = :username")
    int getTotalVotesByUsername(@Param("username") String username);
    @SuppressWarnings("override")
    long count(); // counts all votes
    long countBySite(String site); // optional: count per site
}*/

public interface VoteRepository extends JpaRepository<Vote, Long> {

  

    // Get total votes for a user
    @Query("SELECT COALESCE(SUM(v.count),0) FROM Vote v WHERE v.username = :username")
    int getTotalVotesByUsername(@Param("username") String username);

    // Get total votes per site for a user
    @Query("SELECT v.site, SUM(v.count) FROM Vote v WHERE v.username = :username GROUP BY v.site")
    List<Object[]> getTotalVotesByUserPerSite(@Param("username") String username);
    // 1️⃣ Get all votes by a specific username
    List<Vote> findByUsername(String username);

    // 2️⃣ Sum of votes per username (for all users)
    @Query("SELECT v.username, SUM(v.count) FROM Vote v GROUP BY v.username")
    List<Object[]> getTotalVotesPerUser();

   
    // 4️⃣ Total votes in the system
    @Override
    long count();

    // 5️⃣ Total votes per site
    long countBySite(String site);
}