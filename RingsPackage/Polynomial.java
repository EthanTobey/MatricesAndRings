/**
 * Class to represent a Polynomial data type
 * @author Ethan Tobey
 */
package RingsPackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;


public class Polynomial<T> implements Iterable<T>{
    
    /** list field of coefficients of the polynomial */
    private List<T> coefficients;

    /**
     * A private constructor for Polynmomial
     * @param coefficients list of coefficients in order from highest to lowest degree
     */
    private Polynomial(List<T> coefficients) {
        this.coefficients = new ArrayList<T>(coefficients);   //using ArrayList constructor to avoid copying coefficients list
    }

    /**
     * A builder method to return a new Polynomial
     * @param <S> the type of data stored as coefficients in the Polynomial
     * @param coefficients the list of coefficients in order from highest to lowest degree
     * @return a new Polynomial
     */
    public static final <S> Polynomial<S> from(List<S> coefficients) {
        Objects.requireNonNull(coefficients, "Coefficients list must not be null");

        return new Polynomial<S>(coefficients);    //maybe pass in coefficients as List.copy for immutable   
    }

    /**
     * Returns an immutable copy of coefficients of this Polynomial
     * @return an immutable copy of the coefficients of this Polynomial
     */
    public List<T> getCoefficients() {
       return new ArrayList<>(coefficients);
    }

    
    /**
     * Overrides String representation to be the coefficients list of this Polynomial
     * @return String representation of this Polynomial
     */
    @Override
    public String toString() {
        return coefficients.toString();
    }

    /**
     * Return an iterator for the coefficients list
     * @return the iterator for the coefficients list
     */
    @Override
    public Iterator<T> iterator() {
        return coefficients.iterator();
    }

    /**
     * Return a ListIterator for the coefficients list starting at a given index
     * @param i the index at which to start the ListIterator
     * @return the ListIterator for the coefficients list
     */
    public ListIterator<T> listIterator(int i) {
        //check that input is valid else throw exception
        if (i < 0 || i > getCoefficients().size())
            throw new IndexOutOfBoundsException(i);

        return getCoefficients().listIterator(i);
    }

    /**
     * Computes the sum of two Polynomials
     * @param other other Polynomial to add to this
     * @param ring ring to compute sum operations
     * @return the sum of this and other Polynomials
     */
    public Polynomial<T> plus(Polynomial<T> other, Ring<T> ring) {
        //check for no null inputs
        Objects.requireNonNull(other, "input polynomial must not be null");
        Objects.requireNonNull(ring, "input ring must not be null");

        List<T> sum;                         //final sum of inputs
        List<T> smallerPolynomial;           //stores copy of smaller of two inputs

        //record which is shorter polynomial and set sum equal to coefficients list of larger polynomial
        if (getCoefficients().size() > other.getCoefficients().size()){
            //initialize as new ArrayLists for O(1) access regardless of if coefficients implemented as LinkedList or ArrayList
            smallerPolynomial = new ArrayList<>(other.getCoefficients());
            sum = new ArrayList<>(getCoefficients());
        }
        else {
            //initialize as new ArrayLists for O(1) access regardless of if coefficients implemented as LinkedList or ArrayList
            smallerPolynomial = new ArrayList<>(this.getCoefficients());
            sum = new ArrayList<>(other.getCoefficients());
        }

        ListIterator<T> smallerIterator = smallerPolynomial.listIterator(0); //iterator to trace through small list
        ListIterator<T> sumIterator = sum.listIterator(0);                   //iterator to trace through sum list

        //add coefficients of smaller polynomial into sum
        while (smallerIterator.hasNext()) {
            sumIterator.set(ring.sum(sumIterator.next(), smallerIterator.next()));
        }
        return from(sum);
    }

    /**
     * Computes the product of two Polynomials
     * @param other other Polynomial to multiply by this
     * @param ring ring to compute multiplication operations
     * @return the product of this and other Polynomial
     */
    public Polynomial<T> times(Polynomial<T> other, Ring<T> ring) {
        //check for no null inputs
        Objects.requireNonNull(other, "input polynomial must not be null");
        Objects.requireNonNull(ring, "input ring must not be null");

        List<T> resultList = new ArrayList<>();                                        //holds results of multiplication
        int size = getCoefficients().size() + other.getCoefficients().size() - 1;      //size of final product
        
        //iterate once for each element in final product
        for (int iteration = 0, offset = 0; iteration < size; iteration++) {
            //if iterates outside bounds of input lists' sizes
            offset = calculateOffset(iteration, other.getCoefficients().size());
            
            //initialize iterators for this and other polynomial
            ListIterator<T> pIterator = listIterator(offset);
            ListIterator<T> qIterator = other.listIterator(1 + iteration - offset);
            //add 1 to input b/c previous() looks 1 ahead of given index compared to next() 
            List<T> productsList = new ArrayList<>();                                   //list to store the products
            
            //do multiplication for current index
            while (canIncrementTimes(pIterator, qIterator)) {                      //could reduce complexity here with a helper method - Complexity TELLS YOU that there can be made a change
                productsList.add(ring.product(pIterator.next(), qIterator.previous()));
            }
            //compute sum of products and add it to result lst
            resultList.add(Rings.sum(productsList, ring));
        }
        return new Polynomial<>(resultList);
    }

    /**
     * Helper method to calculate index offset during Polynomial multiplication
     * @param iteration iteration number of the multiplication
     * @param listSize size of the list being iterated accross
     * @return the offset value
     */
    private int calculateOffset(int iteration, int listSize) {
        int offset = 0;

        //if offset exceeds size of the list
        if (iteration >= listSize)
                offset = iteration - listSize + 1;

        return offset;
    }

    /**
     * Helper method to check if iterators can increment during multiplication process
     * @param forward the ListIterator moving forward
     * @param backward the ListIterator moving backward
     * @return true if the iterators can increment, otherwise returns false
     */
    private boolean canIncrementTimes(ListIterator<T> forward, ListIterator<T> backward) {
        return forward.hasNext() && backward.hasPrevious();
    }
}
