package sundeckunical.core;

enum TypeOfCollision {
	
	ANYTHING,		//nessuna collisione
	LEFT_RIGHT, 	//E' la collisione della faccia sinistra del Player con la destra di un obj
	UP_DOWN,		//E' la collisione della faccia superiore del Player
					//con quella inferiore di un obj
	RIGHT_LEFT,		//E' la collisione della faccia destra del Player con la sinistra di un obj
	DOWN_UP;		//E' la collisione della faccia inferiore del Player con la superiore di un obj
}
