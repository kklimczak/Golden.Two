package utils;

import static java.lang.Math.exp;

public class ActivationFunc {
    public static Double sigmoid(Double value){
        return 1.0/(1.0+exp(-value));
    }
}
