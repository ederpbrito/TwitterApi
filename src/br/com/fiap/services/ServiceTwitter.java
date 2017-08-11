package br.com.fiap.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import br.com.fiap.entity.Tweet;
import br.com.fiap.util.ConnectFactory;
import br.com.fiap.util.Constants;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Classe responsável pela interação com o Twitter4j.
 * @author Eder Brito, Felipe, Alexandre
 *
 */
public class ServiceTwitter {

	/*** @property api de conexao com twitter */
	private Twitter apitwitter;
	
	/*** @property Palavra que será pesquisada no twitter */
	private String hashtag;
	
	/*** @property Data inicio */
	private LocalDate datainicio;
	
	/*** @property Data fim */
	private LocalDate datafim;
		
	/*** @property Lista de objetos tweet retornado pelo Twitter */
	private List<Status> tweets = new ArrayList<>();
	
	/*** @property Lista de objetos tweet gerado pela classe ServiceTwitter */
	private List<Tweet> lista = new ArrayList<>();

	/**
	 * Construtor de inicialização do serviço Twitter
	 * @autor Eder Brito, Felipe, Alexandre
	 * @param String hashtag
	 * @throws TwitterException
	 */	
	public ServiceTwitter(String hashtag,LocalDate data) throws TwitterException{
		this.hashtag = hashtag;
		apitwitter = ConnectFactory.getInstance();
		datainicio = data;			
		datafim = data.plusDays(Constants.NUMBER_DAYS);
	}

	/**
	 * Construtor default do serviço Twitter
	 * @autor Eder Brito, Felipe, Alexandre
	 * @param String hashtag
	 * @throws TwitterException
	 */	
	public ServiceTwitter() throws TwitterException{
		apitwitter = ConnectFactory.getInstance();
	}
	
	/**
	 * Método para escrever um tweet
	 * @autor Eder Brito, Felipe, Alexandre
	 * @param String texto
	 * @return Status
	 * @throws TwitterException
	 */
	public Status escreverTweet(String texto) throws TwitterException{

		return apitwitter.updateStatus(texto);
	}

	/**
	 * Método que busca tweet de acordo com o período informado
	 * @autor Eder Brito, Felipe, Alexandre 
	 * @throws TwitterException
	 */
	public List<Tweet> buscarTweets() throws TwitterException{

		QueryResult result;
		Query query = new Query(hashtag);
		query.setSince(datainicio.toString());

		do{
			result = apitwitter.search(query);

			result.getTweets().forEach(r -> tweets.add(r));

		}while((query = result.nextQuery()) != null);
		
		return carregarLista();
	}	

	/**
	 * Método que carrega um objeto Tweet. Informações: hashtag da publicação, autor do tweet
	 * data de publicação, quantidade de retweets e favorites. 
	 * @autor Eder Brito, Felipe, Alexandre 
	 * @param Status tweet
	 * @param String hashTag
	 * @return Tweet
	 */
	private Tweet carregarDados(Status tweet, String hashTag) {
		Tweet tw = new Tweet(hashTag);
		LocalDate date = tweet.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		tw.setAutor(tweet.getUser().getName());				
		tw.setData(date);		
		tw.setQtdretweets(tweet.getRetweetCount());
		tw.setQtdfavorites(tweet.getFavoriteCount());


		return tw;
	}

	/**
	 * Método que carrega uma lista de Tweet
	 * @autor Eder Brito, Felipe, Alexandre 
	 * @throws TwitterException
	 */
	private List<Tweet> carregarLista() throws TwitterException{
		
		lista.clear();
		Stream<Tweet> twts = tweets.stream().map(s -> carregarDados(s, hashtag));		
		twts.filter(l -> (l.getData().isEqual(datainicio) || l.getData().isAfter(datainicio)) 
				&& (l.getData().isEqual(datafim) || l.getData().isBefore(datafim))).forEach(lista::add);
		
		return lista;
	}
}
