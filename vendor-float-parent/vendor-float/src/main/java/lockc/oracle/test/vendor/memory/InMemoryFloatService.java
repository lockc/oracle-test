package lockc.oracle.test.vendor.memory;

import lockc.oracle.test.vendor.ChangeCalculator;
import lockc.oracle.test.vendor.Coins;
import lockc.oracle.test.vendor.FloatException;
import lockc.oracle.test.vendor.FloatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Objects;

/**
 * A simple in memory implementation of the float service that uses a {@link CoinCache} to manage deposits and withdrawals from the float.
 *
 * @author Chris
 */
public class InMemoryFloatService implements FloatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryFloatService.class);

    private CoinCache coinCache;
    private ChangeCalculator changeCalculator = new ChangeCalculator();

    public InMemoryFloatService(CoinCache coinCache, ChangeCalculator changeCalculator) {
        Objects.requireNonNull(coinCache, "coinCache must not be null");
        Objects.requireNonNull(changeCalculator, "changeCalculator must not be null");
        this.coinCache = coinCache;
        this.changeCalculator = changeCalculator;
    }

    /**
     * {{@inheritDoc}}
     */
    @Override
    public void initialise(Coins coins) throws FloatException {
        LOGGER.info("Initialising the float with {}", coins);
        coinCache = new CoinCache(coins);
    }

    /**
     * {{@inheritDoc}}
     */
    @Override
    public void deposit(Coins coins) throws FloatException {
        LOGGER.info("Depositing the coins to the float {}", coins);
        coinCache.addCoins(coins);
    }

    /**
     * {{@inheritDoc}}
     */
    @Override
    public Coins issue(double change) throws FloatException {
        LOGGER.info("Issuing change to the value of {}", change);

        if (change <= 0) {
            LOGGER.info("Change required was less than or equal to zero, no change to issue");
            return new Coins();
        }

        Coins coins = changeCalculator.calculateChange(change);

        try {
            coinCache.removeCoins(coins);
        } catch (IllegalStateException e) {
            LOGGER.error("Cannot issue change there is not enough money in the float", e);
            throw new FloatException("Cannot issue change there is not enough money in the float", e);
        }

        return coins;
    }

    private Coins calculatedRequriedCoins(double change) {
        return new Coins(new HashMap<>());
    }

    @Override
    public Coins getFloat() {
        return new Coins(coinCache.getCoinDenominationCounts());
    }
}
