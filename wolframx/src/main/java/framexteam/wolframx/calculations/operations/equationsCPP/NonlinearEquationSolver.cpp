#include <iostream>
#include <vector>
#include <cmath>
#include <random>
#include <thread>
#include <mutex>
#include <jni.h>

class NonlinearEquationSolver {
private:
    double epsilon;
    double answerThreshold;
    int maxIterations;
    int totalIterations;

    std::mutex lock;
    std::vector<double> roots;

    int numThreads;
    std::vector<double> coefficients;

public:
    NonlinearEquationSolver(const std::vector<double>& coefficients, int numThreads, double epsilon, int maxIterations)
        : numThreads(numThreads), coefficients(coefficients), epsilon(epsilon), maxIterations(maxIterations)
    {
        answerThreshold = 0.001;
        totalIterations = 10000;
    }

    double function(const std::vector<double>& coefficients, double x) {
        double result = 0.0;
        int len = coefficients.size() - 1;
        for (int i = 0; i < coefficients.size(); i++) {
            result += coefficients[i] * std::pow(x, len - i);
        }
        return result;
    }

    double derivative(const std::vector<double>& coefficients, double x) {
        double h = 1e-6;
        return (function(coefficients, x + h) - function(coefficients, x)) / h;
    }

    void solve() {
        std::random_device rd;
        std::mt19937 random(rd());
        roots.clear();
        std::uniform_real_distribution<double> dist(-10000.0, 10000.0);

        for (int j = 0; j < totalIterations / numThreads; j++) {
            double x0 = dist(random);
            double x = x0;
            for (int i = 0; i < maxIterations; i++) {
                double fx = function(coefficients, x);
                double fpx = derivative(coefficients, x);
                double newX = x - fx / fpx;

                if (std::abs(newX - x) < epsilon) {
                    bool isUnique = true;
                    std::lock_guard<std::mutex> guard(lock);
                    for (double root : roots) {
                        if (std::abs(newX - root) < answerThreshold) {
                            isUnique = false;
                            break;
                        }
                    }
                    if (isUnique) {
                        roots.push_back(newX);
                    }
                    break;
                }

                x = newX;
            }
        }
    }

    std::vector<double> getRoots() const {
        return roots;
    }
};

std::vector<double> solveNonlinearEquation(const std::vector<double>& coefficients, int threadCount,
    double epsilon, int maxIterations)
{
    if (coefficients.empty()) {
        throw std::invalid_argument("Coefficients cannot be empty");
    }

    if (threadCount <= 0) {
        throw std::invalid_argument("Thread count must be greater than zero.");
    }

    if (threadCount > std::thread::hardware_concurrency()) {
        throw std::invalid_argument("Thread count exceeded the maximum. Current device has " +
            std::to_string(std::thread::hardware_concurrency()) + " available processors");
    }

    std::vector<double> roots;
    NonlinearEquationSolver solver(coefficients, threadCount, epsilon, maxIterations);

    std::vector<std::thread> threads;
    for (int i = 0; i < threadCount; i++) {
        threads.emplace_back(&NonlinearEquationSolver::solve, &solver);
    }

    for (auto& thread : threads) {
        thread.join();
    }

    roots = solver.getRoots();

    if (roots.empty()) {
        throw std::runtime_error("No solutions exist");
    }

    return roots;
}

JNIEXPORT jdoubleArray JNICALL Java_framexteam_wolframx_calculations_operations_equations_NonlinearEquationSolverJNI_solve(
    JNIEnv* env, jclass clazz, jdoubleArray coefficientsArray, jint threadCount, jdouble epsilon, jint maxIterations)
{
    jsize coefficientsLength = env->GetArrayLength(coefficientsArray);

    jdouble* coefficientsData = env->GetDoubleArrayElements(coefficientsArray, nullptr);

    std::vector<double> coefficients(coefficientsData, coefficientsData + coefficientsLength);

    std::vector<double> roots = solveNonlinearEquation(coefficients, threadCount, epsilon, maxIterations);

    jdoubleArray resultArray = env->NewDoubleArray(roots.size());
    env->SetDoubleArrayRegion(resultArray, 0, roots.size(), roots.data());

    env->ReleaseDoubleArrayElements(coefficientsArray, coefficientsData, JNI_ABORT);

    return resultArray;
}