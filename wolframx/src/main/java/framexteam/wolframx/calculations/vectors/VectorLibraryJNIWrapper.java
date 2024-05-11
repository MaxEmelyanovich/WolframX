package framexteam.wolframx.calculations.operations.vectors;

public class VectorLibraryJNIWrapper implements VectorLibrary {

    @Override
    public double[] sum(double[] v1, double[] v2) {
        return VectorLibraryJNI.sum(v1, v2);
    }

    @Override
    public double[] sub(double[] v1, double[] v2) {
        return VectorLibraryJNI.sub(v1, v2);
    }

    @Override
    public double scalarMul(double[] v1, double[] v2) {
        return VectorLibraryJNI.scalarMul(v1, v2);
    }

    @Override
    public double[] vectorMul(double[] v1, double[] v2) {
        return VectorLibraryJNI.vectorMul(v1, v2);
    }

    @Override
    public double[] numberMul(double[] v1, double number) {
        return VectorLibraryJNI.numberMul(v1, number);
    }

    @Override
    public double[] numberDiv(double[] v1, double number) {
        return VectorLibraryJNI.numberDiv(v1, number);
    }

    @Override
    public double abs(double[] v1) {
        return VectorLibraryJNI.abs(v1);
    }

    @Override
    public double angle(double[] v1, double[] v2) {
        return VectorLibraryJNI.angle(v1, v2);
    }
}