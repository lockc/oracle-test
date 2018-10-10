package lockc.oracle.test.vendor;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.shouldHaveThrown;

/**
 * Test class for {@link Coins}
 *
 * @author Chris
 */
public class CoinsTest {

    @Test
    public void shouldInstantiateGivenEmptyDenominations() throws Exception {

        // Given
        Map<CoinDenomination, Integer> denominations = new HashMap<>();

        // Then
        Coins coins = new Coins(denominations);

        // When
        assertThat(coins).isNotNull();
        assertThat(coins.getDenominations()).isNotNull();
        assertThat(coins.getDenominations()).isNotSameAs(denominations);
        assertThat(coins.getDenominations()).isEqualTo(denominations);
    }

    @Test
    public void shouldFailToInstantiateGivenNullDenominations() throws Exception {
        // Given
        Map<CoinDenomination, Integer> denominations = null;

        try {
            // WHen
            new Coins(denominations);
            shouldHaveThrown(NullPointerException.class);
        } catch (NullPointerException e) {
            // Then
            assertThat(e).hasMessage("denominations must not be null");
        }
    }

    @Test
    public void shouldVerifyEqualsContract() {
        EqualsVerifier.forClass(Coins.class).verify();
    }
}
