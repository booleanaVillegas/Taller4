import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class Zorro extends Thread{

	public PVector posiciones;
	private PVector velocidad;
	public PVector aceleracion;
	private PVector destino;
	private Mundo mundo;
	private long tDescanso;
	private boolean vida;
	private boolean cazando;
	private PImage img;
	float direccion;
	public boolean seleccionado;
	private int millis;
	private short temp;
	  Conejo criaturitaBlanca;
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
				buscarVictima();
				sleep(tDescanso);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void mover() {
		aceleracion = new PVector(mundo.app.random(-1, 1), mundo.app.random(-1, 1));		
		velocidad.limit(5);

		if(cazando){
			aceleracion= PVector.sub(destino, posiciones);
			aceleracion.setMag(0.5F);
			velocidad.add(aceleracion);
			
			
				posiciones.add(velocidad);
				if (PVector.dist(posiciones, destino) < 15) {
					mundo.conejines.remove(criaturitaBlanca);
					cazando = false;
				}
			}
		if(!cazando){
				velocidad.add(aceleracion);
				posiciones.add(velocidad);
		}
	
	}
	private void buscarVictima(){
		
		for (int i = 0; i < mundo.conejines.size(); i++) {
			Conejo c = mundo.conejines.get(i);
			if(PApplet.dist(posiciones.x, posiciones.y, c.posiciones.x, c.posiciones.y)<100){
				criaturitaBlanca=c;
				cazando=true;
				
				destino=c.posiciones;
				break;
			}else{cazando=false; criaturitaBlanca=null;}
		}
		
	}
	public void press(int x, int y){
		for (Zorro zorros : mundo.zorrines) {
			if(zorros!=this){
			if((PApplet.dist(x, y, posiciones.x, posiciones.y)<50) && zorros.seleccionado==false && mundo.EnfriamientoMoverZorro==0){
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
