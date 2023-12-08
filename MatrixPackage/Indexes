/**
 * Record of Indexes
 * @author Ethan Tobey
 */
package MatrixPackage;

import java.util.Objects;
import java.util.stream.Stream;

public record Indexes(int row, int column) implements Comparable<Indexes> {

    /**
     * Compares which is greater of this Indexes or input Indexes or if they are equal
     * @param o other Indexes to compare to this
     * @return greater than 0 if this is greater, less than 0 if other is greater, or 0 if they are equal
     */
    @Override
    public int compareTo(Indexes o) {
        Objects.requireNonNull(o, "Indexes must not be null");

        return (row() != o.row()) ? row() - o.row() : column() - o.column();
    }
    
    /**
     * Returns value stored at this index in input two dimensional array
     * @param <S> type of data stored in the given array
     * @param matrix the two dimensional array
     * @return value at this indexes in input two dimensional array
     */
    public <S> S value(S[][] matrix) {
        Objects.requireNonNull(matrix, "Matrix must not be null");

        return matrix[row()][ column()];
    }
  
    /**
     * Returns value stored at this index in given matrix
     * @param <S> the type of data stored in the matrix
     * @param matrix the matrix
     * @return value at this indexes in input matrix
     */
    public <S> S value(Matrix<S> matrix) {
        Objects.requireNonNull(matrix, "Matrix must not be null");

        return matrix.value(this);
    }

    //returns true if this index is on matric diagonal
    /**
     * Returns weather or not this Indexes is on matrix diagonal
     * @return true if this is on diagonal, otherwise false
     */
    public boolean areDiagonal() {
        return row() == column();
    }  

    /**
     * Returns a stream of all indexes from first input to second input
     * @param from indexes to start stream from
     * @param to indexes to end stream at
     * @return the stream of indexes
     */
    public static Stream<Indexes> stream(Indexes from, Indexes to) {
        Objects.requireNonNull(from, "Indexes must not be null");
        Objects.requireNonNull(to, "Indexes must not be null");

        return indexStream(from, to);
    }

    /**
     * Helper method to generate stream of finite length from first input to second input
     * @param from indexes to start stream from
     * @param to indeses to end stream at
     * @return the stream of indexes
     */
    private static Stream<Indexes> indexStream(Indexes from, Indexes to) {
        return Stream.iterate(from, index -> {        //produces infinite long stream iterating through column
            int nextCol = index.column() + 1;           //left -> right, then rows top -> bottom
            int nextRow = index.row();

            //if column reaches end of matrix, go back to front, increment row
            if (nextCol > to.column()) {
                nextCol = from.column();
                nextRow++;
            }

            return new Indexes(nextRow, nextCol);
        })
        .limit(((to.row() - from.row() + 1) * (to.column() - from.column() + 1)));  //limits stream size to only outputs within bounds of "to" input
    }

    /**
     * Returns a stream of all indexes from (0,0) to input
     * @param size indexes at which to stop the stream
     * @return the stream of indexes
     */
    public static Stream<Indexes> stream(Indexes size) {
        Objects.requireNonNull(size, "Indexes must not be null");      //null check
        
        return stream(new Indexes(0, 0), size);
    }

    /**
     * Returns a stream of all indexes from (0,0) to input rows and columns
     * @param rows row at which to stop streaming
     * @param columns column at which to stop streaming
     * @return the stream of indexes
     */
    public static Stream<Indexes> stream(int rows, int columns) {
        //subtract 1 to account for 0 value
        return stream(new Indexes(0, 0), new Indexes(rows - 1, columns - 1));
    }
}
