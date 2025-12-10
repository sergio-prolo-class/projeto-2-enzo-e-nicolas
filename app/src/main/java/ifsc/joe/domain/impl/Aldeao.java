package ifsc.joe.domain.impl;

import ifsc.joe.domain.Personagem;

import java.awt.*;

/**
 * Classe que representa um Aldeão no jogo.
 * O Aldeão é um personagem básico com velocidade padrão.
 * Possui alcance curto de ataque (50px).
 */
public class Aldeao extends Personagem {

    private static final String NOME_IMAGEM = "aldeao";
    private static final String NOME_IMAGEM_ATACANDO = "aldeao2";
    private static final int ALCANCE = 50;

    public Aldeao(int x, int y) {
        super(x, y);
    }

    @Override
    public String getNomeImagem() {
        return NOME_IMAGEM;
    }

    @Override
    public String getNomeImagemAtacando() {
        return NOME_IMAGEM_ATACANDO;
    }

    /**
     * O Aldeão tem alcance curto de ataque.
     *
     * @return alcance de ataque em pixels
     */
    @Override
    public int getAlcanceAtaque() {
        return ALCANCE;
    }

    /**
     * O Aldeão tem um ataque fraco.
     *
     * @return valor de ataque base
     */
    @Override
    public int getAtaqueBase() {
        return 5;
    }

    /**
     * Cor da aura do Aldeão (marrom/terra).
     *
     * @return cor da aura de alcance
     */
    @Override
    public Color getCorAlcance() {
        return new Color(139, 90, 43, 50); // Marrom semi-transparente
    }
}
