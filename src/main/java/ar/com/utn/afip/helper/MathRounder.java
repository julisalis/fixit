package ar.com.utn.afip.helper;

import java.math.BigDecimal;

/**
 * Created by scamisay on 24/07/17.
 */
public class MathRounder {

    public static BigDecimal round(Double number){
        return BigDecimal.valueOf(number).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
