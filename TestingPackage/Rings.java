/**
 * Class to compute ring operations
 * @author Ethan Tobey
 */
package RingsPackage;

import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;


public final class Rings {
    
    /**
     * Reduces a list of type T into a single value of type T based on input operation
     * @param <T> the type of data to reduce
     * @param args list of data to reduce
     * @param zero zero value of type T
     * @param accumulator functional interface to define reduction operation
     * @return reduced value of the input list
     */
    public static <T> T reduce(List<T> args, T zero, BinaryOperator<T> accumulator) {
        //make sure no inputs are null
        Objects.requireNonNull(args, "Null arguments are invalid");
        Objects.requireNonNull(zero, "Null arguments are invalid");
        Objects.requireNonNull(accumulator, "Null arguments are invalid");

        boolean foundAny = false;  //checks if value found
        T result = zero;           //initialize result to zero field

        //loop through args list
        for (T element : args) {
            //make sure element is not null
            Objects.requireNonNull(element, "Null values in list are illegal");

            if (!foundAny) {
                foundAny = true;
                result = element;
            }
            else {
                result = accumulator.apply(result, element);
            }
        }
            return result; 
    }

    /**
     * Computes the sum of a list of values
     * @param <T> the type of data being added
     * @param args the list of values to add
     * @param ring a ring to compute sum operations
     * @return the sum of the input list
     */
    public static final <T> T sum(List<T> args, Ring<T> ring) {
        //throw exception if any arguments are null
        Objects.requireNonNull(args, "Null arguments are invalid");
        Objects.requireNonNull(ring, "Null arguments are invalid");
        
        //if args is empty, just return 0
        if (args.isEmpty())
            return ring.zero();
        
        return reduce(args, ring.zero(), (param1, param2) -> ring.sum(param1, param2));
        /* How the lambda works
         *    - BinaryOperator is a functional interface - means it has one functional method to override
         *    - Since the type of the parameter in this method is a BinaryOperator, it already knows the 
         *      type needed in this space and the lambda infers it... we just say
         *      (param1, param2) -> FUNCTION WE WANT TO DO
         */
    }

    //reduces list of T to the product of the elements
    /**
     * Computes the product of a list of values
     * @param <T> the type of data being multiplied
     * @param args the list of values to multiply
     * @param ring a ring to compute multiplication operations
     * @return the product of the input list
     */
    public static final <T> T product(List<T> args, Ring<T> ring) {
        //throw exception if any arguments are null
        Objects.requireNonNull(args, "Null arguments are invalid");
        Objects.requireNonNull(ring, "Null arguments are invalid");
        
        //if args is empty, just return 0
        if (args.isEmpty())
            return ring.zero();

        return reduce(args, ring.zero(), (param1, param2) -> ring.product(param1, param2));
        //use ring.identity() instead of zero()
    }
}
