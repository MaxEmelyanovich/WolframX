#include "GaussSolverJNI.h"
#include <iostream>
#include <vector>
#include <stdexcept>
#include <thread>
#include <cmath>
#include <chrono>
#include <mutex>

class GaussSolver {
private:
    std::vector<std::vector<double>>& coefficients;
    std::vector<double>& constants;
    static long spentTime;
    static std::mutex mtx;

public:
    GaussSolver(std::vector<std::vector<double>>& coefficients, std::vector<double>& constants)
        : coefficients(coefficients), constants(constants) {}

    static long getSpentTime() {
        return spentTime;
    }

    void elimination(int row, int threadCount) {
        int n = coefficients.size();

        // Pivoting
        int max = row;
        for (int k = row + 1; k < n; k++) {
            if (std::abs(coefficients[k][row]) > std::abs(coefficients[max][row])) {
                max = k;
            }
        }

        {
            std::lock_guard<std::mutex> lock(mtx);
            std::swap(coefficients[row], coefficients[max]);
            std::swap(constants[row], constants[max]);
        }

        if (std::abs(coefficients[row][row]) <= 1e-10) {
            throw std::invalid_argument("Matrix is singular or nearly singular");
        }

        // Elimination process using thread pool
        std::vector<std::thread> threads;
        for (int k = row + 1; k < n; k++) {
            if (threads.size() < threadCount) {
                threads.push_back(std::thread([this, row, k]() {
                    double factor = coefficients[k][row] / coefficients[row][row];
                    constants[k] -= factor * constants[row];
                    for (int j = row; j < coefficients.size(); j++) {
                        coefficients[k][j] -= factor * coefficients[row][j];
                    }
                    }));
            }
            else {
                for (auto& thread : threads) {
                    thread.join();
                }
                threads.clear();
            }
        }

        for (auto& thread : threads) {
            thread.join();
        }
    }

    std::vector<double> solve(int threadCount) {
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

        auto start = std::chrono::high_resolution_clock::now();

        for (int i = 0; i < n; i++) {
            elimination(i, threadCount);
        }

        // Back substitution
        std::vector<double> solution(n);
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += coefficients[i][j] * solution[j];
            }
            solution[i] = (constants[i] - sum) / coefficients[i][i];
        }

        auto end = std::chrono::high_resolution_clock::now();
        spentTime = std::chrono::duration_cast<std::chrono::milliseconds>(end - start).count();

        return solution;
    }
};


static GaussSolver* solverInstance = nullptr;
long GaussSolver::spentTime = 0;
std::mutex GaussSolver::mtx;


    JNIEXPORT jdoubleArray JNICALL Java_framexteam_wolframx_calculations_operations_equations_GaussSolverJNI_solve
    (JNIEnv* env, jclass cls, jobjectArray coefficientsArray, jdoubleArray constantsArray, jint threadCount) {
        jsize n = env->GetArrayLength(coefficientsArray);

        std::vector<std::vector<double>> coefficients(n);
        for (int i = 0; i < n; i++) {
            jdoubleArray rowArray = (jdoubleArray)env->GetObjectArrayElement(coefficientsArray, i);
            jsize rowLength = env->GetArrayLength(rowArray);
            jdouble* rowData = env->GetDoubleArrayElements(rowArray, nullptr);
            coefficients[i] = std::vector<double>(rowData, rowData + rowLength);
            env->ReleaseDoubleArrayElements(rowArray, rowData, JNI_ABORT);
            env->DeleteLocalRef(rowArray);
        }

        jdouble* constantsData = env->GetDoubleArrayElements(constantsArray, nullptr);
        std::vector<double> constants(constantsData, constantsData + n);
        env->ReleaseDoubleArrayElements(constantsArray, constantsData, JNI_ABORT);

        GaussSolver solver(coefficients, constants);
        std::vector<double> solution;
        try {
            solution = solver.solve(threadCount);
        }
        catch (const std::exception& e) {
            jclass exceptionClass = env->FindClass("java/lang/RuntimeException");
            env->ThrowNew(exceptionClass, e.what());
            env->DeleteLocalRef(exceptionClass);
            return nullptr;
        }

        jdoubleArray resultArray = env->NewDoubleArray(n);
        env->SetDoubleArrayRegion(resultArray, 0, n, solution.data());

        return resultArray;
    }


    JNIEXPORT jlong JNICALL Java_framexteam_wolframx_calculations_operations_equations_GaussSolverJNI_getExecutionTime
    (JNIEnv* env, jclass cls) {
        return solverInstance ? GaussSolver::getSpentTime() : 0;
    }