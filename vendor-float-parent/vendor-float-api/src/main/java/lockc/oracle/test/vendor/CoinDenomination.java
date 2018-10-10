package lockc.oracle.test.vendor;

/**
 * An enum representing the different coin denominations used and their associated value in pence
 *
 * @author Chris
 */
public enum CoinDenomination {

    ONE_PENCE(1),
    TWO_PENCE(2),
    FIVE_PENCE(5),
    TEN_PENCE(10),
    TWENTY_PENCE(20),
    FIFTY_PENCE(50),
    ONE_POUND(100);

    int value;

    CoinDenomination(int value) {
        this.value = value;
    }

    /**
     * @return the value in pence
     */
    public int getValue() {
        return value;
    }
}
