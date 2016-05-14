

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Conejo extends Thread{
	public PVector posiciones;
	public PVector velocidad;
	public PVector aceleracion;
	private Mundo mundo;
	private long tDescanso;
	public boolean vida;
	private int dormir;
	public boolean dormido;
	private int millis;
	private PImage img;

	PApplet app;
	private boolean huyendo;
	public Conejo(Mundo mundo, PApplet app, PImage img) {
		this.mundo = mundo;
		this.img=img;
		posiciones= new PVector();
		posiciones.set((float)(Math.random()*mundo.app.height),(float)(Math.random()*mundo.app.height));
		velocidad= new PVector();
		aceleracion= new PVector(app.random(-1, 1), app.random(-1, 1));
		tDescanso = mundo.TIEMPO_DESCANSO;
		vida =  true;
		
		this.app=app;
		dormir=0;
		start();
	}
	
	public void pintar() {
		app.image(img, posiciones.x, posiciones.y);		
	}
	public void run() {
		while(vida){
			try {
				mover();
				dormido();
				sleep(tDescanso+dormir);
				limites();
			} catch (InterruptedException e) {			
				vida = false;				
			}
		}
	}
	public void press(int x, int y){
		if(mundo.EnfriamientoDormirConejo==0){
		if(PApplet.dist(x, y, posiciones.x, posiciones.y)<20){
			dormir=2000;
			millis=app.millis();
			
			dormido=true;
			mundo.DormirConejo=true;
			mundo.EnfriamientoDormirConejo=6;
			mundo.millis=app.millis();
			
		}
		}
	}
	
	private void dormido(){
		if(dormido){
			if(app.millis()-millis>dormir){
				dormir=0;
				dormido=false;
			}
		}
	}
	private void mover() {
		aceleracion = new PVector(app.random(-1, 1), app.random(-1, 1));		
		velocidad.limit(5);
		for (int i = 0; i < mundo.zanahorias.size(); i++) {
			zanahoria zanahorin = mundo.zanahorias.get(i);
		
			if (PVector.dist(posiciones, zanahorin.posiciones) < 200 && !huyendo) {
				aceleracion=PVector.sub(zanahorin.posiciones,posiciones);
				aceleracion.setMag(0.5F);
				velocidad.add(aceleracion);
				posiciones.add(velocidad);
			}
		}
		for (int i = 0; i < mundo.zorrines.size(); i++) {
			Zorro z = mundo.zorrines.get(i);
			if(PVector.dist(posiciones, z.posiciones)<100){
				aceleracion= PVector.sub(posiciones,z.posiciones);
				
				aceleracion.setMag(0.05F);
				velocidad.add(aceleracion);
				
				
					posiciones.add(velocidad);
			
				huyendo=true;
			}else {huyendo=false;}
		}
		
		
		
		if(!huyendo){
			velocidad.add(aceleracion);
			posiciones.add(velocidad);
		}
		

	}
	private void limites(){
		if(posiciones.x>mundo.app.width+5){
			posiciones.x = 0;
		}
		if(posiciones.x<-5){
			posiciones.x =  mundo.app.width;
		}
		if(posiciones.y>mundo.app.height+5){
			posiciones.y = 0;
		}
		if(posiciones.y<-5){
			posiciones.y = mundo.app.height;
		}
	}

}
