package command;

import framework.command.AbstractRunnableCommand;
import framework.utils.ConsoleUtils;
import framework.utils.ValidationUtils;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.geometry.euclidean.oned.Interval;
import org.apache.commons.math3.ode.AbstractIntegrator;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.univariate.BrentOptimizer;
import org.apache.commons.math3.optim.univariate.SearchInterval;
import org.apache.commons.math3.optim.univariate.UnivariateObjectiveFunction;
import state.function.LaboratoryFunction;
import state.function.Variant21Function;

public class RunnableCommand extends AbstractRunnableCommand {

    private static final String NAME = "run";

    public RunnableCommand() {
        super(NAME);
    }

    @Override
    public void execute(String[] strings) {
        assertStateSanity();
        LaboratoryFunction laboratoryFunction = new Variant21Function();
        int nParts = getNPArts(laboratoryFunction);
        ConsoleUtils.println(String.format("Steps: %d", nParts));
        double integral = calculateIntegral(laboratoryFunction, nParts);

        SimpsonIntegrator simpson = new SimpsonIntegrator();
        Interval interval = (Interval) applicationState.getVariable("interval");
        double actualIntegral = simpson
                .integrate(1000, laboratoryFunction.getFunction(), interval.getInf(), interval.getSup());

        ConsoleUtils.println(String.format("My integral - actual integral = %f", integral - actualIntegral));
    }

    private int getNPArts(LaboratoryFunction laboratoryFunction) {
        double precision = (Double) applicationState.getVariable("precision");
        Interval interval = (Interval) applicationState.getVariable("interval");
        double factor = Math.pow(interval.getSup() - interval.getInf(), 2) / (2 * precision);

        MaxEval maxEval = new MaxEval(1000);
        double relativeTolerance = 0.001;
        double absoluteTolerance = 0.001;
        BrentOptimizer optimizer = new BrentOptimizer(relativeTolerance, absoluteTolerance);
        UnivariateFunction absOfFirstDerivative = (x) -> Math.abs(laboratoryFunction.getFirstDerivative().value(x));
        UnivariateObjectiveFunction objective = new UnivariateObjectiveFunction(absOfFirstDerivative);
        SearchInterval searchInterval = new SearchInterval(interval.getInf(), interval.getSup());
        double pointOfMaximum = optimizer.optimize(objective, GoalType.MAXIMIZE, searchInterval, maxEval).getPoint();
        return (int) Math.floor(factor * absOfFirstDerivative.value(pointOfMaximum)) + 1;
    }

    private double calculateIntegral(LaboratoryFunction laboratoryFunction, int nParts) {
        ValidationUtils.requireGreaterOrEqualThan(nParts, 1, "Step must be >= 1");
        Interval interval = (Interval) applicationState.getVariable("interval");
        double dx = (interval.getSup() - interval.getInf()) / nParts;
        double result = 0;
        for (int i = 1; i <= nParts; i++) {
            result += laboratoryFunction.getFunction().value(interval.getInf() + i * dx);
        }
        return result * dx;
    }

    ;

    private void assertStateSanity() {
        ValidationUtils.requireNonNull(
                applicationState.getVariable("interval"),
                applicationState.getVariable("precision"));
    }

}
