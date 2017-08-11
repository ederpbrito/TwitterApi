package br.com.fiap.util;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Classe que é responsavel por recuperar um instância da classe Twitter,
 * representando o padrao de projeto Singleton
 * @author Eder Brito, Felipe, Alexandre
 *
 */
public class ConnectFactory {

	private static Twitter twitter;
	
	public static Twitter getInstance(){
		if(twitter == null){
			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setOAuthConsumerKey(Constants.CONSUMER_KEY);
			builder.setOAuthConsumerSecret(Constants.CONSUMER_SECRET);
			
			Configuration config = builder.build();
			
			TwitterFactory factory = new TwitterFactory(config);
			
			twitter = factory.getInstance(); 
			
			twitter.setOAuthAccessToken(loadAccessToken());
		
		}
		
		return twitter;
	}
	
	private static AccessToken loadAccessToken(){
		String token = Constants.TOKEN;
				String tokenSecret = Constants.TOKEN_SECRET;
		return new AccessToken(token, tokenSecret);
	}
	
}
