/**
 * Class to represent a ring for Polynomial data type
 * @author Ethan Tobey
 */
package RingsPackage;

import java.util.Arrays;
import java.util.Objects;


public class PolynomialRing<T> implements Ring<Polynomial<T>>{
    
     /** stores a ring field for the data in the polnomial this ring operates on*/
    private Ring<T> ring;

    /**
      * A private constructor for PolynomialRing
      * @param ring the ring for the the data in the Polynomial
     */
    private PolynomialRing(Ring<T> ring) {
        this.ring = ring;
    }

    /**
     * Builder method to return a new PolynomialRing
     * @param <S> the type of data within the Polynomial that the ring operates on
     * @param r the ring for the data type of the Polynomial this ring operates on
     * @return a new PolynomialRing
     */
    public static <S> PolynomialRing<S> instance(Ring<S> r) {
        Objects.requireNonNull(r, "Ring must not be null");

        return new PolynomialRing<S>(r);
    }

    /**
     * Returns empty polynomial to serve as multiplicative 0 for type Polynomial
     * @return empty polynomial
     */
    @Override
    public Polynomial<T> zero() {
        return Polynomial.from(Arrays.asList());
    }

    /**
     * Returns Polynomial contianing its ring's multiplicative identity to serve as Polynomial multiplicative identity
     * @return Polynomial contianing multiplicative identity
     */
    @Override
    public Polynomial<T> identity() {
        return Polynomial.from(Arrays.asList(ring.identity()));
    }

    /**
     * Returns the sum of inputs x and y
     * @param x the first Polynomial to add
     * @param y the second Polynomial to add
     * @return the sum of x and y
     */
    @Override
    public Polynomial<T> sum(Polynomial<T> x, Polynomial<T> y) {
        //make sure inputs not null
        Objects.requireNonNull(x, "Polynomial must not be null");
        Objects.requireNonNull(y, "Polynomial must not be null");

        return x.plus(y, ring);
    }

    /**
     * Returns the product of inputs x and y
     * @param x the first Polynomial to multiply
     * @param y the second Polynomail to multiply
     * @return the product of x and y
     */
    @Override
    public Polynomial<T> product(Polynomial<T> x, Polynomial<T> y) {
        //make sure inputs not null
        Objects.requireNonNull(x, "Polynomial must not be null");
        Objects.requireNonNull(y, "Polynomial must not be null");

        return x.times(y, ring);
    }
}
