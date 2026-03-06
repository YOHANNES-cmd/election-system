package com.election.election_system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.election.election_system.entity.User;
import com.election.election_system.entity.Vote;
import com.election.election_system.repository.UserRepository;
import com.election.election_system.repository.VoteRepository;

@RestController
@RequestMapping("/user")
public class UserController {

     private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    public UserController(VoteRepository voteRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }
    @PostMapping("/submit-vote")
public Map<String, Object> submitVote(@RequestBody Vote voteRequest,
                                      @RequestParam String password) {

    // 1️⃣ Check user
    Optional<User> userOpt = userRepository.findByUsername(voteRequest.getUsername());
    if (userOpt.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found!");
    }

    User user = userOpt.get();
    if (!user.getPassword().trim().equals(password.trim())) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password!");
    }

    // 2️⃣ Determine vote count
    int count = voteRequest.getCount() <= 0 ? 1 : voteRequest.getCount();

    // 3️⃣ Save vote
    Vote vote = new Vote();
    vote.setUsername(user.getUsername());
    vote.setSite(voteRequest.getSite().trim().toUpperCase());
    vote.setCount(count);
    voteRepository.save(vote);

    // 4️⃣ Get total votes & per-site votes
    int totalVotes = voteRepository.getTotalVotesByUsername(user.getUsername());
    List<Object[]> votesPerSite = voteRepository.getTotalVotesByUserPerSite(user.getUsername());

    Map<String, Long> siteVotesMap = new HashMap<>();
    for (Object[] row : votesPerSite) {
        String site = (String) row[0];
        Long total = (Long) row[1];
        siteVotesMap.put(site, total);
    }

    // 5️⃣ Build response
    Map<String, Object> response = new HashMap<>();
    response.put("message", "Vote submitted successfully!");
    response.put("totalSubmitted", totalVotes);
    response.put("votesPerSite", siteVotesMap);

    return response;
}
/*latst@PostMapping("/submit-vote")
public Map<String, Object> submitVote(@RequestBody Vote voteRequest,
                                      @RequestParam String password) {

    Optional<User> userOpt = userRepository.findByUsername(voteRequest.getUsername());
    if (userOpt.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found!");
    }

    User user = userOpt.get();
    if (!user.getPassword().trim().equals(password.trim())) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password!");
    }

    int count = voteRequest.getCount() <= 0 ? 1 : voteRequest.getCount();

    Vote vote = new Vote();
    vote.setUsername(user.getUsername());
    vote.setSite(voteRequest.getSite().trim().toUpperCase());
    vote.setCount(count);

    voteRepository.save(vote);

    // ✅ Get votes per site for this user
    List<Object[]> votesPerSite = voteRepository.getTotalVotesByUserPerSite(user.getUsername());
    Map<String, Long> siteVotesMap = new HashMap<>();
    for (Object[] row : votesPerSite) {
        String site = (String) row[0];
        Long total = (Long) row[1];
        siteVotesMap.put(site, total);
    }

    Map<String, Object> response = new HashMap<>();
    response.put("message", "Vote submitted successfully!");
    response.put("totalSubmitted", siteVotesMap); // now shows per site

    return response;
}*/
/*@PostMapping("/submit-vote")
public Map<String, Object> submitVote(@RequestBody Vote voteRequest,
                                      @RequestParam String password) {

    // 1️⃣ Find the user
    Optional<User> userOpt = userRepository.findByUsername(voteRequest.getUsername());
    if (userOpt.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found!");
    }

    User user = userOpt.get();

    // 2️⃣ Check password
    if (!user.getPassword().trim().equals(password.trim())) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password!");
    }

    // 3️⃣ Set default vote count if not provided
    int count = voteRequest.getCount() <= 0 ? 1 : voteRequest.getCount();

    // 4️⃣ Create and save vote
    Vote vote = new Vote();
    vote.setUsername(voteRequest.getUsername());
    vote.setSite(voteRequest.getSite().trim().toUpperCase());
    vote.setCount(count);
    voteRepository.save(vote);

    // 5️⃣ Get total votes submitted by this user
    long totalUserVotes = voteRepository.getTotalVotesByUsername(vote.getUsername());

    // 6️⃣ Prepare response
    Map<String, Object> response = new HashMap<>();
    response.put("message", "Vote submitted successfully!");
    response.put("totalSubmitted", totalUserVotes); // ✅ Total votes for this user

    return response;
}*/
   /* @PostMapping("/submit-vote")
    public String submitVote(@RequestBody Vote voteRequest) {

        // 1️⃣ Find user by username
        Optional<User> userOpt = userRepository.findByUsername(voteRequest.getUsername());
        if (userOpt.isEmpty()) {
            return "Unauthorized: User not found!";
        }

        User user = userOpt.get();

        // 2️⃣ Check password
        if (!user.getPassword().equals(voteRequest.getPassword())) {
            return "Unauthorized: Invalid password!";
        }

        // 3️⃣ Default vote count
        int count = voteRequest.getCount() <= 0 ? 1 : voteRequest.getCount();

        // 4️⃣ Create and save vote
        Vote vote = new Vote();
        vote.setUsername(voteRequest.getUsername());
        vote.setSite(voteRequest.getSite());
        vote.setCount(count);

        voteRepository.save(vote);

        return "Vote submitted successfully!";
    }*/
}