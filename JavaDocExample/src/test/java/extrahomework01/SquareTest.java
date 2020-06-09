package extrahomework01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Javadoc for {@link SquareTest}.
 * 
 * <b>SquareTest</b> is the class which define the test cases
 * of {@link Square} class and its methods
 * @see RectangleTest
 * @author Mehtab Alam Syed
 * @since 1.0
 * @version 1.0
 */
public class SquareTest {

	/**
	 * Test case for Expected value = 4
	 * Create object of class {@link Rectangle}
	 * call method {@link Square#setSize(int)}
	 * with side 2
	 * @see Square#getArea() 
	 * @see SquareTest#testFailureArea() 
	 */
	@Test
	public void testSuccessArea() {
		int expectedValue = 4;
		Square square = new Square();
		square.setSize(2);
		// Expected value is equal to actual value
		assertEquals(expectedValue, square.getArea());
	}

	/**
	 * Test case for Expected value = 5
	 * Create object of class {@link Square}
	 * call method {@link Square#setSize(int)}
	 * with side 2
	 * 
	 * @see Square#getArea()
	 * @see SquareTest#testSuccessArea()  
	 */
	@Test
	public void testFailureArea() {
		int expectedValue = 5;
		Square square = new Square();
		square.setSize(2);
		// Expected value is not equal to actual value
		assertEquals(expectedValue, square.getArea());
	}
	
	
	/**
	 * Test case for Expected value = 8
	 * Create object of class {@link Square}
	 * call method {@link Square#setSize(int)}
	 * with side 2
	 * 
	 * @see Square#getPerimeter() 
	 * @see SquareTest#testFailurePerimeter() 
	 */
	@Test
	public void testSuccessPerimeter() {
		int expectedValue = 8;
		Square square = new Square();
		square.setSize(2);
		// Expected value is equal to actual value
		assertEquals(expectedValue, square.getPerimeter());
	}

	/**
	 * Test case for Expected value = 9
	 * Create object of class {@link Square}
	 * call method {@link Square#setSize(int)}
	 * with side 2
	 * 
	 * @see Square#getPerimeter()
	 * @see SquareTest#testSuccessPerimeter()
	 */
	@Test
	public void testFailurePerimeter() {
		int expectedValue = 5;
		Square square = new Square();
		square.setSize(2);
		// Expected value is not equal to actual value
		assertEquals(expectedValue, square.getArea());
	}

}
