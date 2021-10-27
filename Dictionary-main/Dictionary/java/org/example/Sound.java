package org.example;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Sound {

  Voice voice;

  public Sound() {
    System.setProperty("freetts.voices",
        "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
    voice = VoiceManager.getInstance().getVoice("kevin16");
    if (voice != null) {
      voice.allocate();// Allocating Voice
      try {
        voice.setRate(190);// Setting the rate of the voice
        voice.setPitch(150);// Setting the Pitch of the voice
        voice.setVolume(3);// Setting the volume of the voice
      } catch (Exception e1) {
        e1.printStackTrace();
      }

    } else {
      throw new IllegalStateException();
    }
  }

  public void Speak(String words) {
    voice.speak(words);
  }
}
