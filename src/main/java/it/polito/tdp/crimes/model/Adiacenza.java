package it.polito.tdp.crimes.model;

import com.javadocmd.simplelatlng.LatLng;

public class Adiacenza {

	private int id; 
	private LatLng centro;
	
	public Adiacenza(int id, LatLng centro) {
		this.id = id;
		this.centro = centro;
	}

	public int getId() {
		return id;
	}

	public LatLng getCentro() {
		return centro;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCentro(LatLng centro) {
		this.centro = centro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((centro == null) ? 0 : centro.hashCode());
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adiacenza other = (Adiacenza) obj;
		if (centro == null) {
			if (other.centro != null)
				return false;
		} else if (!centro.equals(other.centro))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vertice: "+ id;
	}

	
	
	
	
}
