/**
 * Interface to make ring data types
 * @author Ethan Tobey
 */
package RingsPackage;


public interface Ring<T> {
 
    /**
     * Returns zero value in type T
     * @return zero in type T
     */
    public T zero();

    /**
     * Returns multiplicative identity in type T
     * @return multiplicative identity in type T
     */
    public T identity();

    /**
     * Returns the sum of inputs x and y
     * @param x the first input to add
     * @param y the second input to add
     * @return the sum of x and y
     */
    public T sum(T x, T y);

    /**
     * Returns the product of inputs x and y
     * @param x the first input to multiply
     * @param y the second input to multiply
     * @return the product of x and y
     */
    public T product(T x, T  y);
}