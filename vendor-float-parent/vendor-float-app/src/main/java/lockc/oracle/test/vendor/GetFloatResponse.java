package lockc.oracle.test.vendor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Chris
 */
public class GetFloatResponse {
    private Coins coins;

    @JsonCreator
    public GetFloatResponse(@JsonProperty("coins") Coins coins) {
        this.coins = coins;
    }

    public Coins getCoins() {
        return coins;
    }
}
