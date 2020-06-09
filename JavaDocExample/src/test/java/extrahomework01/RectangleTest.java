package extrahomework01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Javadoc for {@link RectangleTest}.
 * 
 * <b>RectangleTest</b> is the class which define the test cases
 * of {@link Rectangle} class and its methods
 * @see SquareTest
 * @author Mehtab Alam Syed
 * @since 1.0
 * @version 1.0
 */
public class RectangleTest {
	
	/**
	 * Test case for Expected value = 4
	 * Create object of class {@link Rectangle}
	 * call method {@link Rectangle#setSize(int, int)}
	 * with width 2 and height 2
	 * 
	 * @see Rectangle#getArea()  
	 * @see RectangleTest#testFailureArea()
	 */
	@Test
	public void testSuccessArea() {
		int expectedValue = 4;
		Rectangle rectangle = new Rectangle();
		rectangle.setSize(2, 2);
		// Expected value is equal to actual value
		assertEquals(expectedValue, rectangle.getArea());
	}

	/**
	 * Test case for Expected value = 5
	 * Create object of class {@link Rectangle}
	 * call method {@link Rectangle#setSize(int, int)}
	 * with width 2 and height 2
	 * 
	 * @see Rectangle#getArea()  
	 * @see RectangleTest#testSuccessArea()
	 */
	@Test
	public void testFailureArea() {
		int expectedValue = 5;
		Rectangle rectangle = new Rectangle();
		rectangle.setSize(2, 2);
		// Expected value is not equal to actual value
		assertEquals(expectedValue, rectangle.getArea());
	}
	
	
	/**
	 * Test case for Expected value = 8
	 * Create object of class {@link Rectangle}
	 * call method {@link Rectangle#setSize(int, int)}
	 * with width 2 and height 2
	 * 
	 * @see Rectangle#getPerimeter()  
	 * @see RectangleTest#testFailurePerimeter()
	 */
	@Test
	public void testSuccessPerimeter() {
		int expectedValue = 8;
		Rectangle rectangle = new Rectangle();
		rectangle.setSize(2, 2);
		// Expected value is equal to actual value
		assertEquals(expectedValue, rectangle.getPerimeter());
	}

	/**
	 * Test case for Expected value = 9
	 * Create object of class {@link Rectangle}
	 * call method {@link Rectangle#setSize(int, int)}
	 * with width 2 and height 2
	 * 
	 * @see Rectangle#getPerimeter()
	 * @see RectangleTest#testSuccessPerimeter()
	 */
	@Test
	public void testFailurePerimeter() {
		int expectedValue = 5;
		Rectangle rectangle = new Rectangle();
		rectangle.setSize(2, 2);
		// Expected value is not equal to actual value
		assertEquals(expectedValue, rectangle.getArea());
	}
}
