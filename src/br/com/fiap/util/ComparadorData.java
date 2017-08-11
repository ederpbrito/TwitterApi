package br.com.fiap.util;

import java.util.Comparator;

import br.com.fiap.entity.Tweet;

/**
 * Classe que � responsavel por realizar a ordena��o dos tweets por Data.
 * @author Eder Brito, Felipe, Alexandre
 *
 */
public class ComparadorData implements Comparator<Tweet> {

	@Override
	public int compare(Tweet twt, Tweet outrotwt) {		
		return twt.getData().compareTo(outrotwt.getData());
	}

}
