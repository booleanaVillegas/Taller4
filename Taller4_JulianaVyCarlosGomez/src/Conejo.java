

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
		velocidad.add(aceleracion);
		posiciones.add(velocidad);
		velocidad.limit(5);
		for (int i = 0; i < mundo.zanahorias.size(); i++) {
			zanahoria zanahorin = mundo.zanahorias.get(i);
			float angulo=PApplet.atan2(zanahorin.posiciones.y-posiciones.y, zanahorin.posiciones.x-posiciones.x);
			if (PApplet.dist(posiciones.x, posiciones.y, zanahorin.posiciones.x, zanahorin.posiciones.y) < 200) {
				posiciones.x = (int) (posiciones.x + (PApplet.cos(angulo) * (velocidad.x * 2)));
				posiciones.y = (int) (posiciones.y + (PApplet.sin(angulo) * (velocidad.y * 2)));
				
			}
		}
		
		for (int i = 0; i < mundo.zorrines.size(); i++) {
			// calcula la direccion al cazador y la invierte para huir
			Zorro c = mundo.zorrines.get(i);			
			float angulo = PApplet.atan2(c.posiciones.y-posiciones.y, c.posiciones.x-posiciones.x);
			angulo+= PApplet.PI;
			
			// si la distancia es menor a 150 huye!! sino solo se mueve por el lienzo
			if( PApplet.dist(posiciones.x, posiciones.y, c.posiciones.x, c.posiciones.y) < 150 ){
				posiciones.x = ((float) (posiciones.x + (PApplet.cos(angulo)*velocidad.x))+3);
				posiciones.y =  ((float) (posiciones.y + (PApplet.sin(angulo)*velocidad.y))+3);	
				// si el cazador le ha atrapado interrumpe el hilo y esto lleva a la muerte a la presa
				if(PApplet.dist(posiciones.x, posiciones.y, c.posiciones.x, c.posiciones.y)<25 && !c.seleccionado){
					interrupt();
				}				
			}else{
				
				posiciones.x = (float) (posiciones.x + (-1 + Math.random()*3));
				posiciones.y = (short) (posiciones.y + (-1 + Math.random()*3));	
				if(posiciones.x>mundo.app.width){
					posiciones.x = 0;
				}
				if(posiciones.x<0){
					posiciones.x =  mundo.app.width;
				}
				if(posiciones.y>mundo.app.height){
					posiciones.y = 0;
				}
				if(posiciones.y<0){
					posiciones.y = (mundo.app.height);
				}
			}
		}
	}

}
