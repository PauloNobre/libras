package ufc.quixada.polilibras.main;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;

import br.usp.libras.dic.SignDictionary;
import br.usp.libras.jonah.VirtualJonah;
import br.usp.libras.translator.Translator;
import net.sourceforge.javaflacencoder.FLACFileWriter;
import ufc.quixada.polilibras.dic.SQLiteDictionary;

public class PoliLibrasApp {
	private static JTextField textField;
	private static JButton iniciarButton;
	private static JButton pararButton;

	private static final String TITULO = "PoliLibras App";
	private static final String INICIAR_MESSAGE = "Iniciar";
	private static final String PARAR_MESSAGE = "Parar";

	public static void main(String[] args) {

		final JFrame jFrame = new JFrame(TITULO);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		
		final Microphone mic = new Microphone(FLACFileWriter.FLAC);
		final GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
		duplex.setLanguage("pt-BR");

		JPanel jPanel = new JPanel();
		JPanel jPanelCommands = new JPanel(new GridBagLayout());

		final VirtualJonah vJonah = new VirtualJonah();
		vJonah.init();

		textField = new JTextField();
		textField.setPreferredSize(new Dimension(200, 20));
		
		iniciarButton = new JButton(INICIAR_MESSAGE);
		pararButton = new JButton(PARAR_MESSAGE);
		
		pararButton.setEnabled(false);
		
		jPanelCommands.add(iniciarButton);
		jPanelCommands.add(pararButton);

		jPanel.add(vJonah);
		jPanel.add(jPanelCommands);

		jFrame.add(jPanel);
		jFrame.setSize(vJonah.getSize().width, vJonah.getSize().height + 70);

		jFrame.setVisible(true);
		
		iniciarButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());
					iniciarButton.setEnabled(false);
					pararButton.setEnabled(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		pararButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mic.close();
				iniciarButton.setEnabled(true);
				pararButton.setEnabled(false);
			}
		});
		
		duplex.addResponseListener(new GSpeechResponseListener() {
			String texto_audio;

			@Override
			public void onResponse(GoogleResponse gr) {
				String output = "";
				output = gr.getResponse();
				if (gr.getResponse() == null) {
					System.out.println(this.texto_audio);
					
					SignDictionary dic = new SQLiteDictionary();
					Translator translator = new Translator(dic);
					
					translator.setVerbose();
					vJonah.loadSignsFromObject(translator.translate(this.texto_audio));
					vJonah.playSigns();
				}
				System.out.println(output);
                this.texto_audio = output;
			}
		});
	}
}
