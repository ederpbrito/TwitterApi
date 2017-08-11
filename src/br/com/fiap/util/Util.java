package br.com.fiap.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Classe utilit�ria de apoio a outras classes
 * @author Eder Brito, Felipe, Alexandre 
 * @param <T>
 */
public class Util<T> {
	
	/**
	 * M�todo que ordena uma lista de acordo com o seu comparador.
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