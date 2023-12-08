/**
 * Interface to represent a Matrix
 * @author Ethan Tobey
 */
package MatrixPackage;

import java.util.Map;
import java.util.Objects;
import RingsPackage.Ring;


public interface Matrix<T> {
    /**
     * Returns the value at given index
     * @param indexes the index at which to retrieve value
     * @return the value at given index
     */
    public T value (Indexes indexes);

    /**
     * Returns the map representation of the matrix
     * @return the map storing the matrix values
     */
    public Map<Indexes, T> getMap();

    /**
     * Returns the size of the matrix
     * @return the size of the matrix
     */
    public Indexes size();

    /**
     * Computes sum of this matrix with another
     * @param other the other matrix to add to this
     * @param ring ring to compute operations
     * @return the sum of this and other matrices
     */
    public Matrix<T> plus(Matrix<T> other, Ring<T> ring);

    /**
     * Computes multiplication of two square matrices
     * @param other other matrix to multiply by this
     * @param ring ring to compute operations
     * @return the product of the two matrices
     */
    public Matrix<T> times(Matrix<T> other, Ring<T> ring);


    /**
     * A class to define exception for an invalid length input for a matrix
     * The class is implemented as a static nested class
     */
    static class InvalidLengthException extends Exception {
        /**
         * Enum to represent the cause of the exception
         */
        public enum Cause {
            ROW,
            COLUMN
        }

        /** the cause of the exception */
        private Cause theCause;
        /** the length of the invalid input */
        private int length;

        /**
         * Constructor to initialize new exception with given cause and length
         * @param cause the cause of the InvalidLengthException
         * @param length the length of the index that caused the exception
         */
        public InvalidLengthException(Cause cause, int length) {
            this.theCause = cause;
            this.length = length;
        }

        /**
         * Getter method for the cause
         * @return the cause of the exception
         */
        public Cause getTheCause() {
            return theCause;
        }       

        /**
         * Getter method for the length
         * @return the length of the index that caused the exception
         */
        public int getLength() {
            return length;
        }

        /**
         * Throws IllegalArgumentException if the input length is less than 0
         * @param cause the cause of the exception
         * @param length the length to assess
         * @return the length, if it was valid
         */
        public static int requireNonEmpty(Cause cause, int length) {
            //require inputs non null
            Objects.requireNonNull(cause, "cause must not be null");
            //if length not positive, throw IllegalArgumentException caused by InvalidLengthException
            if (length <= 0)
                throw new IllegalArgumentException(new InvalidLengthException(cause, length));

            //throw no error and return length if length is positive
            return length;
        }       
    }

    //a nested class to respresent exception from unmatched sizes of matrix arguments
    /**
     * A class to define exception for unmatched sizes of matrix arguments
     * The class is implemented as a static nested class
     */
    static class InconsistentSizeException extends Exception {
        /** index size of first matrix */
        private Indexes thisIndexes;
        /** index size of second matrix */
        private Indexes otherIndexes;

        /**
         * Constructor to initialize new exception with given index inputs
         * @param thisIndexes index size of the first matrix
         * @param otherIndexes index size of the second matrix
         */
        public InconsistentSizeException(Indexes thisIndexes, Indexes otherIndexes) {
            this.thisIndexes = thisIndexes;
            this.otherIndexes = otherIndexes;
        }

        /**
         * Throws InconsistentSizeException if the sizes of the two input matrices
         * @param <T> the type of data stored in the matrices
         * @param thisMatrix the first matrix to compare sizes
         * @param otherMatrix the second matrix to compare sizes
         * @return the size of the matrices if they were equal
         */
        public static <T> Indexes requireMatchingSize(Matrix<T> thisMatrix, Matrix<T> otherMatrix) {
            //require inputs non null
            Objects.requireNonNull(thisMatrix, "thisMatrix must not be null");
            Objects.requireNonNull(otherMatrix, "otherMatrix must not be null");
            //if sizes do not match, throw IllegalArgumentException caused by InconsistentSizeException
            if (thisMatrix.size().compareTo(otherMatrix.size()) != 0)
                throw new IllegalArgumentException(new InconsistentSizeException(thisMatrix.size(), otherMatrix.size()));
            
            //throw no error and return size if the sizes match
            return thisMatrix.size();
        }

        /**
         * Getter method for thisIndexes
         * @return thisIndexes field
         */
        public Indexes getThisIndexes() {
            return thisIndexes;
        }

        /**
         * Getter method for otherIndexes
         * @return otherIndexes field
         */
        public Indexes getOtherIndexes() {
            return otherIndexes;
        }  
    }

    //a nested class to represent exception from matrix not being square
    /**
     * A class to define exception for a matrix not being square
     * The class is implemented as a static nested class
     */
    static class NonSquareException extends Exception {
        /** index size of the matrix */
        private final Indexes indexes;

        /**
         * Constructor to initialize new exception with given index input
         * @param indexes index size of the matrix
         */
        public NonSquareException(Indexes indexes) {
            this.indexes = indexes;
        }

        /**
         * Throws NonSquareException if the input indexes is not on matrix diagonal
         * @param indexes the index to evaluate
         * @return the index input if it was on the matrix diagonal
         */
        public static Indexes requireDiagonal(Indexes indexes) {
            //require inputs non null
            Objects.requireNonNull(indexes, "indexes must not be null");
            //if indexes not diagonal, throw IllegalStateEception caused by NonSquareException
            if (!indexes.areDiagonal())
                throw new IllegalStateException(new NonSquareException(indexes));

            //throw no error and return indexes if it is diagonal
            return indexes;
        }

        /**
         * Getter method for indexes
         * @return indexes field
         */
        public Indexes getIndexes() {
            return indexes;
        }
    }
}
