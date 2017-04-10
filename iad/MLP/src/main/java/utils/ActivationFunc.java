package utils;

import static java.lang.Math.exp;

public class ActivationFunc {
    public static Double sigmoid(Double value){
            return 1.0/(1.0+exp(-value));
    }

    public static Double sigmoidDerivative(Double value){
        return exp(value)/((exp(value) + 1) * (exp(value) + 1));
    }
}
