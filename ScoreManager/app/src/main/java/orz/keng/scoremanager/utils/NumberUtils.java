package orz.keng.scoremanager.utils;

import java.math.BigDecimal;

public class NumberUtils {
    public static Double formatDouble(Double number){
        return (new BigDecimal(number)).setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
