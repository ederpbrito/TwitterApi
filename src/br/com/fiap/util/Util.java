package br.com.fiap.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Classe utilitária de apoio a outras classes
 * @author Eder Brito, Felipe, Alexandre 
 * @param <T>
 */
public class Util<T> {
	
	/**
	 * Método que ordena uma lista de acordo com o seu comparador.
	 * @autor Eder Brito, Felipe, Alexandre 	 
	 * @param List lista
	 * @param Comparator comparador
	 * @return List
	 */	
	public List<T> ordenarLista(List<T> lista, Comparator<T> comparador){	

		Collections.sort(lista,comparador);

		return lista;
	}
}