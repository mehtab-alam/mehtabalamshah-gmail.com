package extrahomework01;

/**
 * Javadoc for {@link Square}.
 * 
 * <b>Square</b> with side field
 * and a no argument constructor and parameterized 
 * constructor. Moreover add two methods to calculate 
 * and return area and perimeter of square.
 * Square is subclass of {@link Rectangle}
 * @author Mehtab Alam Syed
 * @since 1.0
 * @version 1.0
 * @see Rectangle
 */
public class Square extends Rectangle {
	int side;
	
	
	 /**
     * Constructor with default value side to 1.
     */
    public Square(){
    	super();
    	this.side = 1;
    }
	/**
     * Constructor with parameter side 
     * Below two lines refers same method.
     * @param side of square
     * @see Rectangle#setSize
     * @see #setSize(int width, int height)
     */
    public Square(int side) {
    	super(side,side);
    	this.side = side;
    }
	
    /**
     * Set size.
     * Below two lines refers same method.
     * @param side of square
     * @see Rectangle#setSize
     * @see #setSize(int width, int height)
     */
    public void setSize(int side) {
    	this.side = 2;
        super.setSize(side, side);
    }
    
    
    /**
     * Get Side
     * @return {@link Square#side}
     */
    public int getSide() {
		return side;
	}
    /**
     * Set Side 
     * @param side Side of the square
     */
	public void setSide(int side) {
		this.side = side;
	}

	/**
     * Gets area of {@link Square}
     * Formula : side * side
     * @return area
     * @see Square#getArea()
     */
    public int getArea() {
    	return side * side;
    }
    
    /**
     * Gets perimter of {@link Square}
     * Formula: 4 * side
     * @return perimeter
     * @see Square#getPerimeter()
     */
    public int getPerimeter() {
    	return 4 * side;
    }
}