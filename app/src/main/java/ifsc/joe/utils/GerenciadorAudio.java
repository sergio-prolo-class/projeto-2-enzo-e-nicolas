package ifsc.joe.utils;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * Classe utilitária para gerenciar efeitos sonoros do jogo.
 * Responsável por carregar e tocar arquivos de áudio.
 */
public class GerenciadorAudio {

    private static GerenciadorAudio instancia;

    private GerenciadorAudio() {
        // Construtor privado para Singleton
    }

    public static GerenciadorAudio getInstancia() {
        if (instancia == null) {
            instancia = new GerenciadorAudio();
        }
        return instancia;
    }

    /**
     * Toca um efeito sonoro.
     * O arquivo deve estar na pasta resources (raiz ou subpastas).
     *
     * @param nomeArquivo nome do arquivo de áudio (ex: "ataque.wav")
     */
    public void tocarSom(String nomeArquivo) {
        new Thread(() -> {
            try {
                URL url = getClass().getClassLoader().getResource(nomeArquivo);
                if (url == null) {
                    System.err.println("[AUDIO] Arquivo não encontrado: " + nomeArquivo);
                    return;
                }

                // Usa BufferedInputStream para garantir suporte a mark/reset
                // Isso resolve a maioria dos erros de "URL of unsupported format" quando lendo
                // de JARs ou recursos
                java.io.InputStream audioSrc = url.openStream();
                java.io.InputStream bufferedIn = new java.io.BufferedInputStream(audioSrc);

                AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);

                // Listener para fechar o stream liberar recursos ao terminar
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });

                clip.start();
            } catch (UnsupportedAudioFileException e) {
                System.err.println("[AUDIO] Formato inválido para " + nomeArquivo + ": " + e.getMessage());
                System.err.println("Certifique-se de usar arquivos .WAV PCM 16-bit (não MP3 renomeados).");
            } catch (IOException | LineUnavailableException e) {
                System.err.println("[AUDIO] Erro ao tocar " + nomeArquivo + ": " + e.getMessage());
            } catch (Exception e) {
                System.err.println("[AUDIO] Erro inesperado: " + e.getMessage());
            }
        }).start();
    }
}
