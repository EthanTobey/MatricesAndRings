/**
 * Class to represent a ring for Integer data type
 * @author Ethan Tobey
 */
package RingsPackage;

import java.util.Objects;


public class IntegerRing implements Ring<Integer> {
    
    /**
     * Returns 0 in Integer type
     * @return 0 in type Integer
     */
    @Override
    public Integer zero() {
        return 0;
    }

    /**
     * Returns multiplicative identity in Integer type
     * @return 1 in type Integer
     */
    @Override
    public Integer identity() {
        return 1;
    }

    /**
     * Returns the sum of inputs x and y
     * @param x the first Integer to add
     * @param y the second Integer to add
     * @return the sum of x and y
     */
    @Override
    public Integer sum(Integer x, Integer y) throws NullPointerException{
        //check that inputs aren't null
        Objects.requireNonNull(x, "Integer must not be null");
        Objects.requireNonNull(y, "Integer must not be null");

        return x + y;
    }

    /**
     * Returns the product of inputs x and y
     * @param x the first Integer to multiply
     * @param y the second Integer to multiply
     * @return the product of x and y
     */
    @Override
    public Integer product(Integer x, Integer y) {
        //check that inputs aren't null
        Objects.requireNonNull(x, "Integer must not be null");
        Objects.requireNonNull(y, "Integer must not be null");

        return x * y;
    }
} 
