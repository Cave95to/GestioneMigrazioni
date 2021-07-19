package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {
	
	// modello: qual è stato sistema ad ogni passo
	private Graph<Country, DefaultEdge> grafo;
	
	// tipi eventi -> coda prioritaria
	private PriorityQueue<Evento> queue;
	
	// parametri simulazione
	private int N_Migranti = 1000;
	private Country partenza;
	
	//valori in ouput
	private int T = -1;  // numero di passi
	// private List<CountryAndNumber> stanziali; ----> NO, meglio avere mappa perche il numero
	// di stanziali in ogni paese cambia durante la simulazione!!!
	private Map<Country, Integer> stanziali;
	
	public void init(Country country, Graph<Country, DefaultEdge> grafo) {
		
		this.partenza=country;
		this.grafo= grafo;
		
		// imposto stato iniziale
		this.T = 1;
		this.stanziali = new HashMap<>();
		for(Country c: this.grafo.vertexSet()) {
			this.stanziali.put(c, 0);
		}
		
		// creo la coda
		this.queue = new PriorityQueue<Evento> ();
		// inserisco primo evento
		this.queue.add(new Evento(this.T, this.partenza, this.N_Migranti));
		
	}
	
	public void run() {
		
		// finche la coda non si svuota ->
		// prendo un evento per volta e lo eseguo
		
		Evento e;
		while((e=this.queue.poll()) != null) {
			
			// simulo evento e 
			
			this.T = e.getT();
			
			int nPersone = e.getN();
			Country stato = e.getCountry();
			
			// ottengo i vicini di stato
			List<Country> vicini = Graphs.neighborListOf(this.grafo, stato);
			
			int migrantiPerStato = (nPersone/2)/vicini.size(); // in automatico fa troncamento
			
			// MA dal testo c'è un caso particolare da considerare.. se il numero di migranti è
			// piu piccolo del numero di paesi vicini
			// nPersone = 10 -> personeCheSiSpostano = 5 (in teoria)
			// vicini.size() = 7 .... -> la divisione che abbiamo fatto tagliata a INT
			// mi restituisce automaticamente 0 -> nessuno si sposta
			
			if(migrantiPerStato > 0) {
				
				// le persone si possono muovere
				
				for (Country confinante : vicini) {
					queue.add(new Evento(e.getT()+1, confinante, migrantiPerStato));
				}
			}
			
			int nStanziali = nPersone - migrantiPerStato * vicini.size();
			
			this.stanziali.put( stato, this.stanziali.get(stato)+nStanziali );
		}
	}
	
	public Map<Country, Integer> getStanziali() {
		return this.stanziali;
	}
	
	public Integer getT() {
		return this.T;
	}
}
