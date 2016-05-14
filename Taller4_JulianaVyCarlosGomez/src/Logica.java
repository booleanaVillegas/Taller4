import processing.core.PApplet;
import processing.core.PImage;


public class Logica {
Mundo mundo;
PApplet app;

private int pantallas;

	public Logica(PApplet app) {
		this.app=app;
		mundo=new Mundo(app);

	
	}
	
	
	public void pintar(){
		switch (pantallas) {
		case 0:
			app.image(mundo.imgs[5],600,350);
			if( ((app.mouseX>393) && (app.mouseX<510)) && ((app.mouseY>510) && (app.mouseY<660)) ){
				app.image(mundo.imgs[7],600,350);
				
			}else {app.image(mundo.imgs[7],600,350);}
		
			
			app.image(mundo.imgs[6],600,350);
	
			break;
			
		case 1:
			app.image(mundo.imgs[8], 600, 350);
			app.image(mundo.imgs[9], 600, 350);
			
			break;

		case 2:
			mundo.pintar();
			mundo.cosas();
			
			break;
		}
		
	}
	
	public void press (int x, int y) {
		if(pantallas==0){
		if( ((x>393) && (x<510)) && ((y>510) && (y<660)) ){
			pantallas=2;
		}
		if( ((x>588) && (x<852)) && ((y>510) && (y<660)) ){
			pantallas=1;
		}
		}
		if(pantallas==1){
			if( ((x>999) && (x<1113)) && ((y>523) && (y<664)) ){
				pantallas=2;
			}
		}
		app.println(x,y);
		mundo.press(x,y);
	}


	public void releassed() {
		mundo.relassed();
		
	}


	public void dragged(int x, int y) {
		mundo.dragged(x, y);
		
	}

}
