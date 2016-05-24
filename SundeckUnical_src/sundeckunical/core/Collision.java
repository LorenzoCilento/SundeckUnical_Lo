package sundeckunical.core;


public final class Collision {

/* funzione che controlla se c'� o meno una collisione con un AbstractStaticObj
 *  e mi ritorna il tipo di collisione trovata */	
	public static TypeOfCollision checkTypeCollision(final AbstractMovableObj mov, final AbstractMovableObj obj){
	/* Se c'� una collisione LEFT_RIGHT*/	
		if( collideLeftRight(mov, obj) )
			return TypeOfCollision.LEFT_RIGHT;
	/* Se c'� una collisione RIGHT_LEFT*/
		else if( collideRightLeft(mov, obj) )
			return TypeOfCollision.RIGHT_LEFT;
	/* Se c'� una collisione UP_DOWN*/
		else if( collideUpDown(mov, obj) )
			return TypeOfCollision.UP_DOWN;
	/* Se c'� una collisione DOWN_UP*/
		else if( collideDownUp(mov, obj))
			return TypeOfCollision.DOWN_UP;
		
	/* ALTRIMENTI se non c'� nessuna collisione: ANYTHING */
		return TypeOfCollision.ANYTHING;
	}

	
	/* collisione faccia sinistra del movable con la destra di obj */	
	public static boolean collideLeftRight(final AbstractMovableObj mov, final AbstractMovableObj obj){
		//punti del rettangolo che delimita mov meno la sua deviation perch� il mov si sposta a sx
		int scostamentoMov=mov.getMovement();
		int sxXMov=mov.x - scostamentoMov;
		int dxXMov=(mov.x - scostamentoMov)+mov.getWIDTH();
		int upYMov=mov.y;
		int downYMov=mov.y+mov.getHEIGHT();
		//punti del rettangolo che delimita obj
		int sxXObj=obj.x;
		int dxXObj=obj.x+obj.getWIDTH();
		int upYObj=obj.y;
		int downYObj=obj.y+obj.getHEIGHT();
		
		//se la direzione del player � LEFT e c'� un ostacolo collide
		if( (mov.getDirection()==Direction.LEFT)){
			if( 
				// se il punto (x,y) o (x+width,y) del mov � compreso nei punti (x,y),(x+width,y) del obj,o viceversa
			  ( ((sxXMov >= sxXObj && sxXMov <= dxXObj) || (dxXMov >= sxXObj && dxXMov <= dxXObj)) 
				|| 
				((sxXObj >= sxXMov && sxXObj <= dxXMov) || (dxXObj >= sxXMov && dxXObj <= dxXMov) ) 
			  )
			  &&
			   // e se il punto (x,y) o (x,y+height) del mov � compreso nei punti (x,y),(x,y+width) del obj,o viceversa
			  ( ((upYMov >=upYObj && (upYMov <= downYObj)) || (downYMov>=upYObj && downYMov<= downYObj) ) 
			    || 
			    ( ((upYObj >= upYMov && upYObj <= downYMov) || (downYObj >= upYMov && downYObj <= upYMov)) )
			  )
			)
				return true;
		}
		return false;
	}

/* collisione faccia destra del movable con quella sinistra di obj */
	public static boolean collideRightLeft(final AbstractMovableObj mov, final AbstractMovableObj obj){
		//punti del rettangolo che delimita mov pi� la sua deviation perch� il mov si sposta a dx
		int scostamentoMov=mov.getMovement();
		int sxXMov=mov.x + scostamentoMov;
		int dxXMov=(mov.x + scostamentoMov)+mov.getWIDTH();
		int upYMov=mov.y;
		int downYMov=mov.y+mov.getHEIGHT();
		//punti del rettangolo che delimita obj
		int sxXObj=obj.x;
		int dxXObj=obj.x+obj.getWIDTH();
		int upYObj=obj.y;
		int downYObj=obj.y+obj.getHEIGHT();
		
		//se la direzione del player � LEFT e c'� un ostacolo collide
		if( (mov.getDirection()==Direction.RIGHT) ){
			if( 
				// se il punto (x,y) o (x+width,y) del mov � compreso nei punti (x,y),(x+width,y) del obj,o viceversa
			  ( ((sxXMov >= sxXObj && sxXMov <= dxXObj) || (dxXMov >= sxXObj && dxXMov <= dxXObj)) 
				|| 
				((sxXObj >= sxXMov && sxXObj <= dxXMov) || (dxXObj >= sxXMov && dxXObj <= dxXMov) ) 
			  )
			  &&
			   // e se il punto (x,y) o (x,y+height) del mov � compreso nei punti (x,y),(x,y+width) del obj,o viceversa
			  ( ((upYMov >=upYObj && (upYMov <= downYObj)) || (downYMov>=upYObj && downYMov<= downYObj) ) 
			    || 
			    ( ((upYObj >= upYMov && upYObj <= downYMov) || (downYObj >= upYMov && downYObj <= upYMov)) )
			  )
			)
				return true;
		}
		return false;
	}
	
/* collisione faccia superiore del movable con quella inferiore di obj */	
	public static boolean collideUpDown(final AbstractMovableObj mov, final AbstractMovableObj obj){
	//punti del rettangolo che delimita mov meno la sua velocit� in quanto si sposta verso sopra
		int sxXMov=mov.x;
		int dxXMov=mov.x+mov.getWIDTH();
		int upYMov=(mov.y - mov.getSpeed()) + (mov.getHEIGHT()/2); // collisione col centro dell'oggetto
		int downYMov=(mov.y-mov.getSpeed())+mov.getHEIGHT();
	//punti del rettangolo che delimita obj pi� la sua velocit� in quanto si sposta verso sotto
		int sxXObj=obj.x;
		int dxXObj=obj.x+obj.getWIDTH();
		int upYObj=obj.y+obj.getHEIGHT()/2;
		int downYObj=obj.y+obj.getHEIGHT();
		if(obj.getDirection()== Direction.DOWN){
			upYObj+=obj.getSpeed();
			downYObj+=obj.getSpeed();
		}
		
		if( mov.getDirection() == Direction.UP && (obj.getDirection() == Direction.DOWN || obj.getDirection()== Direction.STOP ) ){
			if( 
					// se il punto (x,y) o (x+width,y) del mov � compreso nei punti (x,y),(x+width,y) del obj,o viceversa
				  ( ((sxXMov >= sxXObj && sxXMov <= dxXObj) || (dxXMov >= sxXObj && dxXMov <= dxXObj)) 
					|| 
					((sxXObj >= sxXMov && sxXObj <= dxXMov) || (dxXObj >= sxXMov && dxXObj <= dxXMov) ) 
				  )
				  &&
				   // e se il punto (x,y) o (x,y+height) del mov � compreso nei punti (x,y),(x,y+width) del obj,o viceversa
				  ( ((upYMov >=upYObj && (upYMov <= downYObj)) || (downYMov>=upYObj && downYMov<= downYObj) ) 
				    || 
				    ( ((upYObj >= upYMov && upYObj <= downYMov) || (downYObj >= upYMov && downYObj <= upYMov)) )
				  )
				)
				return true;
		}
			
		return false;
	}
	
/* collisione faccia inferiore del movable con quella superiore di obj */	
	public static boolean collideDownUp(final AbstractMovableObj mov, final AbstractMovableObj obj){
		//punti del rettangolo che delimita mov meno la sua velocit� in quanto si sposta verso sopra
		int sxXMov=mov.x;
		int dxXMov=mov.x+mov.getWIDTH();
		int upYMov=mov.y - mov.getSpeed() + (mov.getHEIGHT()/2);
		int downYMov=(mov.y-mov.getSpeed())+mov.getHEIGHT();
	//punti del rettangolo che delimita obj meno la sua velocit� in quanto si sposta verso sopra
		int sxXObj=obj.x;
		int dxXObj=obj.x+obj.getWIDTH();
		int upYObj=obj.y-obj.getSpeed()+obj.getHEIGHT()/2;
		int downYObj=obj.y-obj.getSpeed()+obj.getHEIGHT();
		
		
		if( mov.getDirection() == Direction.UP && (obj.getDirection() == Direction.UP || obj.getDirection()== Direction.STOP ) ){
			if( 
					// se il punto (x,y) o (x+width,y) del mov � compreso nei punti (x,y),(x+width,y) del obj,o viceversa
				  ( ((sxXMov >= sxXObj && sxXMov <= dxXObj) || (dxXMov >= sxXObj && dxXMov <= dxXObj)) 
					|| 
					((sxXObj >= sxXMov && sxXObj <= dxXMov) || (dxXObj >= sxXMov && dxXObj <= dxXMov) ) 
				  )
				  &&
				   // e se il punto (x,y) o (x,y+height) del mov � compreso nei punti (x,y),(x,y+width) del obj,o viceversa
				  ( ((upYMov >=upYObj && (upYMov <= downYObj)) || (downYMov>=upYObj && downYMov<= downYObj) ) 
				    || 
				    ( ((upYObj >= upYMov && upYObj <= downYMov) || (downYObj >= upYMov && downYObj <= upYMov)) )
				  )
				)
				return true;
		}
		
		return false;
	}
	
	
}


