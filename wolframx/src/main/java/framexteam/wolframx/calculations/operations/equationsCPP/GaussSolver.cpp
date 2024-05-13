#include "GaussSolverJNI.h"
#include <vector>
#include <stdexcept>
#include <thread>
#include <cmath>
#include <vector>
#include <iostream>

class GaussSolver {
private:
    std::vector<std::vector<double>>& coefficients;
    std::vector<double>& constants;
    int row;

public:
    GaussSolver(std::vector<std::vector<double>>& coefficients, std::vector<double>& constants, int row)
        : coefficients(coefficients), constants(constants), row(row) {}

    void operator()() {
        std::vector<double>& rowCoefficients = coefficients[row];
        double rowConstant = constants[row];
        double pivot = rowCoefficients[row];
        int n = coefficients.size();

        for (int k = row + 1; k < n; k++) {
            double factor = coefficients[k][row] / pivot;
            for (int j = row; j < n; j++) {
                coefficients[k][j] -= factor * rowCoefficients[j];
            }
            constants[k] -= factor * rowConstant;
        }
    }
};

std::vector<double> solve(std::vector<std::vector<double>>& coefficients, std::vector<double>& constants, int threadCount) {
    int n = coefficients.size();

    if (n != coefficients[0].size() || n != constants.size()) {
        throw std::invalid_argument("Matrix dimensions are not equal.");
    }

    if (threadCount <= 0) {
        throw std::invalid_argument("Thread count must be greater than zero.");
    }

    if (threadCount > std::thread::hardware_concurrency()) {
        throw std::invalid_argument("Thread count exceeded the maximum. Current device has " +
            std::to_string(std::thread::hardware_concurrency()) + " available processors");
    }

    std::vector<GaussSolver> solvers;
    for (int i = 0; i < n; i++) {
        solvers.push_back(GaussSolver(coefficients, constants, i));
    }

    std::vector<std::thread> threads;
    for (int i = 0; i < n; i++) {
        threads.push_back(std::thread(solvers[i]));
    }

    for (auto& thread : threads) {
        thread.join();
    }

    std::vector<double> solution(n);
    for (int i = n - 1; i >= 0; i--) {
        double sum = 0.0;
        for (int j = i + 1; j < n; j++) {
            sum += coefficients[i][j] * solution[j];
        }
        if (std::abs(coefficients[i][i]) < 1e-6) {
            throw std::invalid_argument("No solutions, matrix is invertible.");
        }
        else {
            solution[i] = (constants[i] - sum) / coefficients[i][i];
        }
    }

    return solution;
}

JNIEXPORT jdoubleArray JNICALL Java_framexteam_wolframx_calculations_operations_equations_GaussSolverJNI_solve(JNIEnv * env, jclass cls, jobjectArray coefficientsArray, jdoubleArray constantsArray, jint threadCount) {
    // Получение размеров системы
    jsize n = env->GetArrayLength(coefficientsArray);

    // Создание вектора коэффициентов
    std::vector<std::vector<double>> coefficients(n);
    for (int i = 0; i < n; i++) {
        jdoubleArray rowArray = (jdoubleArray)env->GetObjectArrayElement(coefficientsArray, i);
        jsize rowLength = env->GetArrayLength(rowArray);
        jdouble* rowData = env->GetDoubleArrayElements(rowArray, nullptr);
        coefficients[i] = std::vector<double>(rowData, rowData + rowLength);
        env->ReleaseDoubleArrayElements(rowArray, rowData, JNI_ABORT);
        env->DeleteLocalRef(rowArray);
    }

    // Создание вектора констант
    jdouble* constantsData = env->GetDoubleArrayElements(constantsArray, nullptr);
    std::vector<double> constants(constantsData, constantsData + n);
    env->ReleaseDoubleArrayElements(constantsArray, constantsData, JNI_ABORT);

    // Вызов функции решения
    std::vector<double> solution;
    try {
        solution = solve(coefficients, constants, threadCount);
    }
    catch (const std::exception& e) {
        // Обработка ошибки и выброс исключения в Java
        jclass exceptionClass = env->FindClass("java/lang/RuntimeException");
        env->ThrowNew(exceptionClass, e.what());
        env->DeleteLocalRef(exceptionClass);
        return nullptr;
    }

    // Создание и заполнение массива ответа
    jdoubleArray solutionArray = env->NewDoubleArray(n);
    env->SetDoubleArrayRegion(solutionArray, 0, n, solution.data());

    return solutionArray;
}
