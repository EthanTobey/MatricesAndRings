/**
 * A class that tests sum and product operations MatrixMap class
 * @author Ethan Tobey
 */
package TestingPackage;

import static org.junit.Assert.*;
import java.math.BigInteger;
import java.util.Arrays;
import org.junit.Test;
import MatrixPackage.MatrixMap;
import RingsPackage.BigIntegerRing;
import RingsPackage.Polynomial;
import RingsPackage.PolynomialRing;

public class MatrixOperationsTest {
    
    /**
     * Tests the sum method of MatrixMap
     */
    @Test
    public void testSum() {
        //demonstrate MatrixMap<BigInteger>
        BigIntegerRing ring = new BigIntegerRing();
        //sizes do not match
        MatrixMap<BigInteger> bigMap1 = MatrixMap.identity(2, BigInteger.ZERO, BigInteger.ONE);
        MatrixMap<BigInteger> bigMap2 = MatrixMap.identity(3, BigInteger.ZERO, BigInteger.ONE);
        assertThrows(IllegalArgumentException.class, () -> bigMap1.plus(bigMap2, ring));
        //one large matrices
        MatrixMap<BigInteger> oneMap = MatrixMap.identity(1, BigInteger.ZERO, BigInteger.ONE);
        assertEquals("MatrixMap [matrix={Indexes[row=0, column=0]=2}]", oneMap.plus(oneMap, ring).toString());
        //full matrices
        BigInteger[][] matrix = new BigInteger[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = BigInteger.valueOf(10*i + j);
            }
        }
        BigInteger[][] matrix2 = new BigInteger[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix2[i][j] = BigInteger.valueOf(i + j);
            }
        }
        MatrixMap<BigInteger> map1 = MatrixMap.from(matrix);
        MatrixMap<BigInteger> map2 = MatrixMap.from(matrix2);
        assertEquals("MatrixMap [matrix={Indexes[row=0, column=0]=0, Indexes[row=1, column=1]=13, Indexes[row=2, column=2]=26," +
        " Indexes[row=0, column=1]=2, Indexes[row=1, column=2]=15, Indexes[row=0, column=2]=4, Indexes[row=2, column=0]=22," +
        " Indexes[row=1, column=0]=11, Indexes[row=2, column=1]=24}]", map1.plus(map2, ring).toString());

        //demonstrate MatrixMap<Polynomial<BigInteger>>
        PolynomialRing<BigInteger> polyRing = PolynomialRing.instance(ring);
        //sizes do not match
        MatrixMap<Polynomial<BigInteger>> polyMap1 = MatrixMap.identity(2, polyRing.zero(), polyRing.identity());
        MatrixMap<Polynomial<BigInteger>> polyMap2 = MatrixMap.identity(3, polyRing.zero(), polyRing.identity());
        assertThrows(IllegalArgumentException.class, () -> polyMap1.plus(polyMap2, polyRing));
        //one long matrices
        MatrixMap<Polynomial<BigInteger>> onePolyMap = MatrixMap.identity(1, polyRing.zero(), polyRing.identity());
        assertEquals("MatrixMap [matrix={Indexes[row=0, column=0]=[2]}]", onePolyMap.plus(onePolyMap, polyRing).toString());
        //full matrices
        Polynomial<BigInteger> polynomial1 = Polynomial.from(Arrays.asList(BigInteger.ONE, BigInteger.valueOf(2), BigInteger.valueOf(3)));
        MatrixMap<Polynomial<BigInteger>> polyMap3 = MatrixMap.identity(3, polyRing.zero(), polyRing.identity());   
        MatrixMap<Polynomial<BigInteger>> polyMap4 = MatrixMap.constant(3, polynomial1);
        assertEquals("MatrixMap [matrix={Indexes[row=0, column=0]=[2, 2, 3], Indexes[row=1, column=1]=[2, 2, 3], Indexes[row=2, column=2]=[2, 2, 3]," +
        " Indexes[row=0, column=1]=[1, 2, 3], Indexes[row=1, column=2]=[1, 2, 3], Indexes[row=0, column=2]=[1, 2, 3], Indexes[row=2, column=0]=[1, 2, 3]," +
        " Indexes[row=1, column=0]=[1, 2, 3], Indexes[row=2, column=1]=[1, 2, 3]}]", 
        polyMap3.plus(polyMap4, polyRing).toString());
    }

    /**
     * Tests the times method of MatrixMap
     */
    @Test
    public void testTimes() {
        //demonstrate MatrixMap<BigInteger>
        BigIntegerRing ring = new BigIntegerRing();
        //sizes do not match
        MatrixMap<BigInteger> bigMap1 = MatrixMap.identity(2, BigInteger.ZERO, BigInteger.ONE);
        MatrixMap<BigInteger> bigMap2 = MatrixMap.identity(3, BigInteger.ZERO, BigInteger.ONE);
        assertThrows(IllegalArgumentException.class, () -> bigMap1.times(bigMap2, ring));
        //matrix not square
        MatrixMap<BigInteger> unevenMap = MatrixMap.instance(2, 1, (indexes) -> ring.zero());
        assertThrows(IllegalStateException.class, () -> unevenMap.times(unevenMap, ring));
        //one large matrices
        MatrixMap<BigInteger> oneMap = MatrixMap.identity(1, BigInteger.ZERO, BigInteger.ONE);
        assertEquals("MatrixMap [matrix={Indexes[row=0, column=0]=1}]", oneMap.times(oneMap, ring).toString());
        //full matrices
        BigInteger[][] matrix = new BigInteger[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = BigInteger.valueOf(10*i + j);
            }
        }
        BigInteger[][] matrix2 = new BigInteger[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix2[i][j] = BigInteger.valueOf(i + j);
            }
        }
        MatrixMap<BigInteger> map1 = MatrixMap.from(matrix);
        MatrixMap<BigInteger> map2 = MatrixMap.from(matrix2);
        assertEquals("MatrixMap [matrix={Indexes[row=0, column=0]=5, Indexes[row=1, column=1]=68, Indexes[row=2, column=2]=191, Indexes[row=0, column=1]=8," +
        " Indexes[row=1, column=2]=101, Indexes[row=0, column=2]=11, Indexes[row=2, column=0]=65, Indexes[row=1, column=0]=35, Indexes[row=2, column=1]=128}]", 
        map1.times(map2, ring).toString());

        //demonstrate MatrixMap<Polynomial<BigInteger>>
        PolynomialRing<BigInteger> polyRing = PolynomialRing.instance(ring);
        //sizes do not match
        MatrixMap<Polynomial<BigInteger>> polyMap1 = MatrixMap.identity(2, polyRing.zero(), polyRing.identity());
        MatrixMap<Polynomial<BigInteger>> polyMap2 = MatrixMap.identity(3, polyRing.zero(), polyRing.identity());
        assertThrows(IllegalArgumentException.class, () -> polyMap1.times(polyMap2, polyRing));
        //matrix not square
        MatrixMap<Polynomial<BigInteger>> unevenPolyMap = MatrixMap.instance(2, 1, (indexes) -> polyRing.zero());
        assertThrows(IllegalStateException.class, () -> unevenPolyMap.times(unevenPolyMap, polyRing));
        //one large matrices
        MatrixMap<Polynomial<BigInteger>> onePolyMap = MatrixMap.identity(1, polyRing.zero(), polyRing.identity());
        assertEquals("MatrixMap [matrix={Indexes[row=0, column=0]=[1]}]", onePolyMap.times(onePolyMap, polyRing).toString());
        //full matrices
        Polynomial<BigInteger> polynomial1 = Polynomial.from(Arrays.asList(BigInteger.ONE, BigInteger.valueOf(2), BigInteger.valueOf(3)));
        MatrixMap<Polynomial<BigInteger>> polyMap3 = MatrixMap.identity(3, polyRing.zero(), polyRing.identity());   
        MatrixMap<Polynomial<BigInteger>> polyMap4 = MatrixMap.constant(3, polynomial1);
        assertEquals("MatrixMap [matrix={Indexes[row=0, column=0]=[1, 2, 3], Indexes[row=1, column=1]=[1, 2, 3], Indexes[row=2, column=2]=[1, 2, 3]," +
        " Indexes[row=0, column=1]=[1, 2, 3], Indexes[row=1, column=2]=[1, 2, 3], Indexes[row=0, column=2]=[1, 2, 3], Indexes[row=2, column=0]=[1, 2, 3]," +
        " Indexes[row=1, column=0]=[1, 2, 3], Indexes[row=2, column=1]=[1, 2, 3]}]", polyMap3.times(polyMap4, polyRing).toString());
    }
}
