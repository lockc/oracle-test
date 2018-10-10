package lockc.oracle.test.vendor.memory;

import lockc.oracle.test.vendor.ChangeCalculator;
import lockc.oracle.test.vendor.CoinDenomination;
import lockc.oracle.test.vendor.Coins;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.shouldHaveThrown;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link InMemoryFloatService}
 *
 * @author Chris
 */
@RunWith(MockitoJUnitRunner.class)
public class InMemoryFloatServiceTest {

    @Mock
    private CoinCache coinCache;

    @Mock
    private ChangeCalculator changeCalculator;

    @InjectMocks
    private InMemoryFloatService target;

    @Test
    public void shouldFailToInstantiateGivenNullCoinCache() throws Exception {
        // Given
        CoinCache coinCache = null;

        try {
            // WHen
            new InMemoryFloatService(coinCache, changeCalculator);
            shouldHaveThrown(NullPointerException.class);
        } catch (NullPointerException e) {
            // Then
            assertThat(e).hasMessage("coinCache must not be null");
        }
    }

    @Test
    public void shouldFailToInstantiateGivenNullChangeCalculator() throws Exception {
        // Given
        ChangeCalculator changeCalculator = null;

        try {
            // WHen
            new InMemoryFloatService(coinCache, changeCalculator);
            shouldHaveThrown(NullPointerException.class);
        } catch (NullPointerException e) {
            // Then
            assertThat(e).hasMessage("changeCalculator must not be null");
        }
    }

    @Test
    public void shouldInitialiseGivenCoins() throws Exception {

        // Given
        Coins coins = new Coins(Collections.singletonMap(CoinDenomination.FIFTY_PENCE, 25));
        target = new InMemoryFloatService(new CoinCache(coins), changeCalculator);

        // When
        target.initialise(coins);

        // Then
        assertThat(target.getFloat().getDenominations()).isEqualTo(coins.getDenominations());
        verifyZeroInteractions(coinCache);
    }

    @Test
    public void shouldReplaceCoinCacheGivenInitialised() throws Exception {

        // Given
        Coins coins = new Coins(Collections.singletonMap(CoinDenomination.FIFTY_PENCE, 25));

        // When
        target.initialise(coins);

        // Then
        verifyZeroInteractions(coinCache);
    }

    @Test
    public void shouldDepositCoins() throws Exception {

        // Given
        Coins coins = new Coins();

        // When
        target.deposit(coins);

        // Then
        verify(coinCache).addCoins(coins);
        verifyNoMoreInteractions(coinCache);
    }

    @Test
    public void shouldIssueChange() throws Exception {

        // Given
        double value = 12.50;
        Map<CoinDenomination, Integer> denominations = new HashMap<>();
        denominations.put(CoinDenomination.ONE_POUND, 12);
        denominations.put(CoinDenomination.FIFTY_PENCE, 1);
        Coins coins = new Coins(denominations);

        given(changeCalculator.calculateChange(anyDouble())).willReturn(coins);

        // When
        Coins issuedCoins = target.issue(value);

        // Then
        assertThat(issuedCoins).isNotNull();
        assertThat(issuedCoins.getDenominations()).isEqualTo(denominations);

        verify(coinCache).removeCoins(coins);
        verify(changeCalculator).calculateChange(value);
        verifyNoMoreInteractions(coinCache, changeCalculator);
    }

    @Test
    public void shouldNotIssueChangeGivenZeroAmount() throws Exception {

        // Given
        double value = 0;

        // When
        Coins issuedCoins = target.issue(value);

        // Then
        assertThat(issuedCoins).isNotNull();
        assertThat(issuedCoins.getDenominations()).isEmpty();

        verifyZeroInteractions(coinCache);
    }

    @Test
    public void shouldNotIssueChangeGivenNegativeAmount() throws Exception {

        // Given
        double value = -1.9;

        // When
        Coins issuedCoins = target.issue(value);

        // Then
        assertThat(issuedCoins).isNotNull();
        assertThat(issuedCoins.getDenominations()).isEmpty();

        verifyZeroInteractions(coinCache);
    }

    @Test
    public void shouldGetFloat() throws Exception {

        // Given

        // When
        Coins actualCoins = target.getFloat();

        // Then
        assertThat(actualCoins).isNotNull();
        assertThat(actualCoins.getDenominations()).isNotNull();
        assertThat(actualCoins.getDenominations()).isEmpty();

        verify(coinCache).getCoinDenominationCounts();
        verifyNoMoreInteractions(coinCache);
    }
}
