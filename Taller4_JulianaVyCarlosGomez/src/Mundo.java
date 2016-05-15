import java.util.Iterator;
import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Mundo{
	public LinkedList<Zorro> zorrines;
	public LinkedList<Conejo> conejines;
	public LinkedList<zanahoria> zanahorias;

	private final int NUM_PRESAS = 4; 
	private final int NUM_CAZADORES = 4;
	public int EnfriamientoDormirConejo=0;
	public boolean DormirConejo;
	public boolean moverZorro;
	public int EnfriamientoMoverZorro=0;
	public int millis;
	public int millisZorro;
	private int millisZanahoria;
	public final int TIEMPO_DESCANSO = 33; // tiempo que duermen los hilos
	PApplet app;
	PImage[] imgs =new PImage[16];
	PFont fuente;
	public int conejosMuertos;
	
	public Mundo(PApplet app) {
	fuente= app.createFont("../data/Roboto-Light.ttf", 20);	
	zorrines= new LinkedList<Zorro>();
	conejines= new LinkedList<Conejo>();
	zanahorias= new LinkedList<zanahoria>();
	this.app=app;
	
	for (int i = 0; i < imgs.length; i++) {
		imgs[i]= app.loadImage("../data/img-"+(i+1)+".png");
	}
	
	
		

	}
	
	public void pintar(){
app.image(imgs[0],600,350);
app.noCursor();
app.image(imgs[11],app.mouseX,app.mouseY);

for (int i = 0; i < conejines.size(); i++) {
	Conejo c = conejines.get(i);
	c.pintar();
}

for (Zorro zorro : zorrines) {
	zorro.pintar();
}
for (zanahoria zanaorin : zanahorias) {
	zanaorin.pintar();
}
habilidades();
conejosMuertos= Zorro.coMuertos();
app.image(imgs[14],300,30);
app.textFont(fuente,30);
app.fill(255);
app.text(conejosMuertos,330, 35);
}
	public void press(int x, int y){
		for (Conejo conejo : conejines) {
			conejo.press(x, y);
		}
		
		for (Zorro zorro : zorrines) {
			zorro.press(x, y);
		}
	}
	
	private void habilidades(){
		//enfriamiento habilidad dormir el conejo
			if(DormirConejo){				
				if(app.millis()-millis>1000){
					millis=app.millis();
					EnfriamientoDormirConejo-=1;
				}
				if(EnfriamientoDormirConejo==0){
					DormirConejo=false;
				}
			}
		//enfriamiento habilidad mover el zorro
			if(moverZorro){
				if(app.millis()-millisZorro>1000){
					millisZorro=app.millis();
					EnfriamientoMoverZorro-=1;
				}
				if(EnfriamientoMoverZorro==0){
					moverZorro=false;
				}
			}
			
			
		app.textFont(fuente,30);
		app.fill(100);
		app.text("PUEDES VOLVER A DESCONGELAR EL CONEJO EN : "+EnfriamientoDormirConejo+ " seg", 100, 100);
		app.text("PUEDES VOLVER A ARRASTRAR EL ZORRO POR : "+EnfriamientoMoverZorro+ " seg", 100, 150);
		app.fill(255);
		app.text("PUEDES VOLVER A DESCONGELAR EL CONEJO EN :"+EnfriamientoDormirConejo+ " seg", 102, 102);
		app.text("PUEDES VOLVER A ARRASTRAR EL ZORRO POR : "+EnfriamientoMoverZorro+ " seg", 102, 152);
	}
	
	
	
	public void agregarEspecimenes(){
		for (int i = 0; i < NUM_CAZADORES; i++) {
			zorrines.add(new Zorro(this, imgs[(int)app.random(1 , 3)]));
		}
		for (int i = 0; i < NUM_PRESAS; i++) {
			conejines.add(new Conejo(this, app,imgs[4], imgs[15] ));
		}
		for (int i = 0; i < 3; i++) {
			zanahorias.add(new zanahoria((int)app.random(app.width),(int) app.random(app.height), this, imgs[3]));
		}
	}
	private void comerZanahoria(){
		for (int i = 0; i < conejines.size(); i++) {
			Conejo conejo = conejines.get(i);
			for (int j = 0; j < zanahorias.size(); j++) {
				zanahoria z = zanahorias.get(j);
				if(PApplet.dist(conejo.posiciones.x, conejo.posiciones.y, z.posiciones.x, z.posiciones.y)<25){
					zanahorias.remove(z);
					conejines.add(new Conejo(this, app, imgs[4], imgs[15]));
				}
			}
			
		}		
		
	}
	private void agregarZanhoria(){
		if(app.millis()-millisZanahoria>7000 && zanahorias.size()<=7){
		
		zanahorias.add(new zanahoria((int)app.random(app.width), (int)app.random(app.height), this, imgs[3]));
		millisZanahoria=app.millis();
		}
		
	}
	
	public void cosas(){
		
comerZanahoria();
agregarZanhoria();
			
	}

	public void dragged(int x, int y) {
		for (Zorro zorro : zorrines) {
		zorro.dragged(x, y);
		}
		
	}

	public void relassed() {
		for (Zorro zorro : zorrines) {
			zorro.relassed();
		}
		
	}
	
	 
}
