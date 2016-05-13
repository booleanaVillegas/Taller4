import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class Zorro extends Thread{

	public PVector posiciones;
	private PVector velocidad;
	public PVector aceleracion;
	private Mundo mundo;
	private long tDescanso;
	private boolean vida;
	private boolean cazando;
	private PImage img;
	float direccion;
	public boolean seleccionado;
	private int millis;
	private short temp;
	public Zorro(Mundo mundo, PImage img) {
		this.mundo = mundo;
		this.img=img;
		posiciones= new PVector();
		posiciones.set((float)(Math.random() * mundo.app.width) ,(float)(Math.random() * mundo.app.height));
		aceleracion= new PVector(mundo.app.random(-1, 1), mundo.app.random(-1, 1));
		tDescanso = this.mundo.TIEMPO_DESCANSO;
		vida = true;
		velocidad= new PVector();
		
		velocidad.set(4, 4);
		velocidad.limit(5);
		direccion = PConstants.PI;
		start();
	}
	

	

	public void pintar() {
		// Area de caza
		mundo.app.noStroke();
		mundo.app.fill(255, 0, 0, 5);
		mundo.app.ellipse(posiciones.x, posiciones.y, 150, 150);
		// Elemento cazador

		mundo.app.image(img, posiciones.x, posiciones.y);
        // tiempo restante de la habilidad sostener zorro
		if(seleccionado){
			mundo.app.text(temp, posiciones.x, posiciones.y-40);
		}
	}


	public void run() {
		while (vida) {
			try {
				if(!seleccionado){
				mover();
				}
				temporizar();
				limites();
				sleep(tDescanso);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void mover() {
		
		for (int i = 0; i < mundo.conejines.size(); i++) {
			// Calcula la direccion hacia la presa
			Conejo p = mundo.conejines.get(i);
			direccion = PApplet.atan2(p.posiciones.y - posiciones.y, p.posiciones.x - posiciones.x);
			// Verifica la distancia hacia la presa y si está viva para perseguirla o moverse por el linezo
			if (PApplet.dist(posiciones.x, posiciones.y, p.posiciones.x, p.posiciones.y) < 150 && p.vida) {
				cazando=true;
				posiciones.x = (int) (posiciones.x + (PApplet.cos(direccion) * (velocidad.x * 1)));
				posiciones.y = (int) (posiciones.y + (PApplet.sin(direccion) * (velocidad.y * 1)));
			} else {
				cazando=false;
			}
			
			
			
			 
		}
			if(cazando==false) {
//				aceleracion = new PVector(mundo.app.random(-1, 1), mundo.app.random(-1, 1));
//				velocidad.add(aceleracion);
//				
//				
//				
//				
//				posiciones.add(velocidad);
				posiciones.x = (int) (posiciones.x + mundo.app.random(-1,3));
				posiciones.y = (int) (posiciones.y +  mundo.app.random(-1,3));
			}
			
			
			

			
		
	}
	public void press(int x, int y){
		for (Zorro zorros : mundo.zorrines) {
			if(zorros!=this){
			if((PApplet.dist(x, y, posiciones.x, posiciones.y)<20) && zorros.seleccionado==false && mundo.EnfriamientoMoverZorro==0){
				seleccionado=true;
				millis=mundo.app.millis();
				temp=3;
				mundo.moverZorro=true;
				mundo.millisZorro=mundo.app.millis();
				mundo.EnfriamientoMoverZorro=6;
				}
			}
		}
		
	}
	//solo puede sostener 3 segundos al zorro
	private void temporizar(){
		if(seleccionado){
			if(mundo.app.millis()-millis>1000){
				millis=mundo.app.millis();
				temp-=1;
			}
			if(temp==0){
				seleccionado=false;
			}
		}
	}
	
	private void limites(){
		if (posiciones.x < 0) {
			posiciones.x = mundo.app.width;
		} else if (posiciones.x > mundo.app.width) {
			posiciones.x = 0;
		}
		if (posiciones.y < 0) {
			posiciones.y = mundo.app.height;
		} else if (posiciones.y > mundo.app.height) {
			posiciones.y = 0;
		}
	}




	public void dragged(int x, int y) {
		if(seleccionado){
		posiciones.x=x;
		posiciones.y=y;
		}
		
	}




	public void relassed() {
		seleccionado=false;
		
	}

	

	
}
