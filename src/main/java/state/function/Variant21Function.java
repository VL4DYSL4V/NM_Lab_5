package state.function;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.util.FastMath;

public class Variant21Function implements LaboratoryFunction {

    private static final UnivariateFunction FUNCTION = (x) ->
            FastMath.pow(9.0 - x, -1);

    private static final UnivariateFunction FIRST_DERIVATIVE = (x) ->
            FastMath.pow(9 - x, -2);

    @Override
    public UnivariateFunction getFunction() {
        return FUNCTION;
    }

    @Override
    public UnivariateFunction getFirstDerivative() {
        return FIRST_DERIVATIVE;
    }

}
