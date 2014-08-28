package br.com.bruno.cadastroalunos.task;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.bruno.cadastroalunos.dao.AlunoDAO;
import br.com.bruno.cadastroalunos.modelo.Aluno;
import br.com.bruno.cadastroalunos.util.AlunoConverter;
import br.com.bruno.cadastroalunos.util.WebClient;

public class EnviaAlunosTask extends AsyncTask<Integer, Double, String>{

	private Activity context;
	private ProgressDialog progress;
	
	public EnviaAlunosTask(Activity context) {
		this.context = context;
	}
	
	@Override
	protected void onPreExecute() {
		progress = ProgressDialog.show(context, "Aguarde...", "Enviando dados", true, true);
	
	}
	@Override
	protected String doInBackground(Integer... params) {
		
		String urlServer = "http://caelum.com.br/mobile";
		
		AlunoDAO dao = new AlunoDAO(context);
		List<Aluno> alunos = dao.getLista();
		dao.close();
		
		String dadosJson = new AlunoConverter().toJson(alunos);
		
		WebClient client = new WebClient(urlServer);
		String respostaJson = client.post(dadosJson);
		
		return respostaJson;
	}
	
	@Override
	protected void onPostExecute(String result) {
		progress.dismiss();
		Toast.makeText(context, result, Toast.LENGTH_LONG).show();;
	
	}

}
