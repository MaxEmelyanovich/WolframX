#include "VectorLibraryJNI.h"
#include <math.h>
#include <iostream>

JNIEXPORT jdoubleArray JNICALL Java_framexteam_wolframx_calculations_vectors_VectorLibraryJNI_sum(JNIEnv* env, jclass cls, jdoubleArray v1, jdoubleArray v2) {
    jsize size = env->GetArrayLength(v1);
    jdouble* arr1 = env->GetDoubleArrayElements(v1, nullptr);
    jdouble* arr2 = env->GetDoubleArrayElements(v2, nullptr);
    jdoubleArray result = env->NewDoubleArray(size);
    jdouble* resultArr = env->GetDoubleArrayElements(result, nullptr);

    for (jsize i = 0; i < size; i++) {
        resultArr[i] = arr1[i] + arr2[i];
    }

    env->ReleaseDoubleArrayElements(v1, arr1, JNI_ABORT);
    env->ReleaseDoubleArrayElements(v2, arr2, JNI_ABORT);
    env->ReleaseDoubleArrayElements(result, resultArr, 0);

    return result;
}

JNIEXPORT jdoubleArray JNICALL Java_framexteam_wolframx_calculations_vectors_VectorLibraryJNI_sub(JNIEnv* env, jclass cls, jdoubleArray v1, jdoubleArray v2) {
    jsize size = env->GetArrayLength(v1);
    jdouble* arr1 = env->GetDoubleArrayElements(v1, nullptr);
    jdouble* arr2 = env->GetDoubleArrayElements(v2, nullptr);
    jdoubleArray result = env->NewDoubleArray(size);
    jdouble* resultArr = env->GetDoubleArrayElements(result, nullptr);

    for (jsize i = 0; i < size; i++) {
        resultArr[i] = arr1[i] - arr2[i];
    }

    env->ReleaseDoubleArrayElements(v1, arr1, JNI_ABORT);
    env->ReleaseDoubleArrayElements(v2, arr2, JNI_ABORT);
    env->ReleaseDoubleArrayElements(result, resultArr, 0);

    return result;
}

JNIEXPORT jdouble JNICALL Java_framexteam_wolframx_calculations_vectors_VectorLibraryJNI_scalarMul(JNIEnv* env, jclass cls, jdoubleArray v1, jdoubleArray v2) {
    jsize size = env->GetArrayLength(v1);
    jdouble* arr1 = env->GetDoubleArrayElements(v1, nullptr);
    jdouble* arr2 = env->GetDoubleArrayElements(v2, nullptr);
    jdouble result = 0;

    for (jsize i = 0; i < size; i++) {
        result += arr1[i] * arr2[i];
    }

    env->ReleaseDoubleArrayElements(v1, arr1, JNI_ABORT);
    env->ReleaseDoubleArrayElements(v2, arr2, JNI_ABORT);

    return result;
}

JNIEXPORT jdoubleArray JNICALL Java_framexteam_wolframx_calculations_vectors_VectorLibraryJNI_vectorMul(JNIEnv* env, jclass cls, jdoubleArray v1, jdoubleArray v2) {
    jdouble* arr1 = env->GetDoubleArrayElements(v1, nullptr);
    jdouble* arr2 = env->GetDoubleArrayElements(v2, nullptr);
    jdoubleArray result = env->NewDoubleArray(3);
    jdouble* resultArr = env->GetDoubleArrayElements(result, nullptr);

    resultArr[0] = arr1[1] * arr2[2] - arr1[2] * arr2[1];
    resultArr[1] = arr1[2] * arr2[0] - arr1[0] * arr2[2];
    resultArr[2] = arr1[0] * arr2[1] - arr1[1] * arr2[0];

    env->ReleaseDoubleArrayElements(v1, arr1, JNI_ABORT);
    env->ReleaseDoubleArrayElements(v2, arr2, JNI_ABORT);
    env->ReleaseDoubleArrayElements(result, resultArr, 0);

    return result;
}

JNIEXPORT jdoubleArray JNICALL Java_framexteam_wolframx_calculations_vectors_VectorLibraryJNI_numberMul(JNIEnv* env, jclass cls, jdoubleArray v1, jdouble number) {
    jsize size = env->GetArrayLength(v1);
    jdouble* arr1 = env->GetDoubleArrayElements(v1, nullptr);
    jdoubleArray result = env->NewDoubleArray(size);
    jdouble* resultArr = env->GetDoubleArrayElements(result, nullptr);

    for (jsize i = 0; i < size; i++) {
        resultArr[i] = arr1[i] * number;
    }

    env->ReleaseDoubleArrayElements(v1, arr1, JNI_ABORT);
    env->ReleaseDoubleArrayElements(result, resultArr, 0);

    return result;
}

JNIEXPORT jdoubleArray JNICALL Java_framexteam_wolframx_calculations_vectors_VectorLibraryJNI_numberDiv(JNIEnv * env, jclass cls, jdoubleArray v1, jdouble number) {
    jsize size = env->GetArrayLength(v1);
    jdouble* arr1 = env->GetDoubleArrayElements(v1, nullptr);
    jdoubleArray result = env->NewDoubleArray(size);
    jdouble* resultArr = env->GetDoubleArrayElements(result, nullptr);

    for (jsize i = 0; i < size; i++) {
        resultArr[i] = arr1[i] / number;
    }

     env->ReleaseDoubleArrayElements(v1, arr1, JNI_ABORT);
     env->ReleaseDoubleArrayElements(result, resultArr, 0);

        return result;
    }

JNIEXPORT jdouble JNICALL Java_framexteam_wolframx_calculations_vectors_VectorLibraryJNI_abs(JNIEnv * env, jclass cls, jdoubleArray v1) {
        jsize size = env->GetArrayLength(v1);
        jdouble* arr1 = env->GetDoubleArrayElements(v1, nullptr);
        jdouble result = 0;

        for (jsize i = 0; i < size; i++) {
            result += arr1[i] * arr1[i];
        }

        result = sqrt(result);

        env->ReleaseDoubleArrayElements(v1, arr1, JNI_ABORT);

        return result;
    }

    JNIEXPORT jdouble JNICALL Java_framexteam_wolframx_calculations_vectors_VectorLibraryJNI_angle(JNIEnv * env, jclass cls, jdoubleArray v1, jdoubleArray v2) {
            jdouble* arr1 = env->GetDoubleArrayElements(v1, nullptr);
            jdouble* arr2 = env->GetDoubleArrayElements(v2, nullptr);
            jsize size = env->GetArrayLength(v1);
            jdouble scalarProduct = 0;

            for (jsize i = 0; i < size; i++) {
                scalarProduct += arr1[i] * arr2[i];
            }

            jdouble absV1 = 0;
            jdouble absV2 = 0;

            for (jsize i = 0; i < size; i++) {
                absV1 += arr1[i] * arr1[i];
                absV2 += arr2[i] * arr2[i];
            }

            absV1 = sqrt(absV1);
            absV2 = sqrt(absV2);

            env->ReleaseDoubleArrayElements(v1, arr1, JNI_ABORT);

            return scalarProduct / (absV1 * absV2);
     }
