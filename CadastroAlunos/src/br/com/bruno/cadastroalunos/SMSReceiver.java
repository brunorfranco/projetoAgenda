package br.com.bruno.cadastroalunos;

import br.com.bruno.cadastroalunos.dao.AlunoDAO;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Object[] mensagens = (Object[]) intent.getExtras().get("pdus");
		
		byte[] primeira = (byte[]) mensagens[0];
		
		SmsMessage sms = SmsMessage.createFromPdu(primeira);
		
		String telefone = sms.getDisplayOriginatingAddress();
		
		AlunoDAO dao = new AlunoDAO(context);
		
		boolean ehAluno = dao.isAluno(telefone);
		
		dao.close();
		
		if(ehAluno){
			MediaPlayer create = MediaPlayer.create(context, R.raw.musica);
			create.start();
			Toast.makeText(context, "Tocando musica", Toast.LENGTH_LONG).show();
		}
	}

}
