package state.function;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
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

    @Override
    public DerivativeStructure getAsDerivativeStructure(double x) {
        DerivativeStructure y = new DerivativeStructure(1, 1, 0, x - 9.0);
        y = y.multiply(-1);
        y = y.pow(-1);
        return y;
    }

}
