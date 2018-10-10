package lockc.oracle.test.vendor;

/**
 * An interface for the float service
 *
 * @author Chris
 */
public interface FloatService {

    /**
     * Initialise the float with the given coins
     *
     * @param coins the coins to initialise the float with
     * @throws FloatException when an issue occurs initialising the float
     */

    void initialise(Coins coins) throws FloatException;

    /**
     * Deposit the given coins to the float, adding the coins to any existing coins that are already in the float
     *
     * @param coins the coins to add to the float
     * @throws FloatException when an issue occurs depositing to the float
     */
    void deposit(Coins coins) throws FloatException;

    /**
     * Issue coins to the value of the required change.
     *
     * @param change the amount of change required
     * @return the coins to the value of the required change
     * @throws FloatException when an issue occurs issuing change from the float
     */
    Coins issue(double change) throws FloatException;

    /**
     * @return the contents of the float
     */
    Coins getFloat();
}
