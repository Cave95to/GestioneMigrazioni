package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private Graph<Country, DefaultEdge> graph ;
	private Map<Integer,Country> countriesMap ;
	private Simulatore sim;
	
	public Model() {
		this.countriesMap = new HashMap<>() ;
		this.sim = new Simulatore();

	}
	
	public void creaGrafo(int anno) {
		
		this.graph = new SimpleGraph<>(DefaultEdge.class) ;

		BordersDAO dao = new BordersDAO() ;
		
		//vertici
		dao.getCountriesFromYear(anno,this.countriesMap) ;
		Graphs.addAllVertices(graph, this.countriesMap.values()) ;
		
		// archi
		List<Adiacenza> archi = dao.getCoppieAdiacenti(anno) ;
		for( Adiacenza c: archi) {
			graph.addEdge(this.countriesMap.get(c.getState1no()), 
					this.countriesMap.get(c.getState2no())) ;
			
		}
	}
	
	public List<CountryAndNumber> getCountriesAndNumbers() {
		
		List<CountryAndNumber> result = new ArrayList<>();
		
		for(Country c : this.graph.vertexSet()) {
			
			// oppure                          this.graph.degreeOf(c)
			result.add(new CountryAndNumber(c, Graphs.neighborListOf(this.graph, c).size()));
		}
		
		Collections.sort(result);
		return result;
	}
	
	public void simula(Country partenza) {
		
		if(this.graph!=null) {
	
		sim.init(partenza, graph);
		sim.run();
		
		}
	}
	
	public Integer getT() {
		return sim.getT();
	}
	
	// serve passare a lista per ORDINAMENTO
	public List<CountryAndNumber> getStanziali() {
		Map<Country, Integer> stanziali = sim.getStanziali();
		List<CountryAndNumber> result = new ArrayList<>();
		
		for(Country c : stanziali.keySet()) {
			if(stanziali.get(c)>0) { // solo se #persone è maggiore di zero
				CountryAndNumber cn = new CountryAndNumber(c, stanziali.get(c));
				result.add(cn);
			}
		}
		
		Collections.sort(result);
		return result;
		
	}

	public Set<Country> getCountries() {
		if(this.graph!=null) {
			return this.graph.vertexSet();
		}
		return null;
	}
}
