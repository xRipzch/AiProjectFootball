
package org.example.chatgptspring08.dto.odds;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "key",
    "last_update",
    "outcomes"
})
@Generated("jsonschema2pojo")
public class Market {
    @JsonProperty("last_update")
    private String last_update;
    @JsonProperty("outcomes")
    private List<Outcome> outcomes;

    @JsonProperty("last_update")
    public String getLast_update() {
        return last_update;
    }

    @JsonProperty("last_update")
    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    @JsonProperty("outcomes")
    public List<Outcome> getOutcomes() {
        return outcomes;
    }

    @JsonProperty("outcomes")
    public void setOutcomes(List<Outcome> outcomes) {
        this.outcomes = outcomes;
    }

}
