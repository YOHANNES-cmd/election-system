package com.election.election_system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.election.election_system.entity.User;
import com.election.election_system.entity.Vote;
import com.election.election_system.repository.UserRepository;
import com.election.election_system.repository.VoteRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final VoteRepository voteRepository;  // <-- Add this

    // Inject both repositories via constructor
    public AdminController(UserRepository userRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }
@GetMapping("/dashboard")
public Map<String,Object> dashboard(){

    Map<String,Object> data = new HashMap<>();

    long totalUsers = userRepository.count();

    int totalVoters = 0;
    Map<String,Integer> siteVotes = new HashMap<>();

    List<Vote> votes = voteRepository.findAll();

    for(Vote v : votes){

        totalVoters += v.getCount();

        siteVotes.put(
            v.getSite(),
            siteVotes.getOrDefault(v.getSite(),0) + v.getCount()
        );
    }

    data.put("totalUsers", totalUsers);
    data.put("totalVoters", totalVoters);
    data.put("sites", siteVotes);

    return data;
}
   @PostMapping("/create-user")
public Map<String,String> createUser(@RequestBody Map<String,String> data){

    String username = data.get("username");
    String password = data.get("password");
    String role = data.get("role");

    Map<String,String> response = new HashMap<>();

    if(username == null || password == null){
        response.put("message","Missing username or password");
        return response;
    }

    if(userRepository.existsByUsername(username)){
        response.put("message","User already exists!");
        return response;
    }

    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    user.setRole(role);
    userRepository.save(user);
    response.put("message","User created successfully");
    return response;
}

    // List all users
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
/*@GetMapping("/admin/dashboard")
public Map<String, Object> getDashboard() {

    Map<String, Object> dashboard = new HashMap<>();

    long totalUsers = userRepository.count();
    long totalVoters = voteRepository.count();

    List<Vote> votes = voteRepository.findAll();

    Map<String, Integer> siteCounts = new HashMap<>();

    for (Vote v : votes) {
        siteCounts.put(
            v.getSite(),
            siteCounts.getOrDefault(v.getSite(), 0) + v.getCount()
        );
    }

    dashboard.put("totalUsers", totalUsers);
    dashboard.put("totalVoters", totalVoters);
    dashboard.put("sites", siteCounts);

    return dashboard;
}*/

    // Votes per user
    @GetMapping("/votes-per-user")
    public List<Object[]> getVotesPerUser() {
        return voteRepository.getTotalVotesPerUser();
    }
}