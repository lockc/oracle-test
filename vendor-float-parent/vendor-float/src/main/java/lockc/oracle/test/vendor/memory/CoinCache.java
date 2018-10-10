package lockc.oracle.test.vendor.memory;

import lockc.oracle.test.vendor.CoinDenomination;
import lockc.oracle.test.vendor.Coins;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A class for keeping track of what coins are available to the float, access to the coin cache is managed by a lock so that
 * additions and removals cannot take place at the same time to avoid concurrency problems.
 *
 * This implementation is not clever enough to work out that the change can be served in other denominations other that the optimum
 *
 * @author Chris
 */
public class CoinCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinCache.class);

    /**
     * A simple map of the coin denominations and how many of them this cache holds
     */
    private final Map<CoinDenomination, Integer> coinDenominationCounts;

    /**
     * Manages access to the underlying map of coins
     */
    private Lock lock;

    public CoinCache(Coins coins) {
        Objects.requireNonNull(coins, "coins must not be null");
        lock = new ReentrantLock();
        coinDenominationCounts = new HashMap<>();
        LOGGER.info("Initialising the coin cache with the coins {}", coins);
        addCoins(coins);
    }

    /**
     * @return an unmodifiable version of the coins in the cache
     */
    public Map<CoinDenomination, Integer> getCoinDenominationCounts() {
        return Collections.unmodifiableMap(coinDenominationCounts);
    }

    /**
     * Add the coins to the coin cache.
     *
     * @param coins the coins to be added to the coin cache
     */
    public void addCoins(Coins coins) {
        LOGGER.info("Adding coins to the coin cache {}", coins);
        if (Objects.nonNull(coins) && Objects.nonNull(coins.getDenominations())) {
            try {
                lock.lock();
                LOGGER.debug("Acquired the coin cache lock in order to add coins");

                coins.getDenominations().entrySet().forEach(entry -> {
                    Integer existing = coinDenominationCounts.get(entry.getKey());
                    if (Objects.isNull(existing)) {
                        existing = 0;
                    }
                    Integer addition = entry.getValue();
                    coinDenominationCounts.put(entry.getKey(), existing + addition);
                });
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * An atomic operation to remove the coins from the coin cache.  If any of the coins cannot be removed from the cache due to lack of coins then none
     * of the coins will be removed.
     *
     * @param coins the coins to remove from the cache.
     */
    public void removeCoins(Coins coins) {
        LOGGER.info("Removing coins from the coin cache {}", coins);
        if (Objects.nonNull(coins) && Objects.nonNull(coins.getDenominations())) {
            try {
                lock.lock();
                LOGGER.debug("Acquired the coin cache lock in order to remove coins");

                tryRemoveCoins(coins);

                coins.getDenominations().entrySet().forEach(entry -> {
                    Integer existing = coinDenominationCounts.get(entry.getKey());
                    if (Objects.isNull(existing)) {
                        existing = 0;
                    }
                    Integer subtraction = entry.getValue();
                    coinDenominationCounts.put(entry.getKey(), existing - subtraction);
                });
            } finally {
                lock.unlock();
            }
        }
    }

    private void tryRemoveCoins(Coins coins) {
        LOGGER.debug("Checking the coins can be removed");
        coins.getDenominations().entrySet().forEach(entry -> {
            CoinDenomination coinDenomination = entry.getKey();
            Integer existing = coinDenominationCounts.get(coinDenomination);
            if (Objects.isNull(existing)) {
                existing = 0;
            }
            Integer subtraction = entry.getValue();
            if (existing - subtraction < 0) {
                LOGGER.error("There are not enough {} coins to remove {} of them", coinDenomination, subtraction);
                throw new IllegalStateException("Cannot service request, not enough coins");
            }
        });
    }
}
