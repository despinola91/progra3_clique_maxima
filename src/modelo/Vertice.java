package modelo;

public class Vertice {
	private String nombre;
	private Integer peso;
	
	public Vertice(String nombre, Integer peso) {
		this.nombre = nombre;
		this.peso = peso;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
}
