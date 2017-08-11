package br.com.fiap.dominio;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.fiap.entity.Tweet;
import br.com.fiap.services.ServiceTwitter;
import br.com.fiap.util.ComparadorData;
import br.com.fiap.util.ComparadorNome;
import br.com.fiap.util.Util;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * Classe respons�vel em gerar os relat�rios de an�lise do tweet
 * @autor Eder Brito, Felipe, Alexandre 
 */
public class RelatorioTweet {

	private String hashtag;
	
	private List<Tweet> lista;

	/**
	 * Construtor da classe Relatorio Tweet
	 * @param String hashtag
	 * @param LocalDate dataanalise
	 * @throws TwitterException
	 */
	public RelatorioTweet(String hashtag, LocalDate dataanalise) throws TwitterException {
		ServiceTwitter service = new ServiceTwitter(hashtag,dataanalise);
		this.hashtag = hashtag;
		lista = service.buscarTweets();
	}
	
	/**
	 * Construtor default da classe Relatorio Tweet
	 */
	public RelatorioTweet(){}
	
	/**
	 * M�todo que obt�m a hashtag do relat�rio
	 * @autor Eder Brito, Felipe, Alexandre 
	 * @return String
	 */
	public String getHashtag() {
		return hashtag;
	}
	
	/**
	 * Quantidade total de tweet da hashtag referente ao per�odo pesquisado
	 * @autor Eder Brito, Felipe, Alexandre 
	 * @return int
	 */
	public int getTotalTweet() {
		return (int) lista.stream().count();
	}	

	/**
	 * Quantidade total de retweet da hashtag referente ao per�odo pesquisado
	 * @autor Eder Brito, Felipe, Alexandre 
	 * @return int
	 */
	public int getTotalRetweet() {
		return lista.stream().collect(Collectors.groupingBy(Tweet::getData,Collectors.summingInt(Tweet::getQtdretweets)))
				.values().stream().mapToInt(Integer::intValue).sum();		
	}

	/**
	 * Quantidade total de favorites da hashtag referente ao per�odo pesquisado
	 * @autor Eder Brito, Felipe, Alexandre 
	 * @return int
	 */
	public int getTotalFavorites(){

		return lista.stream().collect(Collectors.groupingBy(Tweet::getData,Collectors.summingInt(Tweet::getQtdfavorites)))
				.values().stream().mapToInt(Integer::intValue).sum();		
	}
	
	/**
	 * Data mais recente da hashtag pequisada referente ao per�odo pesquisado
	 * @autor Eder Brito, Felipe, Alexandre 
	 * @return LocalDate
	 */
	public LocalDate getDataRecente(){
		
		Util<Tweet> util = new Util<Tweet>();
		ComparadorData comparador = new ComparadorData();		
		
		lista = util.ordenarLista(lista, comparador);
		
		return lista.get(lista.size() - 1).getData();
	}
	
	/**
	 * Data mais antiga da hashtag pequisada referente ao per�odo pesquisado
	 * @autor Eder Brito, Felipe, Alexandre 
	 * @return LocalDate
	 */
	public LocalDate getDataAntiga(){

		Util<Tweet> util = new Util<Tweet>();
		ComparadorData comparador = new ComparadorData();
		
		lista = util.ordenarLista(lista, comparador);

		return lista.get(0).getData();
	}

	/**
	 * Primeiro nome do relat�rio, referente ao per�odo pesquisado,
	 * da lista ordenada por ordem alfabetica
	 * @autor Eder Brito, Felipe, Alexandre 
	 * @return String
	 */	
	public String getPrimeiroNome(){
		Util<Tweet> util = new Util<Tweet>();
		ComparadorNome comparador = new ComparadorNome();
		
		lista = util.ordenarLista(lista, comparador);

		return lista.get(0).getAutor();
	}

	/**
	 * �ltimo nome do relat�rio, referente ao per�odo pesquisado,
	 * da lista ordenada por ordem alfabetica
	 * @autor Eder Brito, Felipe, Alexandre 
	 * @return String
	 */	
	public String getUltimoNome(){
		Util<Tweet> util = new Util<Tweet>();
		ComparadorNome comparador = new ComparadorNome();
		
		lista = util.ordenarLista(lista, comparador);

		return lista.get(lista.size() - 1).getAutor();
	}

	/**
	 * Quantidade de tweet por dia referente ao per�odo pesquisado
	 * @autor Eder Brito, Felipe, Alexandre
	 * @return Map
	 */
	public Map<LocalDate, Long> obterTweetPorDia() {
		return lista.stream().collect(Collectors.groupingBy(Tweet::getData, Collectors.counting()));
	}	

	/**
	 * Quantidade de retweet por dia referente ao per�odo pesquisado
	 * @autor Eder Brito, Felipe, Alexandre
	 * @return Map
	 */
	public Map<LocalDate, Integer> obterReTweetPorDia() {

		return lista.stream().collect(Collectors.groupingBy(Tweet::getData,Collectors
						.summingInt(Tweet::getQtdretweets)));
	}

	/**
	 * Quantidade de favorites por dia referente ao per�odo pesquisado
	 * @autor Eder Brito, Felipe, Alexandre
	 * @return Map
	 */
	public Map<LocalDate, Integer> obterFavoritesPorDia() {	

		return lista.stream().collect(Collectors.groupingBy(Tweet::getData,Collectors
						.summingInt(Tweet::getQtdfavorites)));
	}
	
	/**
	 * Quantidade de favorites por dia referente ao per�odo pesquisado
	 * @autor Eder Brito, Felipe, Alexandre
	 * @return Map
	 * @throws TwitterException 
	 */
	public String escreveTweet(String texto) throws TwitterException {	

		ServiceTwitter service = new ServiceTwitter();
		
		Status status = service.escreverTweet(texto);
		
		return status.getText();
	}
	
}
