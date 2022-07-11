package it.polito.tdp.crimes.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.crimes.db.EventsDao;
import it.polito.tdp.crimes.model.Evento.TipoEvento;


public class Simulator {

		
		//parametri simulazione---INPUT UTENTE
		private Integer anno; 
		private Integer mese; 
		private Integer giorno; 
		private Integer nAgenti; 
		private String distr; 
		
		//Stato del mondo simulato
		private Graph<Integer, DefaultWeightedEdge> grafo;
		Map<Integer, Integer> agenti; //mappa distretto-agenti liberi

		//coda degli eventi
		private PriorityQueue<Evento> queue;

		//OUTPUT simulazione
		public Integer malGestiti; //T
		

		/*public Simulator() {
			super();
		}*/

		public void init(Integer n, Integer anno, Integer mese, Integer giorno, 
				Graph<Integer, DefaultWeightedEdge> grafo2) {
			
			this.nAgenti=n;
			this.anno=anno; 
			this.mese= mese; 
			this.giorno=giorno; 
			this.grafo = grafo2;
			
			this.malGestiti=0; 
			this.agenti= new HashMap<Integer, Integer>();
	
			for (Integer i: this.grafo.vertexSet()) {
				this.agenti.put(i, 0);
			}
			
			//devo scegliere dov'è la centrale
			EventsDao dao= new EventsDao(); 
			Integer minD=dao.getDistrettoMin(anno);
			this.agenti.put(minD, n); //assegno gli agenti al distretto più vicino

			//creo e inizializzo la coda
			this.queue= new PriorityQueue<Evento>(); 
			for (Event e: dao.listAllEventsbyDate(anno, mese, giorno)) {
				queue.add(new Evento(TipoEvento.CRIMINE, e.getReported_date(), e));
			}
		}
			
			public int run() {
				Evento e;
				while((e = queue.poll())!=null){
					switch (e.getTipo()) {
					case CRIMINE:
						System.out.println("NUOVO CRIMINE! " + e.getCrimine().getIncident_id());	
						Integer partenza= null;
						partenza=cercaAgente(e.getCrimine().getDistrict_id());
						if (partenza!=null) {
							
							//esiste 1 agente libero in partenza, lo setto occupato
							this.agenti.put(partenza,this.agenti.get(partenza));
							
							//cerco di capire quanto ci mette l'agente libero ad arrivare
							Double distanza; 
							if(partenza.equals(e.getCrimine().getDistrict_id()))
								distanza=0.0;
							else
								distanza=this.grafo.getEdgeWeight(this.grafo.getEdge(partenza, e.getCrimine().getDistrict_id()));
							
							Long seconds = (long) ((distanza * 1000)/(60/3.6));
							this.queue.add(new Evento(TipoEvento.ARRIVA_AGENTE, e.getData().plusSeconds(seconds), e.getCrimine()));
						}
					 else {
						//non c'è nessun agente libero al momento -> crimine mal gestito
						System.out.println("CRIMINE " + e.getCrimine().getIncident_id() + " MAL GESTITO!");
						this.malGestiti ++;
					}
					break;
						
					
				
					case ARRIVA_AGENTE:
						System.out.println("ARRIVA AGENTE PER CRIMINE! " + e.getCrimine().getIncident_id());
						Long duration = getDurata(e.getCrimine().getOffense_category_id());
						this.queue.add(new Evento(TipoEvento.GESTITO,e.getData().plusSeconds(duration), e.getCrimine()));
						
						//controllare se il crimine è mal gestito
						if(e.getData().isAfter(e.getCrimine().getReported_date().plusMinutes(15))) {
							System.out.println("CRIMINE " + e.getCrimine().getIncident_id() + " MAL GESTITO!");
							this.malGestiti ++;
						}
						break;
						
						
					case GESTITO:
						System.out.println("CRIMINE " + e.getCrimine().getIncident_id() + " GESTITO");
						this.agenti.put(e.getCrimine().getDistrict_id(), this.agenti.get(e.getCrimine().getDistrict_id())+1);
						break;
					}
				}
				
			return this.malGestiti;
			
		}
			
			private Long getDurata(String offense_category_id) {
				if(offense_category_id.equals("all_other_crimes")) {
					Random r = new Random();
					if(r.nextDouble() > 0.5)
						return Long.valueOf(2*60*60);
					else
						return Long.valueOf(1*60*60);
				} else {
					return Long.valueOf(2*60*60);
				}
			}

			private Integer cercaAgente(Integer district_id) {
				Double distanza = Double.MAX_VALUE;
				Integer distretto = null;
				
				for(Integer d : this.agenti.keySet()) {
					if(this.agenti.get(d) > 0) {
						if(district_id.equals(d)) {
							distanza = 0.0;
							distretto = d; 
						} else if(this.grafo.getEdgeWeight(this.grafo.getEdge(district_id, d)) < distanza) {
							distanza = this.grafo.getEdgeWeight(this.grafo.getEdge(district_id, d));
							distretto = d;
						}
					}
				}
				return distretto;
			}
			
			
		
}
