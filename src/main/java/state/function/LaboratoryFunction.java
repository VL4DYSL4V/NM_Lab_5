package state.function;

import org.apache.commons.math3.analysis.UnivariateFunction;

public interface LaboratoryFunction {

    UnivariateFunction getFunction();

    UnivariateFunction getFirstDerivative();

}
