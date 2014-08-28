package br.com.bruno.cadastroalunos.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class WebClient {

	private String urlServer;

	public WebClient(String urlServer) {
		this.urlServer = urlServer;
	}

	public String post(String dadosJson) {
		try {

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(urlServer);
		
			post.setEntity(new StringEntity(dadosJson ));
			post.setHeader("Content-type", "application/json");
			post.setHeader("Accept", "application/json");
			
			HttpResponse response = client.execute(post);
			HttpEntity resposta = response.getEntity();
			
			String respostaJson = EntityUtils.toString(resposta);
			
			return respostaJson;
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	

}
