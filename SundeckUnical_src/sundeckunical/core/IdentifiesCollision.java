package sundeckunical.core;


/* Classe che identifica se è avvenuta una collisione mortale o superficiale,
 * in modo da rilevare l'eventuale sconfitta del player
 */

public final class IdentifiesCollision {
	
	private static int PRECISION_RANGE=10;  // range che riguarda le suddivisioni di ogni faccia per aumentare o meno la precisione
	
	private static boolean fatalRangeCollision(final AbstractMovableObj mov,final AbstractMovableObj obj ){
		double dimension=mov.getWIDTH()/PRECISION_RANGE; // dimensione dell'estensione del range
		double offsetSx=mov.x + (mov.getWIDTH() /2)  - (dimension/2);  //margine sinistro del range fatale
		double offsetDx=mov.x+ (mov.getWIDTH()/2) + (dimension/2);  //margine destro del range fatale
		
		if( (offsetSx >= obj.x && offsetSx <= (obj.x + obj.getWIDTH()) ) 
				|| ((offsetDx >= obj.x) && (offsetDx <= (obj.x + obj.getWIDTH()))) 
		)
			return true;
		
		
		return false;
	}
	
/* se si ha una collisione superficiale */
	public static boolean shallowCollision(final AbstractMovableObj mov,final AbstractMovableObj obj ){
	/* se si verifica una collisione con la faccia posteriore di un qualsiasi ostacolo */
		if( !fatalRangeCollision(mov, obj) ){
			return true;
			
		}
		return false;		
	}
/* se si ha una collisione mortale */
	public static boolean fatalCollision(final AbstractMovableObj mov,final AbstractMovableObj obj ){
		if( fatalRangeCollision(mov, obj) )
			return true;
		
		return false;
	}
	
	public static void setPrecisionRange ( int precisionRange ){
		PRECISION_RANGE=precisionRange;
	}
	
	

}
