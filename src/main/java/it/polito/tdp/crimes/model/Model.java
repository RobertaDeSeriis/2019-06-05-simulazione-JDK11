package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	
	EventsDao dao;
	List <Integer> anno; 
	List <Integer> mese; 
	List<Integer> giorno;
	List<Integer> id;
	Graph <Adiacenza, DefaultWeightedEdge> grafo; 

	public Model() {
		this.dao = new EventsDao();
	}

	public List<Integer> getAnno() {
		return dao.listAnno();
	}

	
	public List<Integer> getMese() {
		return dao.listMese();
	}


	public List<Integer> getGiorno() {
		return dao.listGiorno();
	}
	
	public String creaGrafo(int anno) {
		grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	
		//aggiungo vertici
		Graphs.addAllVertices(this.grafo, dao.getVertici(anno));
	
		//aggiungo archi
		for(Adiacenza a: dao.getVertici(anno)) {
			for(Adiacenza a1: dao.getVertici(anno)) {
				if(!a.equals(a1)) {
					double peso= LatLngTool.distance(a.getCentro(), a1.getCentro(), LengthUnit.KILOMETER); 
					Graphs.addEdge(this.grafo, a, a1, peso); 
				}
			}
		}
		
		return "Grafo creato con " +this.grafo.vertexSet().size()+ " vertici e "
		+ this.grafo.edgeSet().size()+ " archi";
	}


	public List<vicino> getVicini(Adiacenza a){
		List<vicino> vicini= new ArrayList<vicino>(); 
		List<Adiacenza> adiacenti; 
		adiacenti= Graphs.neighborListOf(this.grafo, a); 
		for (Adiacenza a1: adiacenti) {
			vicini.add(new vicino(a1.getId(), this.grafo.getEdgeWeight(this.grafo.getEdge(a, a1)))); 
		}
		Collections.sort(vicini);
		return vicini; 		
	}
	
	public List<Adiacenza> getVertici(Integer a){
		return dao.getVertici(a); 
	}
	
	
}
