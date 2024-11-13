
package org.example.chatgptspring08.dto.odds;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "title",
    "last_update",
    "markets"
})
@Generated("jsonschema2pojo")
public class Bookmaker {

    @JsonProperty("title")
    private String title;
    @JsonProperty("last_update")
    private String last_update;
    @JsonProperty("markets")
    private List<Market> markets;

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("last_update")
    public String getLast_update() {
        return last_update;
    }

    @JsonProperty("last_update")
    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    @JsonProperty("markets")
    public List<Market> getMarkets() {
        return markets;
    }

    @JsonProperty("markets")
    public void setMarkets(List<Market> markets) {
        this.markets = markets;
    }
}
