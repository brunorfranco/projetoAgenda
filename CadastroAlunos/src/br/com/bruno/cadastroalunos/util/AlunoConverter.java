package br.com.bruno.cadastroalunos.util;

import java.util.List;

import org.json.JSONException;
import org.json.JSONStringer;

import br.com.bruno.cadastroalunos.modelo.Aluno;

public class AlunoConverter {

	public String toJson(List<Aluno> alunos) {
		try{
			JSONStringer jsonStringer = new JSONStringer();
		
			jsonStringer.object().key("list").array();
			
			jsonStringer.object().key("aluno").array();
			
			for (Aluno aluno : alunos) {
				jsonStringer.object();
				jsonStringer.key("nome").value(aluno.getNome());
				jsonStringer.key("nota").value(aluno.getNota());
				jsonStringer.endObject();
				
			}
			
			jsonStringer.endArray().endObject();
			jsonStringer.endArray().endObject();
			
			return jsonStringer.toString();
		} catch(JSONException e){
			throw new RuntimeException();
		}
	}

}
