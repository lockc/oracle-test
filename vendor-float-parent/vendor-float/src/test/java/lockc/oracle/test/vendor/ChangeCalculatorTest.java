package lockc.oracle.test.vendor;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link ChangeCalculator}
 *
 * @author Chris
 */
public class ChangeCalculatorTest {

    private ChangeCalculator changeCalculator;

    @Before
    public void setUp() throws Exception {
        changeCalculator = new ChangeCalculator();

    }

    @Test
    public void shouldCalculateChange() throws Exception {

        // Given
        double value = 10.99;

        Map<CoinDenomination,Integer> denominations = new HashMap<>();
        denominations.put(CoinDenomination.ONE_POUND, 10);
        denominations.put(CoinDenomination.FIFTY_PENCE, 1);
        denominations.put(CoinDenomination.TWENTY_PENCE, 2);
        denominations.put(CoinDenomination.FIVE_PENCE, 1);
        denominations.put(CoinDenomination.TWO_PENCE, 2);

        // When
        Coins coins = changeCalculator.calculateChange(value);

        // Then
        assertThat(coins).isNotNull();
        assertThat(coins.getDenominations()).isEqualTo(denominations);
    }

    @Test
    public void shouldCalculateChangeGivenZeroValue() throws Exception {

        // Given
        double value = 0;
        Map<CoinDenomination,Integer> denominations = new HashMap<>();

        // When
        Coins coins = changeCalculator.calculateChange(value);

        // Then
        assertThat(coins).isNotNull();
        assertThat(coins.getDenominations()).isEqualTo(denominations);
    }

    @Test
    public void shouldCalculateChangeGivenNegativeValue() throws Exception {

        // Given
        double value = -1;
        Map<CoinDenomination,Integer> denominations = new HashMap<>();

        // When
        Coins coins = changeCalculator.calculateChange(value);

        // Then
        assertThat(coins).isNotNull();
        assertThat(coins.getDenominations()).isEqualTo(denominations);
    }
}
