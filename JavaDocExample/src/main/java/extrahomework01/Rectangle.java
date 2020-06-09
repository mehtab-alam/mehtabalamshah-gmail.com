package extrahomework01;

/**
 * Javadoc for {@link Rectangle}.
 * 
 * <b>Rectangle</b> with width and height fields
 * and a no argument constructor and parameterized 
 * constructor. Moreover add two methods to calculate 
 * and return area and perimeter of rectangle.
 * It is super class of {@link Square}
 * @see Square
 * @author Mehtab Alam Syed
 * @since 1.0
 * @version 1.0
 */
public class Rectangle {
 
    private int width;
    private int height;
 
    /**
     * Constructor with default value width and height to 1.
     */
    public Rectangle(){
        this.width = 1;
        this.height = 1;
    }
    
    /**
     * Constructor with parameters width and height.
     * @param width  width of rectangle
     * @param height height of rectangle
     */
    public Rectangle(int width, int height){
        this.width = width;
        this.height = height;
    }
 
    /**
     * Set size.
     * @param width  width of rectangle
     * @param height height of rectangle
     */
    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }
 
    /**
     * Set width.
     * @param width  width of rectangle
     */
    public void setWidth(int width) {
		this.width = width;
	}
    /**
     * Set height.
     * @param height height of rectangle
     */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
     * Get width
     * @return {@link #width}
     * @see #getHeight()
     */
    public int getWidth(){
        return width;
    }
 
    /**
     * Get height
     * @return {@link Rectangle#height}
     * @see Rectangle#getWidth()
     */
    public int getHeight(){
        return height;
    }
    
    /**
     * Get area of {@link Rectangle}
     * Formula : width * height
     * @return area
     * @see Square#getArea()
     */
    public int getArea() {
    	return width * height;
    }
    
    /**
     * Get perimter of {@link Rectangle}
     * Formula: (2 * width) + (2 * height)
     * @return perimeter
     * @see Square#getPerimeter()
     */
    public int getPerimeter() {
    	return (2 * width) + (2 * height);
    }
}
