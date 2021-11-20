package state.function;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;

public interface LaboratoryFunction {

    UnivariateFunction getFunction();

    UnivariateFunction getFirstDerivative();

    DerivativeStructure getAsDerivativeStructure(double x);

}
