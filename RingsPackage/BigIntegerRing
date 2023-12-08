/**
 * Class to represent a ring for BigInteger data type
 * @author Ethan Tobey
 */
package RingsPackage;

import java.math.BigInteger;
import java.util.Objects;


public class BigIntegerRing implements Ring<BigInteger> {
    
    /**
     * Returns 0 in BigInteger type
     * @return 0 in type BigInteger
     */
    @Override
    public BigInteger zero() {
        return BigInteger.ZERO;
    }

    /**
     * Returns multiplicative identity in BigInteger type
     * @return 1 in type BigInteger
     */
    @Override
    public BigInteger identity() {
        return BigInteger.ONE;
    }

    /**
     * Returns the sum of inputs x and y
     * @param x the first BigInteger to add
     * @param y the second BigInteger to add
     * @return the sum of x and y
     */
    @Override
    public BigInteger sum(BigInteger x, BigInteger y) {
        //make sure inputs are not null
        Objects.requireNonNull(x, "BigInteger must not be null");
        Objects.requireNonNull(y, "BigInteger must not be null");

        return x.add(y);
    }

    /**
     * Returns the product of inputs x and y
     * @param x the first BigInteger to multiply
     * @param y the second BigInteger to multiply
     * @return the product of x and y
     */
    @Override
    public BigInteger product(BigInteger x, BigInteger y) {
        //make sure inputs are not null
        Objects.requireNonNull(x, "BigInteger must not be null");
        Objects.requireNonNull(y, "BigInteger must not be null");

        return x.multiply(y);
    }
}
