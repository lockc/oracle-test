package lockc.oracle.test.vendor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Chris
 */
public class IssueChangeRequest {
    private double value;

    @JsonCreator
    public IssueChangeRequest(@JsonProperty("value") double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
