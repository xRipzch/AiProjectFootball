
package org.example.chatgptspring08.dto.odds;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "sport_key",
    "sport_title",
    "commence_time",
    "home_team",
    "away_team",
    "bookmakers"
})
@Generated("jsonschema2pojo")
public class OddsResponse {
    @JsonProperty("commence_time")
    private String commence_time;
    @JsonProperty("home_team")
    private String home_team;
    @JsonProperty("away_team")
    private String away_team;
    @JsonProperty("bookmakers")
    private List<Bookmaker> oddsRespons;

    @JsonProperty("commence_time")
    public String getCommence_time() {
        return commence_time;
    }

    @JsonProperty("commence_time")
    public void setCommence_time(String commence_time) {
        this.commence_time = commence_time;
    }

    @JsonProperty("home_team")
    public String getHome_team() {
        return home_team;
    }

    @JsonProperty("home_team")
    public void setHome_team(String home_team) {
        this.home_team = home_team;
    }

    @JsonProperty("away_team")
    public String getAway_team() {
        return away_team;
    }

    @JsonProperty("away_team")
    public void setAway_team(String away_team) {
        this.away_team = away_team;
    }

    @JsonProperty("bookmakers")
    public List<Bookmaker> getBookmakers() {
        return oddsRespons;
    }



    @JsonProperty("bookmakers")
    public void setBookmakers(List<Bookmaker> oddsRespons) {
        this.oddsRespons = oddsRespons;
    }



    @Override
    public String toString() {
        return "OddsResponse{" +
                "commence_time='" + commence_time + '\'' +
                ", home_team='" + home_team + '\'' +
                ", away_team='" + away_team + '\'' +
                ", oddsRespons=" + oddsRespons +
                '}';
    }
}
