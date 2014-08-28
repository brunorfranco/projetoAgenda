package br.com.bruno.cadastroalunos.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.bruno.cadastroalunos.R;
import br.com.bruno.cadastroalunos.modelo.Aluno;

public class ListaAlunosAdapter extends BaseAdapter{

	private List<Aluno> alunos;
	private Activity context;

	public ListaAlunosAdapter(List<Aluno> alunos, Activity context) {
		this.alunos = alunos;
		this.context = context;
	}

	@Override
	public int getCount() {
		return alunos.size();
	}

	@Override
	public Object getItem(int position) {
		return alunos.get(position);
	}

	@Override
	public long getItemId(int position) {
		Long id = alunos.get(position).getId();
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Aluno aluno = alunos.get(position);
		
		LayoutInflater inflater = context.getLayoutInflater();
		View linha = inflater.inflate(R.layout.linha_listagem, null);
		
		TextView nome = (TextView) linha.findViewById(R.id.nome);
		nome.setText(aluno.getNome());
		
		ImageView foto = (ImageView) linha.findViewById(R.id.foto);
		
		if(aluno.getFoto() != null){
			Bitmap fotoAluno = BitmapFactory.decodeFile(aluno.getFoto());
			Bitmap fotoReduzida = Bitmap.createScaledBitmap(fotoAluno, 100, 100, true);

			foto.setImageBitmap(fotoReduzida);
		} else {
			Drawable semFoto = context.getResources().getDrawable(R.drawable.ic_launcher);
			foto.setImageDrawable(semFoto );
		}
		
		//tratamento para o celular deitado, na posicao `land`
//		TextView telefone = (TextView) convertView.findViewById(R.id.telefone);
//		if(telefone != null){
//			telefone.setText(aluno.getTelefone());
//		}
//
//		TextView site = (TextView) convertView.findViewById(R.id.site);
//		if(site != null){
//			site.setText(aluno.getSite());
//		}
		
		return linha;
	}

}
