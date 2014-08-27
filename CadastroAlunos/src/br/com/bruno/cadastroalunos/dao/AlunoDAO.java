package br.com.bruno.cadastroalunos.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.bruno.cadastroalunos.modelo.Aluno;

public class AlunoDAO extends SQLiteOpenHelper {

	private static final String DATABASE = "CadastroAluno";
	private static final int VERSAO = 2;

	public AlunoDAO(Context context) {
		super(context, DATABASE, null, VERSAO);
	}

	public void salva(Aluno aluno) {
		ContentValues values = new ContentValues();

		values.put("nome", aluno.getNome());
		values.put("site", aluno.getSite());
		values.put("endereco", aluno.getEndereco());
		values.put("nota", aluno.getNota());
		values.put("foto", aluno.getFoto());
		values.put("telefone", aluno.getTelefone());

		getWritableDatabase().insert("Alunos", null, values);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String ddl = "CREATE TABLE Alunos (id integer PRIMARY KEY autoincrement, nome TEXT UNIQUE NOT NULL,"
				+ " telefone TEXT, endereco TEXT, site TEXT, foto TEXT, nota REAL);";

		db.execSQL(ddl);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String ddl = "DROP TABLE IF EXISTS Alunos";
		db.execSQL(ddl);

		this.onCreate(db);
	}

	public List<Aluno> getLista() {
		String[] colunas = { "id", "nome", "site", "telefone", "endereco",
				"foto", "nota" };

		Cursor cursor = getWritableDatabase().query("Alunos", colunas, null,
				null, null, null, null);

		List<Aluno> alunos = new ArrayList<Aluno>();

		while (cursor.moveToNext()) {

			Aluno aluno = new Aluno();

			aluno.setId(cursor.getLong(0));
			aluno.setNome(cursor.getString(1));
			aluno.setSite(cursor.getString(2));
			aluno.setTelefone(cursor.getString(3));
			aluno.setEndereco(cursor.getString(4));
			aluno.setFoto(cursor.getString(5));
			aluno.setNota(cursor.getDouble(6));
			
			alunos.add(aluno);
		}

		return alunos;
	}

	public void deletar(Aluno aluno) {
		String[] args = {aluno.getId().toString()};
		
		getWritableDatabase().delete("Alunos", "id=?", args);
		
	}

	public void altera(Aluno aluno) {
		ContentValues values = new ContentValues();

		values.put("nome", aluno.getNome());
		values.put("site", aluno.getSite());
		values.put("endereco", aluno.getEndereco());
		values.put("nota", aluno.getNota());
		values.put("foto", aluno.getFoto());
		values.put("telefone", aluno.getTelefone());

		String[] args = {aluno.getId().toString()};
		getWritableDatabase().update("Alunos", values, "id=?", args);

	}

}
