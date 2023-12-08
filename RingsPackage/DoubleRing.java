/**
 * Class to represent a ring for Double data type
 * @author Ethan Tobey
 */
package RingsPackage;

import java.util.Objects;


public class DoubleRing implements Ring<Double> {
    
    /**
     * Returns 0 in Double type
     * @return 0 in type Double
     */
    @Override
    public Double zero() {
        return 0.;
    }

    /**
     * Returns multiplicative identity in Double type
     * @return 1 in type Double
     */
    @Override
    public Double identity() {
        return 1.;
    }

    /**
     * Returns the sum of inputs x and y
     * @param x the first Double to add
     * @param y the second Double to add
     * @return the sum of x and y
     */
    @Override
    public Double sum(Double x, Double y) {
        //check that inputs aren't null
        Objects.requireNonNull(x, "Double must not be null");
        Objects.requireNonNull(y, "Double must not be null");

        return x + y;
    }

    /**
     * Returns the product of inputs x and y
     * @param x the first Double to multiply
     * @param y the second Double to multiply
     * @return the product of x and y
     */
    @Override
    public Double product(Double x, Double y) {
        //check that inputs aren't null
        Objects.requireNonNull(x, "Double must not be null");
        Objects.requireNonNull(y, "Double must not be null");

        return x * y;
    }
}
