package br.com.fiap.aplicacao;


import java.time.LocalDate;
import java.util.Map;

import javax.swing.JOptionPane;

import br.com.fiap.dominio.RelatorioTweet;
import br.com.fiap.util.Constants;
import twitter4j.TwitterException;

public class Principal {

	private static String hashtag;
	private static LocalDate datapesquisa;
	private static RelatorioTweet relatorio;

	public static void main(String[] args){
		String input = mostrarPainel();
		int opcao = 0;
		if(input != null) {
			opcao = Integer.parseInt(input);
		}
		
		while(opcao != 0){
			switch(opcao){
			case 1: informacoesPesquisa(); break;
			case 2: relatorioCompleto(); break;
			case 3: tweetPorDia(); break;
			case 4: retweetPorDia(); break;
			case 5: favoritesPorDia(); break;	
			case 6: totaisPeriodo(); break;
			case 7: relatorioNome(); break;
			case 8: relatorioData(); break;
			case 9: mencionaMichel(); break;
			default: JOptionPane.showMessageDialog(null, "Opção Inválida!", "Erro de Opção", 1);			 
			}

			opcao = mostrarPainelContinuar();

			if(opcao != 0){
				input =	mostrarPainel();
				if(input != null) {
					opcao = Integer.parseInt(input);
				}else{
					opcao = 0;
				}
			}
		}
		
		System.out.println("Programa Finalizado!");	
	}

	private static int mostrarPainelContinuar(){
		Object[] options = { "Encerrar", "Continue" };
		return JOptionPane.showOptionDialog(null, "Clique em Continue para continuar", "Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
	}	

	private static String mostrarPainel() {

		String input = JOptionPane.showInputDialog("Escolha uma das opções do Relatório: "
				+ "\n1 - Inicialização do relatório."
				+ "\n2 - Relatório completo."
				+ "\n3 - Total de Tweet por dia."
				+ "\n4 - Total de ReTweet por dia."
				+ "\n5 - Total de Favorites por dia."
				+ "\n6 - Totais no período."
				+ "\n7 - Primeiro e Ultimo nome"				
				+ "\n8 - Data mais recente e antiga"	
				+ "\n9 - Mencionar Michel em um tweet"
				+ "\n0 - Para sair.");
		return input;
	}

	private static void inicializarRelatorio(){		
		
		try {
			relatorio = new RelatorioTweet(hashtag, datapesquisa);
		} catch (TwitterException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao digitar a data", 1);
		}
	}

	private static void informacoesPesquisa(){
		hashtag = JOptionPane.showInputDialog("Digite sua HashTag da pesquisa.");
		try{
			datapesquisa = LocalDate.parse(JOptionPane.showInputDialog("Digite a data de inicio da pesquisa com no máximo 7 dias de diferença da data atual. Formato: YYYY-MM-dd"
																		+ "\n Data de inicio máxima: " + LocalDate.now().minusDays(Constants.NUMBER_DAYS)));
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao digitar a data", 1);
		}
		
		if(datapesquisa.isBefore(LocalDate.now().minusDays(Constants.NUMBER_DAYS))) {
			System.out.println("Não foi possivel inicializar o relatório, a data da pesquisa deve ter no máximo 7 dias de diferença da data atual");
			return;
		}
		
		inicializarRelatorio();
		System.out.println("Hashtag: " + hashtag);
		System.out.println("Período de Pequisa - " + datapesquisa + " - " + datapesquisa.plusDays(Constants.NUMBER_DAYS));
		System.out.println("Relatório pronto, utilize o menu para exibi-lo.");
	}
	
	private static void relatorioCompleto() {
		tweetPorDia();
		retweetPorDia();
		favoritesPorDia();
		totaisPeriodo();	
		relatorioData();		
		relatorioNome();
		mencionaMichel();
	}

	@SuppressWarnings("rawtypes")
	private static void tweetPorDia(){	
		try{
			System.out.println("\n----------------------------Tweets Por Dia------------------------------------------------------");
			for (Map.Entry entry : relatorio.obterTweetPorDia().entrySet()) {
				System.out.println("Data: " + entry.getKey() + " - Total de Tweet: " + entry.getValue());
			}
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro no relatório.", 1);
		}
	}

	@SuppressWarnings("rawtypes")
	private static void retweetPorDia() {
		try{
			System.out.println("\n----------------------------Retweets Por Dia------------------------------------------------------");
			for (Map.Entry entry : relatorio.obterReTweetPorDia().entrySet()) {
				System.out.println("Data: " + entry.getKey() + " - Total de Retweet: " + entry.getValue());
			}
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro no relatório.", 1);
		}

	}

	@SuppressWarnings("rawtypes")
	private static void favoritesPorDia() {
		try{
			System.out.println("\n----------------------------Favorites Por Dia------------------------------------------------------");
			for (Map.Entry entry : relatorio.obterFavoritesPorDia().entrySet()) {
				System.out.println("Data: " + entry.getKey() + " - Total de Favorites: " + entry.getValue());
			}
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro no relatório.", 1);
		}
	}

	private static void totaisPeriodo(){
		System.out.println("\n----------------------------Relatório Totais------------------------------------------------------");
		System.out.println("Total de Tweet: " + relatorio.getTotalTweet());
		System.out.println("Total de Retweet: " + relatorio.getTotalRetweet());
		System.out.println("Total de Favorites: " +relatorio.getTotalFavorites());		
	}
	
	private static void relatorioData() {
		System.out.println("\n----------------------------Relatório Data------------------------------------------------------");
		System.out.println("Data mais recente : " + relatorio.getDataRecente());
		System.out.println("Data mais antiga  : " + relatorio.getDataAntiga());
	}	

	private static void relatorioNome() {
		System.out.println("\n----------------------------Relatório Autor------------------------------------------------------");
		System.out.println("Primeiro nome: " + relatorio.getPrimeiroNome());
		System.out.println("Último nome: " + relatorio.getUltimoNome());
	}
	
	private static void mencionaMichel(){
		try {
			if(relatorio == null) {
				relatorio = new RelatorioTweet();
			}
			String msg = "@michelpf ";
			String texto = JOptionPane.showInputDialog("Escreve uma mensagem para Michel:");
			
			if(texto == null) {
				System.out.println("Mensagem não foi postada");
				return;
			}
			
			msg = msg + texto;
			
			relatorio.escreveTweet(msg);
			System.out.println("Michel mencionado com sucesso - " + msg);
		} catch (TwitterException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao escrever tweet", 1);
		}
	}
	
}
