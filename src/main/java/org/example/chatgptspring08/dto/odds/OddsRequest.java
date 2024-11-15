package org.example.chatgptspring08.dto.odds;
import com.fasterxml.jackson.annotation.*;

public class OddsRequest {
    @JsonProperty("league")
    private String league;
    @JsonProperty("region")
    private String region;
    @JsonProperty("team")
    private String team;

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}

