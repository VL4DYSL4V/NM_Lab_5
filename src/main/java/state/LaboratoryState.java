package state;

import framework.state.AbstractApplicationState;
import framework.state.StateHelper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.geometry.euclidean.oned.Interval;
import org.apache.commons.math3.util.FastMath;

@Getter
@Setter
public class LaboratoryState extends AbstractApplicationState {

    private final UnivariateFunction function = (x) ->
            FastMath.pow(9.0 - x, -1);

    private final UnivariateFunction derivative = (x) ->
            FastMath.pow(9 - x, -2);

    private Interval interval = new Interval(4.0, 7.0);

    private double precision = 0.5;

    @Override
    protected void initVariableNameToGettersMap() {
        this.variableNameToGetter.put("interval", this::getInterval);
        this.variableNameToGetter.put("precision", this::getPrecision);
        this.variableNameToGetter.put("function", this::getFunction);
        this.variableNameToGetter.put("derivative", this::getDerivative);
    }

    @Override
    protected void initVariableNameToSettersMap() {
        this.variableNameToSetter.put("interval", StateHelper
                .getIntervalSetter("interval", this::setInterval));
        this.variableNameToSetter.put("precision", StateHelper
                .getDoubleSetter("precision", this::setPrecision));
    }
}
