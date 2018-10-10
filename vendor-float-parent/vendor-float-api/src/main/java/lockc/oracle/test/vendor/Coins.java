package lockc.oracle.test.vendor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A class that represents a collection of coins in various denominations and the number of each of them
 *
 * @author Chris
 */
public final class Coins {

    @JsonProperty("denominations")
    private final Map<CoinDenomination, Integer> denominations;

    public Coins() {
        this.denominations = new HashMap<>();
    }

    @JsonCreator
    public Coins(@JsonProperty("denominations") Map<CoinDenomination, Integer> denominations) {
        Objects.requireNonNull(denominations, "denominations must not be null");
        this.denominations = new HashMap<>(denominations);
    }

    public Map<CoinDenomination, Integer> getDenominations() {
        return denominations;
    }

    @Override
    public String toString() {
        return "Coins{" +
                "denominations=" + denominations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coins coins = (Coins) o;
        return Objects.equals(denominations, coins.denominations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(denominations);
    }
}
