import processing.core.PImage;
import processing.core.PVector;

public class zanahoria{
public PVector posiciones;
Mundo mundo;
private PImage img;

	public zanahoria(int x, int y, Mundo mundo, PImage img) {
		posiciones= new PVector(x,y);
		this.mundo=mundo;
		this.img=img;
		
	}
	
	public void pintar(){
		
		mundo.app.image(img,posiciones.x, posiciones.y);
	}
	

	

}
