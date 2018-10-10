package lockc.oracle.test.vendor;

import lockc.oracle.test.vendor.memory.CoinCache;
import lockc.oracle.test.vendor.memory.InMemoryFloatService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Chris
 */
@Configuration
public class ApplicationConfiguration {

    @Bean
    public FloatService floatService() {
        return new InMemoryFloatService(new CoinCache(new Coins()), new ChangeCalculator());
    }

}
