package br.com.bruno.cadastroalunos;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import br.com.bruno.cadastroalunos.adapter.ListaAlunosAdapter;
import br.com.bruno.cadastroalunos.dao.AlunoDAO;
import br.com.bruno.cadastroalunos.modelo.Aluno;

public class ListaAlunos extends ActionBarActivity {

	private ListView lista;
	private Aluno aluno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listagem_alunos);

		lista = (ListView) findViewById(R.id.lista);
		
		registerForContextMenu(lista);
		
		lista.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {

				Aluno alunoClicado = (Aluno) adapter.getItemAtPosition(position);
				
				Intent irParaFormulario = new Intent(ListaAlunos.this, Formulario.class);
				irParaFormulario.putExtra("alunoSelecionado", alunoClicado);
				
				startActivity(irParaFormulario);
			}
		
		});
		
		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int position, long id) {

				aluno = (Aluno) adapter.getItemAtPosition(position);
				
				return false;
			}
		});
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		MenuItem ligar = menu.add("Ligar");
		ligar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent irParaTelaDiscagem = new Intent(Intent.ACTION_CALL);
				Uri discarPara = Uri.parse("tel:"+aluno.getTelefone());
				irParaTelaDiscagem.setData(discarPara);
				
				startActivity(irParaTelaDiscagem);
				return false;
			}
		});
		menu.add("Enviar SMS");
		MenuItem site = menu.add("Navegar no site");
		site.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent irParaOSite = new Intent(Intent.ACTION_VIEW);
				Uri localSite = Uri.parse("http://"+aluno.getSite());
				irParaOSite.setData(localSite);
				
				startActivity(irParaOSite);
				return false;
			}
		});
		
		MenuItem deletar = menu.add("Deletar");
		deletar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				AlunoDAO dao = new AlunoDAO(ListaAlunos.this);
				dao.deletar(aluno);
				dao.close();
				carregaListaAlunos();
				return false;
			}
		});
		menu.add("Ver no mapa");
		menu.add("Enviar e-mail");
		
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.lista_alunos, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemClicado = item.getItemId();
		
		switch (itemClicado) {
		case R.id.novo:
			Intent intent = new Intent(this, Formulario.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		carregaListaAlunos();
	}

	private void carregaListaAlunos() {
		AlunoDAO dao = new AlunoDAO(this);
		List<Aluno> alunos = dao.getLista();
		dao.close();
		
		ListaAlunosAdapter adapter = new ListaAlunosAdapter(alunos, this);
		
		lista.setAdapter(adapter);
	}
}
