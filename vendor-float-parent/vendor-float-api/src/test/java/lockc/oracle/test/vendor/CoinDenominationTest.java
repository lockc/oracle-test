package lockc.oracle.test.vendor;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link CoinDenomination}
 *
 * @author Chris
 */
public class CoinDenominationTest {

    @Test
    public void shouldGetValueGivenOnePence() throws Exception {

        // Given
        CoinDenomination coinDenomination = CoinDenomination.ONE_PENCE;

        // When
        int value = coinDenomination.getValue();

        // Then
        assertThat(value).isEqualTo(1);
    }

    @Test
    public void shouldGetValueGivenTwoPence() throws Exception {

        // Given
        CoinDenomination coinDenomination = CoinDenomination.TWO_PENCE;

        // When
        int value = coinDenomination.getValue();

        // Then
        assertThat(value).isEqualTo(2);
    }

    @Test
    public void shouldGetValueGivenFivePence() throws Exception {

        // Given
        CoinDenomination coinDenomination = CoinDenomination.FIVE_PENCE;

        // When
        int value = coinDenomination.getValue();

        // Then
        assertThat(value).isEqualTo(5);
    }

    @Test
    public void shouldGetValueGivenTenPence() throws Exception {

        // Given
        CoinDenomination coinDenomination = CoinDenomination.TEN_PENCE;

        // When
        int value = coinDenomination.getValue();

        // Then
        assertThat(value).isEqualTo(10);
    }

    @Test
    public void shouldGetValueGivenTwentyPence() throws Exception {

        // Given
        CoinDenomination coinDenomination = CoinDenomination.TWENTY_PENCE;

        // When
        int value = coinDenomination.getValue();

        // Then
        assertThat(value).isEqualTo(20);
    }

    @Test
    public void shouldGetValueGivenFiftyPence() throws Exception {

        // Given
        CoinDenomination coinDenomination = CoinDenomination.FIFTY_PENCE;

        // When
        int value = coinDenomination.getValue();

        // Then
        assertThat(value).isEqualTo(50);
    }

    @Test
    public void shouldGetValueGivenOnePound() throws Exception {

        // Given
        CoinDenomination coinDenomination = CoinDenomination.ONE_POUND;

        // When
        int value = coinDenomination.getValue();

        // Then
        assertThat(value).isEqualTo(100);
    }
}
