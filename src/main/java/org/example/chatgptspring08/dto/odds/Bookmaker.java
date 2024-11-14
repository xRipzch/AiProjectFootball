
package org.example.chatgptspring08.dto.odds;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "title",
    "last_update",
    "link",
    "markets"
})
@Generated("jsonschema2pojo")
public class Bookmaker {

    @JsonProperty("key")
    private String key;
    @JsonProperty("title")
    private String title;
    @JsonProperty("last_update")
    private String lastUpdate;
    @JsonProperty("link")
    private String link;
    @JsonProperty("markets")
    private List<Market> markets;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("last_update")
    public String getLastUpdate() {
        return lastUpdate;
    }

    @JsonProperty("last_update")
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
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
