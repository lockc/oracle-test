package lockc.oracle.test.vendor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Chris
 */
public class DepositFloatRequest {
    private Coins coins;

    @JsonCreator
    public DepositFloatRequest(@JsonProperty("coins") Coins coins) {
        this.coins = coins;
    }

    public Coins getCoins() {
        return coins;
    }
}
