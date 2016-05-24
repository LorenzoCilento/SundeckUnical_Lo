package sundekunical.sfx;

import javax.sound.sampled.*;

public class SoundEffectProvider  {

	private Clip clip;
	private static SoundEffectProvider explosionSound = new SoundEffectProvider("/sfx/explosion.wav", -10);
	private static SoundEffectProvider captured = new SoundEffectProvider("/sfx/capturedBonus.wav", -10);
	private static SoundEffectProvider coinsSound = new SoundEffectProvider("/sfx/coins.wav", -10);
	public SoundEffectProvider(String s, float f) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(s));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(),
					16,
					baseFormat.getChannels(),
					baseFormat.getChannels()*2,
					baseFormat.getSampleRate(),
					false
					);
			
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat,ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			FloatControl vol = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			vol.setValue(f);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void play() {
		if(clip == null) return;
			stop();
		if(clip.isRunning())
			clip.stop();
		clip.setFramePosition(0);
		while(!clip.isRunning())
			clip.start();
	}
	
	public void stop(){
		if(clip.isRunning())
			clip.stop();
	}
	
	public void loop() {
		if(clip == null) return;
		stop();
	if(clip.isRunning())
		clip.stop();
	clip.setFramePosition(0);
	if(!clip.isRunning()) {
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
		
	}
	
	public void close() {
		stop();
		clip.stop();
	}
	
	public Clip getClip() {
		return clip;
	}

	public static SoundEffectProvider getExplosionSound() {
		return explosionSound;
	}

	public static SoundEffectProvider getCaptured() {
		return captured;
	}

	public static SoundEffectProvider getCoinsSound() {
		return coinsSound;
	}
	
}