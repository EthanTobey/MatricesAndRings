/**
 * Class to represent a ring for Matrix data type
 * @author Ethan Tobey
 */
package RingsPackage;

import java.util.Objects;
import MatrixPackage.Matrix;
import MatrixPackage.MatrixMap;


public class MatrixRing<T> implements Ring<Matrix<T>> {

    /** stores a ring field for the data in the matrix this ring operates on*/
    private Ring<T> ring;
    /** the size of the matrix that this ring operates on */
    private int size;

    /**
     * A private constructor for MatrixRing
     * @param ring the ring for the the data in the matrix
     * @param size the size of the matrix for this ring
     */
    private MatrixRing(Ring<T> ring, int size) {
        this.ring = ring;
        this.size = size;
    }

    /**
     * Builder method to return a new MatrixRing
     * @param <S> the type of data within the matrix that the ring operates on
     * @param r the ring for the data type of the matrix this ring operates on
     * @param size the size of the matrix this ring operates on
     * @return a new MatrixRing
     */
    public static <S> MatrixRing<S> instance(Ring<S> r, int size) {
        Objects.requireNonNull(r, "Ring must not be null");

        return new MatrixRing<S>(r, size);
    }
    
    /**
     * Returns 0 matrix with data type T and correct size
     * @return 0 matrix with size and data type specified by this ring
     */
    @Override
    public Matrix<T> zero() {
        return MatrixMap.constant(size, ring.zero());
    }

    /**
     * Returns identity matrix with data type T and correct size
     * @return identity matrix with size and data type specified by this ring
     */
    @Override
    public Matrix<T> identity() {
        return MatrixMap.identity(size, ring.zero(), ring.identity());
    }

    /**
     * Returns the sum of inputs x and y
     * @param x the first Matrix to add
     * @param y the second Matrix to add
     * @return the sum of x and y
     */
    @Override
    public Matrix<T> sum(Matrix<T> x, Matrix<T> y) {
        Objects.requireNonNull(x, "input must not be null");
        Objects.requireNonNull(y, "inputs must not be null");

        return x.plus(y, ring);
    }

    /**
     * Returns the product of inputs x and y
     * @param x the first Matrix to multiply
     * @param y the second Matrix to multiply
     * @return the product of x and y
     */
    @Override
    public Matrix<T> product(Matrix<T> x, Matrix<T> y) {
        Objects.requireNonNull(x, "input must not be null");
        Objects.requireNonNull(y, "input must not be null");

        return x.times(y, ring);
    }
}
