package lockc.oracle.test.vendor.memory;

import lockc.oracle.test.vendor.CoinDenomination;
import lockc.oracle.test.vendor.Coins;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.shouldHaveThrown;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


/**
 * Test class for {@link CoinCache}
 *
 * @author Chris
 */
public class CoinCacheTest {

    private CoinCache target;

    @Before
    public void setUp() throws Exception {
        target = new CoinCache(new Coins(new HashMap<>()));
    }

    @Test
    public void shouldAddCoinsGivenEmptyCoinCache() throws Exception {

        // Given
        Map<CoinDenomination, Integer> coinDenominationIntegerMap = givenDefaultCoinDenominations();
        Coins coins = new Coins(coinDenominationIntegerMap);

        // When
        target.addCoins(coins);

        // Then
        assertThat(target.getCoinDenominationCounts()).isEqualTo(coinDenominationIntegerMap);
    }

    @Test
    public void shouldAddCoinsGivenPopulatedCoinCache() throws Exception {

        // Given
        Map<CoinDenomination, Integer> prePopulatedCoins = givenDefaultCoinDenominations();
        Coins coins = new Coins(prePopulatedCoins);
        target = new CoinCache(coins);

        Map<CoinDenomination, Integer> coinsToAdd = new HashMap<>();
        coinsToAdd.put(CoinDenomination.ONE_PENCE, 1);
        coinsToAdd.put(CoinDenomination.TWO_PENCE, 1);
        coinsToAdd.put(CoinDenomination.FIVE_PENCE, 1);
        coinsToAdd.put(CoinDenomination.TEN_PENCE, 1);
        coinsToAdd.put(CoinDenomination.TWENTY_PENCE, 1);
        coinsToAdd.put(CoinDenomination.FIFTY_PENCE, 1);
        coinsToAdd.put(CoinDenomination.ONE_POUND, 1);
        coinsToAdd.put(CoinDenomination.ONE_PENCE, 1);
        coins = new Coins(coinsToAdd);

        Map<CoinDenomination, Integer> expectedCoins = new HashMap<>();
        expectedCoins.put(CoinDenomination.ONE_PENCE, 3);
        expectedCoins.put(CoinDenomination.TWO_PENCE, 4);
        expectedCoins.put(CoinDenomination.FIVE_PENCE, 5);
        expectedCoins.put(CoinDenomination.TEN_PENCE, 6);
        expectedCoins.put(CoinDenomination.TWENTY_PENCE, 7);
        expectedCoins.put(CoinDenomination.FIFTY_PENCE, 8);
        expectedCoins.put(CoinDenomination.ONE_POUND, 9);
        expectedCoins.put(CoinDenomination.ONE_PENCE, 10);

        // When
        target.addCoins(coins);

        // Then
        assertThat(target.getCoinDenominationCounts()).isEqualTo(expectedCoins);
    }

    @Test
    public void shouldNotAddCoinsGivenNullCoins() throws Exception {

        // Given
        Coins coins = null;

        // When
        target.addCoins(coins);

        // Then
        assertThat(target.getCoinDenominationCounts()).isEmpty();

    }

    @Test
    public void shouldRemoveCoins() throws Exception {

        // Given
        Map<CoinDenomination, Integer> prePopulatedCoins = givenDefaultCoinDenominations();
        Coins coins = new Coins(prePopulatedCoins);
        target = new CoinCache(coins);

        Map<CoinDenomination, Integer> coinsToRemove = new HashMap<>();
        coinsToRemove.put(CoinDenomination.ONE_PENCE, 1);
        coinsToRemove.put(CoinDenomination.TWO_PENCE, 1);
        coinsToRemove.put(CoinDenomination.FIVE_PENCE, 1);
        coinsToRemove.put(CoinDenomination.TEN_PENCE, 1);
        coinsToRemove.put(CoinDenomination.TWENTY_PENCE, 1);
        coinsToRemove.put(CoinDenomination.FIFTY_PENCE, 1);
        coinsToRemove.put(CoinDenomination.ONE_POUND, 1);
        coinsToRemove.put(CoinDenomination.ONE_PENCE, 1);
        coins = new Coins(coinsToRemove);

        Map<CoinDenomination, Integer> expectedCoins = new HashMap<>();
        expectedCoins.put(CoinDenomination.ONE_PENCE, 1);
        expectedCoins.put(CoinDenomination.TWO_PENCE, 2);
        expectedCoins.put(CoinDenomination.FIVE_PENCE, 3);
        expectedCoins.put(CoinDenomination.TEN_PENCE, 4);
        expectedCoins.put(CoinDenomination.TWENTY_PENCE, 5);
        expectedCoins.put(CoinDenomination.FIFTY_PENCE, 6);
        expectedCoins.put(CoinDenomination.ONE_POUND, 7);
        expectedCoins.put(CoinDenomination.ONE_PENCE, 8);

        // When
        target.removeCoins(coins);

        // Then
        assertThat(target.getCoinDenominationCounts()).isEqualTo(expectedCoins);

    }

    @Test
    public void shouldFailToRemoveCoinsGivenNotEnoughCoins() throws Exception {

        // Given
        Map<CoinDenomination, Integer> coinDenominationIntegerMap = givenDefaultCoinDenominations();
        Coins coins = new Coins(coinDenominationIntegerMap);

        try {
            // When
            target.removeCoins(coins);
            shouldHaveThrown(IllegalStateException.class);
        } catch (IllegalStateException e) {
            // Then
            assertThat(e).hasMessage("Cannot service request, not enough coins");
        }
    }

    @Test
    public void shouldNotRemoveCoinsGivenNullCoins() throws Exception {

        // Given
        Coins coins = null;

        // When
        target.removeCoins(coins);

        // Then
        assertThat(target.getCoinDenominationCounts()).isEmpty();
    }

    private Map<CoinDenomination, Integer> givenDefaultCoinDenominations() {
        Map<CoinDenomination, Integer> coinDenominationIntegerMap = new HashMap<>();
        coinDenominationIntegerMap.put(CoinDenomination.ONE_PENCE, 2);
        coinDenominationIntegerMap.put(CoinDenomination.TWO_PENCE, 3);
        coinDenominationIntegerMap.put(CoinDenomination.FIVE_PENCE, 4);
        coinDenominationIntegerMap.put(CoinDenomination.TEN_PENCE, 5);
        coinDenominationIntegerMap.put(CoinDenomination.TWENTY_PENCE, 6);
        coinDenominationIntegerMap.put(CoinDenomination.FIFTY_PENCE, 7);
        coinDenominationIntegerMap.put(CoinDenomination.ONE_POUND, 8);
        coinDenominationIntegerMap.put(CoinDenomination.ONE_PENCE, 9);
        return coinDenominationIntegerMap;
    }
}
