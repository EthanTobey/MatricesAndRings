/**
 * Class to represent a SparseMatrix       
 * @author Ethan Tobey
 */

 //NOTE: may want to add description of how this class is more efficient than MatrixMap for high 0 matrices
package MatrixPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import RingsPackage.Ring;
import RingsPackage.Rings;

public final class SparseMatrix<T> implements Matrix<T> {
    /** holds a map to represent the matrix */
    private final Map<Indexes, T> matrix;
    /** holds size field of matrix */
    private final Indexes size;
    /** ring for type stored in matrix */
    private final Ring<T> ring;


    /**
     * A private constructor for SparseMatrix
     * @param matrix map of coefficients and indexes to construct matrix from
     * @param size the size of the SparseMatrix
     * @param ring ring for operations and zero values in the matrix
     */
    private SparseMatrix(Map<Indexes, T> matrix, Indexes size, Ring<T> ring) {
        this.matrix = Map.copyOf(matrix);
        this.size = size;
        this.ring = ring;
    }

    /**
     * Overrides String representation of this SparseMatrix
     * @return String representationof this SparseMatrix
     */

     //add clarification on how string format is ordered
    @Override
    public String toString() {
        //make sorted list of keys of matrix
        List<Indexes> sortedKeySet = new ArrayList<>(matrix.keySet());
        Collections.sort(sortedKeySet); 
        StringBuilder builder = new StringBuilder("SparseMatrix [matrix={");  //compile the toString

        //append each value and its index in specific format
        for (Indexes index : sortedKeySet) {
            builder.append("Indexes[row=");      //using StringBuilder.append to avoid extra string generation
            builder.append(index.row());              
            builder.append(", column=");     //NOTE:could actually chain them like builder.append().append().append() in one line
            builder.append(index.column());
            builder.append("]=");
            builder.append(value(index));
            builder.append(", ");
        }

        //remove final ", " from end if keySet had values
        if (!sortedKeySet.isEmpty())
            builder.delete(builder.length() - 2, builder.length());
        builder.append("}]");
        return builder.toString();
    }

    /**
     * Returns value in this SparseMatrix at given index
     * @param indexes the index at which to retrieve value
     * @return the value at the given index
     */
    @Override
    public T value(Indexes indexes) {
        //ensure indexes not null
        Objects.requireNonNull(indexes, "indexes must not be null");

        if (indexes.compareTo(size) <= 0)
            return matrix.getOrDefault(indexes, ring.zero());
        
        return null;
    }

    /**
     * Returns size field
     * @return size field of this SparseMatrix
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
     * A builder method to return a new instance of SparseMatrix
     * SparseMatrix has size specified by input
     * values of SparseMatrix are determined by the functional interface input
     *@param <S> the type of data stored in the MatrixMap
     * @param size the size for the SparseMatrix
     * @param valueMapper functional interface to define values of the SparseMatrix
     * @param ring ring for the SparseMatrix
     * @return new SparseMatrix built based on the inputs
     */
    public static <S> SparseMatrix<S> instance(Indexes size, Function<Indexes, S> valueMapper, Ring<S> ring) {
        //ensure inputs not null
        Objects.requireNonNull(size, "size must not be null");
        Objects.requireNonNull(valueMapper, "valueMapper must not be null");
        Objects.requireNonNull(ring, "zero must not be null");
       
        return instance(size.row() + 1, size.column() + 1, valueMapper, ring);
    }

    /**
     * A builder method to return a new instance of SparseMatrix
     * SparseMatrix has specified rows and columns count
     * values of SparseMatrix are determined by the functional interface input
     * @param <S> the type of data stored in the SparseMatrix
     * @param rows the number of rows for the SparseMatrix
     * @param columns the number of columns for the SparseMatrix
     * @param valueMapper functional interface to define values of the SparseMatrix
     * @param ring ring for the SparseMatrix
     * @return new SparseMatrix built based on the inputs
     */
    public static <S> SparseMatrix<S> instance(int rows, int columns, Function<Indexes, S> valueMapper, Ring<S> ring) {
         //make sure inputs not null
        Objects.requireNonNull(valueMapper, "valueMapper must not be null");
        Objects.requireNonNull(ring, "zero must not be null");
        //require rows and columns to be greater than 0
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, rows);
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.COLUMN, columns);

        //generate list of indexes from rows and column sizes
        List<Indexes> indexList = Indexes.stream(rows, columns).collect(Collectors.toList());
        //map to hold indexes and values
        Map<Indexes, S> map = new HashMap<>();

        //use private helper to add all to map
        addToMap(map, indexList, ring, valueMapper);

        //-1 to account for row/col # 0
        return new SparseMatrix<>(map, new Indexes(rows - 1, columns - 1), ring);
    }

    /**
     * A builder method to return a new square instance of SparseMatrix with one constant value throughout
     * @param <S> the type of data stored in the SparseMatrix
     * @param size the size for the square SparseMatrix
     * @param value value to fill the SparseMatrix with
     * @param ring ring for the SparseMatrix
     * @return new SparseMatrix with constant value throuhgout
     */
    public static <S> SparseMatrix<S> constant(int size, S value, Ring<S> ring) {
        //ensure value not null
        Objects.requireNonNull(value, "value must not be null");
        Objects.requireNonNull(ring, "zero must not be null");
        //make sure size is valid
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, size);

        return instance(size, size, (indexes) -> value, ring);    //pass in value input for all indexes
    }

    /**
     * A bulder method to return a new identity SparseMatrix
     * @param <S> the type of data stored in the SparseMatrix
     * @param size the size for the SparseMatrix
     * @param ring ring for the SparseMatrix
     * @return new SparseMatrix of identity matrix
     */
    public static <S> SparseMatrix<S> identity(int size, Ring<S> ring) {
        //make sure inputs not null
        Objects.requireNonNull(ring.zero(), "zero must not be null");
        Objects.requireNonNull(ring.identity(), "identity must not be null");
        //make sure size is valid
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, size);

        return instance(size, size, (indexes) -> assignIdentityValue(indexes, ring.zero(), ring.identity()), ring);
    }

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
     * A builder method to return a new SparseMatrix based on given two dimensional array
     * @param <S> the type of data stored in the SparseMatrix
     * @param matrix two dimensional array of values for the SparseMatrix
     * @param ring ring for the SparseMatrix
     * @return new SparseMatrix of given values
     */
    public static <S> SparseMatrix<S> from(S[][] matrix, Ring<S> ring) {
        //ensure matrix not null
        Objects.requireNonNull(matrix, "matrix must not be null");

        return instance(matrix.length, matrix[0].length, (indexes) -> indexes.value(matrix), ring);
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

        //if other is not SparseMatrix, compute the long way
        if (!(other instanceof SparseMatrix))
            return instance(size(), (indexes) -> ring.sum(value(indexes), other.value(indexes)), ring);

        //typecast other to sparseMatrix
        SparseMatrix<T> otherSparse = (SparseMatrix<T>)other;
        //list to hold union of valid indices of both matrices
        List<Indexes> indexList = unionOfKeysets(otherSparse);
        //map to hold indexes and values
        Map<Indexes, T> map = new HashMap<>();

        //add indexes and associated values to the map
        addToMap(map, indexList, ring, (index) -> ring.sum(value(index), other.value(index)));

        return new SparseMatrix<T>(map, size(), ring);
    }

    /**
     * Helper method to find union of keySets of this and other SparseMatrix
     * @param other other sparseMatrix for union operation
     * @return list of the union of the keysets of the matrices
     */
    private List<Indexes> unionOfKeysets(SparseMatrix<T> other) {
        Set<Indexes> resultSet = new HashSet<>(matrix.keySet());
        resultSet.addAll(other.getMap().keySet());
        return new ArrayList<>(resultSet);
    }

    //helper method to add values to map
    /**
     * Helper method to add mappings for a list of indexes to a map
     * Values are given by a functional interface input
     * @param <S> type of the values
     * @param map map to add values to
     * @param indexList list of Indexes to add mappings for
     * @param ring ring to compute operations
     * @param valueMapper functional interface to determine values at given index
     */
    private static <S> void addToMap(Map<Indexes, S> map, List<Indexes> indexList, Ring<S> ring, Function<Indexes, S> valueMapper) {
        for (Indexes index : indexList) {
            S value = valueMapper.apply(index);
            //only add to final if does not result in zero
            if (value != ring.zero())
                map.put(index, value);
        }
    }

    /**
     * Computes the product of two matrices
     * @param other matrix to multiply by this
     * @param ring ring to compute operations
     * @return the product of the two matrices
     */
    @Override
    public Matrix<T> times(Matrix<T> other, Ring<T> ring) {
        //ensure no null vlaues
        Objects.requireNonNull(other, "Input matrix must not be null");
        Objects.requireNonNull(ring, "Ring must not be null");
        //ensure sizes match and is square
        InconsistentSizeException.requireMatchingSize(this, other);
        NonSquareException.requireDiagonal(size());
        //list of all indexes within size of matrix
        List<Indexes> fullIndexesList = Indexes.stream(size()).toList();
        //list to hold union of valid indices of both matrices
        List<Indexes> intersectionIndexList = intersectionKeysets(other);
        
        //map to hold indexes and values
        Map<Indexes, T> map = new HashMap<>();

        //add indexes and associated values to the map
        addToMap(map,fullIndexesList, ring, (index) -> productAtIndex(other, ring, index, intersectionIndexList));

        return new SparseMatrix<T>(map, size(), ring);
    }

    /**
     * Helper method to find intersection of keySets of this and other SparseMatrix
     * @param other other sparseMatrix for union operation
     * @return list of the intersection of the keysets of the matrices
     */
    private List<Indexes> intersectionKeysets(Matrix<T> other) {
        List<Indexes> intersection = new ArrayList<>(matrix.keySet());
        intersection.retainAll(other.getMap().keySet());      //remove all entries that aren't also in other's nonZeroEntries
        return intersection;
    }

    /**
     * Helper method to calculate the produt at a given index in matrix multiplication
     * @param other other matrix to multiply wiht
     * @param ring ring to compute operations
     * @param indexes index for which to compute product
     * @return the product at given index
     */
    private T productAtIndex(Matrix<T> other, Ring<T> ring, Indexes indexes, List<Indexes> intersectionIndexList) { 
        //a list to hold the products from the iterations
        List<T> productsList = new ArrayList<>();
        //multiply row * column from entry 0 to entry n
        for (int i = 0; i <= this.size().row(); i++) {
            if (intersectionIndexList.contains(new Indexes(indexes.row(), i)) && intersectionIndexList.contains(new Indexes(i, indexes.column())))
                productsList.add(ring.product(this.value(new Indexes(indexes.row(), i)), other.value(new Indexes(i, indexes.column()))));
        }
        //return sum of all products
        T x = Rings.sum(productsList, ring);
        System.out.println("Index: " + indexes + " value: " + x);
        return x;
    }

    /**
     * Convert this SparseMatrix to MatrixMap
     * @return MatrixMap representation of this SparseMatrix
     */
    public MatrixMap<T> toMatrixMap() {
       return MatrixMap.instance(size(), (indexes) -> value(indexes));  //applies sum input by BinaryOperator
    }   
}
