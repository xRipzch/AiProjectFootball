package org.example.chatgptspring08.model;

import java.time.LocalDateTime;
import java.util.List;

public class Match {
    private int id;
    private LocalDateTime dateTime;
    private String referee;
    private List<Team> teams;
    private String score;


}