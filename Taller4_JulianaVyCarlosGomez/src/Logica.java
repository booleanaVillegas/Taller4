import processing.core.PApplet;


public class Logica {
Mundo mundo;
PApplet app;


	public Logica(PApplet app) {
		this.app=app;
		mundo=new Mundo(app);
		
	}
	
	
	public void pintar(){
		mundo.pintar();
		mundo.cosas();
	}
	
	public void press (int x, int y) {
		mundo.press(x,y);
	}


	public void releassed() {
		mundo.relassed();
		
	}


	public void dragged(int x, int y) {
		mundo.dragged(x, y);
		
	}

}
