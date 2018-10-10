package lockc.oracle.test.vendor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * A very simple change calculator that takes a value for which changes is required and calculates the coin denominations required
 *
 * @author Chris
 */
public class ChangeCalculator {

    /**
     * Calculate the coins required to meet the change required
     *
     * @param change the change required
     * @return the coins that sum the required change
     */
    public Coins calculateChange(double change) {

        if (change <= 0) {
            return new Coins();
        }
        BigDecimal bd = new BigDecimal(change);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        int pounds = bd.toBigInteger().intValue() * 100;
        int remainder = bd.remainder(BigDecimal.ONE).multiply(new BigDecimal(100)).intValue();
        int totalPence = pounds + remainder;
        HashMap<CoinDenomination, Integer> denominations = new HashMap<>();


        totalPence = setForDenomination(totalPence, CoinDenomination.ONE_POUND, denominations);
        totalPence = setForDenomination(totalPence, CoinDenomination.FIFTY_PENCE, denominations);
        totalPence = setForDenomination(totalPence, CoinDenomination.TWENTY_PENCE, denominations);
        totalPence = setForDenomination(totalPence, CoinDenomination.TEN_PENCE, denominations);
        totalPence = setForDenomination(totalPence, CoinDenomination.FIVE_PENCE, denominations);
        totalPence = setForDenomination(totalPence, CoinDenomination.TWO_PENCE, denominations);
        setForDenomination(totalPence, CoinDenomination.ONE_PENCE, denominations);

        return new Coins(denominations);
    }

    private int setForDenomination(int remainder, CoinDenomination coinDenomination, Map<CoinDenomination, Integer> denominations) {
        double divideByDenom = remainder / coinDenomination.getValue();
        if (divideByDenom >= 1 && remainder > 0) {
            denominations.put(coinDenomination, new BigDecimal(divideByDenom).intValue());
            remainder = new BigDecimal(remainder - coinDenomination.getValue() * divideByDenom).intValue();
        }
        return remainder;
    }
}
