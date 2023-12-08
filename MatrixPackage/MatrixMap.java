/**
 * Class to represent a MatrixMap
 * @author Ethan Tobey
 */
package MatrixPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import RingsPackage.*;

public final class MatrixMap<T> implements Matrix<T> {
    /** stores a map to represent the matrix */
    private final Map<Indexes, T> matrix;
    /** stores size of the matrix */
    private final Indexes size;

    /**
     * A private constructor for MatrixMap
     * @param matrix map of coefficients and indexes to construct matrix from
     */
    private MatrixMap(Map<Indexes, T> matrix) {
        this.matrix = matrix;
        List<Indexes> keyList = new ArrayList<>(matrix.keySet());
        size =  Collections.max(keyList, Indexes::compareTo);       //compute size on initialization instead of during runtime
    }

    /**
     * Returns size field
     * @return size field of this MatrixMap
     */
    @Override
    public Indexes size() {
        return size;
    }

    /**
     * Returns map representation of the matrix
     * @return map field of the matrix
     */
    @Override
    public Map<Indexes, T> getMap() {
        return Map.copyOf(matrix);
    }

    /**
     * Overrides String representation of this MatrixMap
     * @return String representationof this MatrixMap
     */
    @Override
    public String toString() {
        return "MatrixMap [matrix=" + matrix + "]";
    }

    /**
     * Returns value in this MatrixMap at given index
     * @param indexes the index at which to retrieve value
     * @return the value at the given index
     */
    @Override
    public T value(Indexes indexes) {
        //ensure indexes not null
        Objects.requireNonNull(indexes, "indexes must not be null");

        return matrix.get(indexes);
    }


    /**
     * Returns value in this MatrixMap at given row and column
     * @param row row at which to retreive value
     * @param column column at which to retrieve value
     * @return the value at the given row and column
     */
    public T value(int row, int column) {
        //ensure row and column inputs are valid
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, row);
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.COLUMN, column);

        return value(new Indexes(row, column));
    }

    /**
     * A builder method to return a new instance of MatrixMap
     * MatrixMap has specified rows and columns count
     * values of MatrixMap are determined by the functional interface input
     * @param <S> the type of data stored in the MatrixMap
     * @param rows the number of rows for the MatrixMap
     * @param columns the number of columns for the MatrixMap
     * @param valueMapper functional interface to define values of the MatrixMap
     * @return new MatrixMap built based on the inputs
     */
    public static <S> MatrixMap<S> instance(int rows, int columns, Function<Indexes, S> valueMapper)   {
        //make sure inputs not null
        Objects.requireNonNull(valueMapper, "valueMapper must not be null");
        //require rows and columns to be greater than 0
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, rows);
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.COLUMN, columns);

        //generate list of indexes from rows and column sizes
        List<Indexes> indexList = Indexes.stream(rows, columns).collect(Collectors.toList());
        //map to hold indexes and values
        Map<Indexes, S> map = new HashMap<>();

        //add indexes and associated values to the map
        for (Indexes index : indexList) {
            map.put(index, valueMapper.apply(index));
        }

        return new MatrixMap<>(map);   //returning copy
    }

    /**
     * A builder method to return a new instance of MatrixMap
     * MatrixMap has size specified by input
     * values of MatrixMap are determined by the functional interface input
     *@param <S> the type of data stored in the MatrixMap
     * @param size the size for the MatrixMap
     * @param valueMapper functional interface to define values of the MatrixMap
     * @return new MatrixMap built based on the inputs
     */
    public static <S> MatrixMap<S> instance(Indexes size, Function<Indexes, S> valueMapper) {
        //make sure inputs not null
        Objects.requireNonNull(size, "size must not be null");
        Objects.requireNonNull(valueMapper, "valueMapper must not be null");

        return MatrixMap.instance(size.row() + 1, size.column() + 1, valueMapper);     //+ 1 to account for 0 row/col
    }

    /**
     * A builder method to return a new square instance of MatrixMap with one constant value throughout
     * @param <S> the type of data stored in the MatrixMap
     * @param size the size for the square MatrixMap
     * @param value value to fill the MatrixMap with
     * @return new MatrixMap with constant value throuhgout
     */
    public static <S> MatrixMap<S> constant(int size, S value) {
        //ensure value not null
        Objects.requireNonNull(value, "value must not be null");
        //make sure size is valid
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, size);

        return MatrixMap.instance(size, size, (indexes) -> value);    //pass in value input for all indexes
    } 

    /**
     * A bulder method to return a new identity MatrixMap
     * @param <S> the type of data stored in the MatrixMap
     * @param size the size for the MatrixMap
     * @param zero zero value for the data type stored in the MatrixMap
     * @param identity multiplicative identity value for the data type stored in the MatrixMap
     * @return new MatrixMap of identity matrix
     */
    public static <S> MatrixMap<S> identity(int size, S zero, S identity) {
        //make sure inputs not null
        Objects.requireNonNull(zero, "zero must not be null");
        Objects.requireNonNull(identity, "identity must not be null");
        //make sure size is valid
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, size);

        return MatrixMap.instance(size, size, (indexes) -> assignIdentityValue(indexes, zero, identity));
    } 

    //private helper method to set diagonal of I matrix to identity and rest to zero
    /**
     * Helper method to set values of identity matrix depending on their location
     * @param <S> the type of data for the assignments
     * @param index the index at which to make the assignment
     * @param zero zero value for possible assignment
     * @param identity multiplicative identity value for possible assignment
     * @return the value for the given index in identity matrix
     */
    private static <S> S assignIdentityValue(Indexes index, S zero, S identity) {
        return (index.areDiagonal()) ? identity : zero;
    }

    /**
     * A builder method to return a new MatrixMap based on given two dimensional array
     * @param <S> the type of data stored in the MatrixMap
     * @param matrix two dimensional array of values for the MatrixMap
     * @return new MatrixMap of given values
     */
    public static <S> MatrixMap<S> from(S[][] matrix) {
        //ensure matrix not null
        Objects.requireNonNull(matrix, "matrix must not be null");

        return MatrixMap.instance(matrix.length, matrix[0].length, (indexes) -> indexes.value(matrix));
    }

    /**
     * Computes the sum of two matrices
     * @param other matrix to add to this
     * @param ring ring to compute operations
     * @return the sum of the two matrices
     */
    @Override
    public Matrix<T> plus(Matrix<T> other, Ring<T> ring) {
        //ensure no null values
        Objects.requireNonNull(other, "Input matrix must not be null");
        Objects.requireNonNull(ring, "Ring must not be null");
        //ensure siszes match
        InconsistentSizeException.requireMatchingSize(this, other);

        //return new matrix, with entries defined as sum of entries of this and other
        return MatrixMap.instance(size(), (indexes) -> ring.sum(value(indexes), other.value(indexes)));  //applies sum input by BinaryOperator
    }
    
    /**
     * Computes the product of two matrices
     * @param other matrix to multiply by this
     * @param ring ring to compute operations
     * @return the product of the two matrices
     */
    @Override
    public Matrix<T> times(Matrix<T> other, Ring<T> ring) {
        //ensure no null values
        Objects.requireNonNull(other, "Input matrix must not be null");
        Objects.requireNonNull(ring, "Ring must not be null");
        //ensure sizes match and is square
        InconsistentSizeException.requireMatchingSize(this, other);
        NonSquareException.requireDiagonal(this.size());

        //return new matrix, with entreis defined as product of cols * rows
        return MatrixMap.instance(size(), (indexes) -> productAtIndex(other, ring, indexes));
    }

    /**
     * Helper method to calculate the produt at a given index in matrix multiplication
     * @param other other matrix to multiply wiht
     * @param ring ring to compute operations
     * @param indexes index for which to compute product
     * @return the product at given index
     */
    private T productAtIndex(Matrix<T> other, Ring<T> ring, Indexes indexes) {        
        //a list to hold the products from the iterations
        List<T> productsList = new ArrayList<>();
        //multiply row * column from entry 0 to entry n
        for (int i = 0; i <= this.size().row(); i++) {
            productsList.add(ring.product(this.value(new Indexes(indexes.row(), i)), other.value(new Indexes(i, indexes.column()))));
        }
        //return sum of all products
        return Rings.sum(productsList, ring);
    }

    /**
     * Convert this MatrixMap to SparseMatrix
     * @param ring ring input for SparseMatrix
     * @return SparseMatrix representation of this MatrixMap
     */
    public SparseMatrix<T> toSparseMatrix(Ring<T> ring) {
        return SparseMatrix.instance(size(), (indexes) -> value(indexes), ring);
    }
}
