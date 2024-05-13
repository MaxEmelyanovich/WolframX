package framexteam.wolframx.calculations.operations.integrals;


import framexteam.wolframx.calculations.operations.equations.GaussSolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class IntegralLibrary {
    // private static final double tolerance = 1e-6;
    // private static final int N = 100;
    private static final Logger logger = LogManager.getLogger(GaussSolver.class);
    private static long elapsedTime;

    public static double performIntegralOperation(String function, double start, double stop, IntegralOperation operation, double tolerance, int N){
        Objects.requireNonNull(function, "Function cannot be null");
        Objects.requireNonNull(start, "Lower bound cannot be null");
        Objects.requireNonNull(stop, "Upper bound cannot be null");
        long startTime = System.currentTimeMillis();
        double result = operation.calculateIntegral(function, start, stop, N, tolerance);
        elapsedTime = System.currentTimeMillis() - startTime;
        logger.info( "Total elapsed time: {} seconds.", (elapsedTime / 1000.0));
        return result;
    }

}
