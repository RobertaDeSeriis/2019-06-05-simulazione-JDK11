package it.polito.tdp.crimes.model;


public class vicino implements Comparable<vicino> {

	private int disId; 
	private double centro;
	
	public vicino(int disId, double centro) {

		this.disId = disId;
		this.centro = centro;
	}
	public int getDisId() {
		return disId;
	}
	public void setDisId(int disId) {
		this.disId = disId;
	}
	public double getCentro() {
		return centro;
	}
	public void setCentro(double centro) {
		this.centro = centro;
	}
	@Override
	public int compareTo(vicino o) {
		return (int) (this.centro-o.centro);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(centro);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + disId;
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
		vicino other = (vicino) obj;
		if (Double.doubleToLongBits(centro) != Double.doubleToLongBits(other.centro))
			return false;
		if (disId != other.disId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "\nVertice adiacente: " + disId + "   distante: " + centro;
	}
	
	
	
}
