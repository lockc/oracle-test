package lockc.oracle.test.vendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Chris
 */
@RestController
public class FloatController {

    @Autowired
    private FloatService floatService;

    @RequestMapping(path = "initialise", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetFloatResponse> initialiseFloat(@RequestBody InitFloatRequest initFloatRequest) throws FloatException {
        floatService.initialise(initFloatRequest.getCoins());
        return new ResponseEntity<>(new GetFloatResponse(floatService.getFloat()), HttpStatus.OK);
    }

    @RequestMapping(path = "float", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetFloatResponse> getFloat() throws FloatException {
        return new ResponseEntity<>(new GetFloatResponse(floatService.getFloat()), HttpStatus.ACCEPTED);
    }

    @RequestMapping(path = "deposit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetFloatResponse> depositCoins(@RequestBody DepositFloatRequest depositFloatRequest) throws FloatException {
        floatService.deposit(depositFloatRequest.getCoins());
        return new ResponseEntity<>(new GetFloatResponse(floatService.getFloat()), HttpStatus.OK);
    }

    @RequestMapping(path = "issueChange", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IssueChangeResponse> issueChange(@RequestBody IssueChangeRequest issueChangeRequest) throws FloatException {
        Coins issued = floatService.issue(issueChangeRequest.getValue());
        return new ResponseEntity<>(new IssueChangeResponse(issued), HttpStatus.OK);
    }
}
