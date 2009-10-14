import java.io.File;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Music{
	private static Sequencer sm_sequencer = null;
	private static Synthesizer	sm_synthesizer = null;
    private static boolean errorMusic = true;
    private SonWav son;

    public SonWav getSonWav(){
        return this.son;
    }

    public void setSonWav(String s){
        this.son = new SonWav(s);
    }

	public void run(String dossier){
		String strFilename = "sprites/maps/"+dossier+"/music.mid";
		Sequence sequence = null;
		try{
			sequence = MidiSystem.getSequence(getClass().getClassLoader().getResource(strFilename));
            sm_sequencer = MidiSystem.getSequencer();
		}
		catch (InvalidMidiDataException e){
			System.out.println(e);
		}
		catch (IOException e){
			System.out.println(e);
		}
		catch (MidiUnavailableException e){
            if (errorMusic){
            Icon image = new ImageIcon("sprites/musique.png");
            JOptionPane.showMessageDialog(null,"Une ou plusieurs applications empêcheront les effets sonores du jeu, veuillez toutes les fermer telles que lecteur audio et navigateur internet, puis relancer le jeu.", "Musique coupée",JOptionPane.INFORMATION_MESSAGE, image);
            errorMusic = false;
            }    
		}
		if (sm_sequencer != null){
		    sm_sequencer.addMetaEventListener(new MetaEventListener(){
				public void meta(MetaMessage event){
					if (event.getType() == 47){
						sm_sequencer.close();
						if (sm_synthesizer != null){
							sm_synthesizer.close();
						}
						System.exit(0);
					}
				}
			});
        
		try{
			sm_sequencer.open();
            sm_sequencer.setSequence(sequence);
		}
		catch (MidiUnavailableException e){
            if (errorMusic){
            Icon image = new ImageIcon("sprites/musique.png");
            JOptionPane.showMessageDialog(null,"Une ou plusieurs applications empêcheront les effets sonores du jeu, veuillez toutes les fermer telles que lecteur audio et navigateur internet, puis relancer le jeu.", "Musique coupée",JOptionPane.INFORMATION_MESSAGE, image);
            errorMusic = false;
            }    
		}
		catch (InvalidMidiDataException e){
            System.out.println(e);
		}
		if (! (sm_sequencer instanceof Synthesizer)){
			try{
				sm_synthesizer = MidiSystem.getSynthesizer();
				sm_synthesizer.open();
				Receiver synthReceiver = sm_synthesizer.getReceiver();
				Transmitter seqTransmitter = sm_sequencer.getTransmitter();
				seqTransmitter.setReceiver(synthReceiver);
			}
			catch (MidiUnavailableException e){
            if (errorMusic){
            Icon image = new ImageIcon("sprites/musique.png");
            JOptionPane.showMessageDialog(null,"Une ou plusieurs applications empêcheront les effets sonores du jeu, veuillez toutes les fermer telles que lecteur audio et navigateur internet, puis relancer le jeu.", "Musique coupée",JOptionPane.INFORMATION_MESSAGE, image);
            errorMusic = false;
            }    
			}
		}
		sm_sequencer.start();
        sm_sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
	}
    }

	private static void out(String strMessage){
		System.out.println(strMessage);
	}

    public Sequencer getSeq(){
        return sm_sequencer;
    }

    class SonWav {        
        private AudioInputStream audioStream = null;
        private SourceDataLine line = null;
        private AudioFormat audioFormat = null;
        
        public AudioInputStream getAudioInputStream(){
            return audioStream;
        }

        public SourceDataLine getSourceDataLine(){
            return line;
        }
         
        public SonWav(String fich){
            try{
                audioStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource(fich));
                audioFormat = audioStream.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class,audioFormat); 
                line = (SourceDataLine) AudioSystem.getLine(info);
             }catch (Exception e) {}		
         }

        public boolean open(){
            try{
                line.open(audioFormat);
            }catch (Exception e) {
                return false;
            }
                return true;
        }
         
        public void close(){
            getSourceDataLine().close();
        }

        public void play(){
            new Thread("monThread"){ 
            public void run(){
                getSonWav().getSourceDataLine().start();
                try{
                    byte bytes[] = new byte[1024];
                    int bytesRead=0;
                    while (((bytesRead = getSonWav().getAudioInputStream().read(bytes,0,bytes.length)) != -1)) {
                        getSonWav().getSourceDataLine().write(bytes,0,bytesRead);
                }
                }catch(IOException io){
                    return;
                }
            } 
            }.start(); 
        }

        public void stop(){
            line.stop();
        }     
    }

    public void lancer(){
        try{
            this.getSonWav().open();
            this.getSonWav().play();
        }catch (Exception e){}
    }
}

