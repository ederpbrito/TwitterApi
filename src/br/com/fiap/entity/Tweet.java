package br.com.fiap.entity;

import java.time.LocalDate;

/**
 * @author Eder Brito, Felipe, Alexandre
 *
 */
public class Tweet{

	private String hashtag;
	private String autor;	
	private int qtdretweets = 0;
	private int qtdfavorites = 0;
	private LocalDate data;

	public Tweet(String hashtag){
		this.hashtag = hashtag;
	}

	public String getHashTag() {
		return hashtag;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getQtdretweets() {
		return qtdretweets;
	}

	public void setQtdretweets(int qtdretweets) {
		this.qtdretweets = qtdretweets;
	}

	public int getQtdfavorites() {
		return qtdfavorites;
	}

	public void setQtdfavorites(int qtdfavorites) {
		this.qtdfavorites = qtdfavorites;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		
		String print = "Autor da publicação: " + autor + "\n Data de publicação: " + data +  "\n Quantidade Retweets: " + qtdretweets + "\n Quantidade de Favorites: " + qtdfavorites;		
		
		return print;
	}
}
