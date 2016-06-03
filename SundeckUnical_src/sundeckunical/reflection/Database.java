package sundeckunical.reflection;

/*
* Classe dedicata alla gestione del Database.
* Gestisce l'apertura e la chiusura della connessione col Database
* Fornisce i metodi per l'esecuzione delle query sul Database
*/
import java.sql.*;
import java.util.ArrayList;

public class Database {
	private String nomeDB; // Nome del Database a cui connettersi
	private String nomeUtente; // Nome utente utilizzato per la connessione al
								// Database
	private String pwdUtente; // Password usata per la connessione al Database
	private String errore; // Raccoglie informazioni riguardo l'ultima eccezione
							// sollevata
	private Connection db; // La connessione col Database
	private boolean connesso; // Flag che indica se la connessione è attiva o
								// meno
	protected static int numero = 0; // Raccoglie il numero di
										// eseguiAggiornamento()
										// andati a buon fine durante
										// l'esecuzione

	public Database(String nomeDB) {
		this(nomeDB, "", "");
	}

	public Database(String nomeDB, String nomeUtente, String pwdUtente) {
		this.nomeDB = nomeDB;
		this.nomeUtente = nomeUtente;
		this.pwdUtente = pwdUtente;
		this.connesso = false;
		this.errore = "";
	}

	public static void main(String[] args) {
		char separator = '-';

		Database db = new Database("databaseigpe", "root", "Rocco89");
		db.connetti();

		ArrayList<String> myString = new ArrayList<String>();
		// operazione di INSERT
		db.eseguiAggiornamento("INSERT INTO player (Nome,x,y) " + "VALUES ('Mari','1','1');");
		// operazione di DELETE
		db.eseguiAggiornamento("DELETE FROM player WHERE Nome='Mari';");
		// operazione di SELECT
		myString = db.eseguiQuery("SELECT * FROM player", separator);
		for (int i = 0; i < myString.size(); i++)
			System.out.println(myString.get(i));
		System.out.println("NUMERO:" + numero);
		db.disconnetti();
	}

	// Apre la connessione con il Database
	public boolean connetti() {
		connesso = false;
		try {
			// Carico il driver JDBC per la connessione con il database MySQL
			Class.forName("com.mysql.jdbc.Driver");
			// Controllo che il nome del Database non sia nulla
			if (!nomeDB.equals("")) {
				// Controllo se il nome utente va usato o meno per la
				// connessione
				if (nomeUtente.equals("")) {
					// La connessione non richiede nome utente e password
					db = DriverManager.getConnection("jdbc:mysql://localhost/" + nomeDB);
				} else {
					// La connessione richiede nome utente, controllo se
					// necessita anche della password
					if (pwdUtente.equals("")) {
						// La connessione non necessita di password
						db = DriverManager.getConnection("jdbc:mysql://localhost/" + nomeDB + "?user=" + nomeUtente);
					} else {
						// La connessione necessita della password
						db = DriverManager.getConnection(
								"jdbc:mysql://localhost/" + nomeDB + "?user=" + nomeUtente + "&password=" + pwdUtente);

					}
				}

				// La connessione è avvenuta con successo
				connesso = true;
				System.out.println("--> Connesso con: { " + nomeDB + " }");
			} else {
				System.out.println("Manca il nome del database!!");
				System.out.println("Scrivere il nome del database da utilizzare all'interno del file \"config.xml\"");
				System.exit(0);
			}
		} catch (Exception e) {
			System.out.println("ERRORE nella connessione al database!!!");
			errore = e.getMessage();
			System.out.println(getErrore());
			e.printStackTrace();
		}
		return connesso;
	}

	// Esegue una query di selezione dati sul Database
	// query: una stringa che rappresenta un'istruzione SQL di tipo SELECT da
	// eseguire
	// colonne: il numero di colonne di cui sarà composta la tupla del risultato
	// ritorna un Vector contenente tutte le tuple del risultato
	public ArrayList<String> eseguiQuery(String query, char separator) {
		ArrayList<String> v = null;
		String[] record;
		int colonne = 0;
		try {
			Statement stmt = db.createStatement(); // Creo lo Statement per
													// l'esecuzione della query
			ResultSet rs = stmt.executeQuery(query); // Ottengo il ResultSet
														// dell'esecuzione della
														// query
			v = new ArrayList<String>();
			ResultSetMetaData rsmd = rs.getMetaData();
			colonne = rsmd.getColumnCount();
			String s = "";
			while (rs.next()) { // Creo il vettore risultato scorrendo tutto il
								// ResultSet
				record = new String[colonne];
				for (int i = 0; i < colonne; i++) {
					if (s == "")
						s += (rs.getString(i + 1));
					else
						s += separator + (rs.getString(i + 1));
				}
				// System.out.println(s);
				v.add(s);
				s = "";

			}
			rs.close(); // Chiudo il ResultSet
			stmt.close(); // Chiudo lo Statement
		} catch (Exception e) {
			e.printStackTrace();
			errore = e.getMessage();
			System.out.println(getErrore());
		}

		return v;
	}

	// Esegue una query di aggiornamento sul Database
	// query: una stringa che rappresenta un'istuzione SQL di tipo UPDATE da
	// eseguire
	// ritorna TRUE se l'esecuzione è adata a buon fine, FALSE se c'è stata
	// un'eccezione
	public boolean eseguiAggiornamento(String query) {
		int num = 0;
		boolean risultato = false;
		try {
			Statement stmt = db.createStatement();
			num = stmt.executeUpdate(query);
			numero += num;
			risultato = true;
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			errore = e.getMessage();
			System.out.println(getErrore());
			risultato = false;
		}
		return risultato;
	}

	// Chiude la connessione con il Database
	public void disconnetti() {
		try {
			db.close();
			connesso = false;
			System.out.println("--> Database chiuso correttamente!");
		} catch (Exception e) {
			System.out.println("Sono ancora connesso...ATTENZIONE!!!");
			errore = e.getMessage();
			System.out.println(getErrore());
			e.printStackTrace();
		}
	}

	// Ritorna TRUE se la connessione con il Database è attiva
	public boolean isConnesso() {
		return connesso;
	}

	// Ritorna il messaggio d'errore dell'ultima eccezione sollevata
	public String getErrore() {
		return errore;
	}
}
