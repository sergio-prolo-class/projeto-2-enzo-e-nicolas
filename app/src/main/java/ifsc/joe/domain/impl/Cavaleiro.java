package ifsc.joe.domain.impl;

import ifsc.joe.domain.Personagem;

import java.awt.*;

/**
 * Classe que representa um Cavaleiro no jogo.
 * O Cavaleiro é um personagem montado, com maior velocidade de movimento.
 * Especializado em combate corpo a corpo com alcance médio (75px).
 */
public class Cavaleiro extends Personagem {

    private static final String NOME_IMAGEM = "cavaleiro";
    private static final String NOME_IMAGEM_ATACANDO = "cavaleiro2";
    private static final int VELOCIDADE = 20;
    private static final int ATAQUE = 25;
    private static final int VIDA = 150;
    private static final int ALCANCE = 75;

    public Cavaleiro(int x, int y) {
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
     * O Cavaleiro, por estar montado, é o personagem mais rápido.
     *
     * @return velocidade em pixels por movimento
     */
    @Override
    public int getVelocidade() {
        return VELOCIDADE;
    }

    /**
     * O Cavaleiro tem alto poder de ataque devido à força do combate montado.
     *
     * @return valor de ataque do cavaleiro
     */
    @Override
    public int getAtaqueBase() {
        return ATAQUE;
    }

    /**
     * O Cavaleiro tem mais vida por usar armadura pesada.
     *
     * @return vida inicial do cavaleiro
     */
    @Override
    public int getVidaInicial() {
        return VIDA;
    }

    /**
     * O Cavaleiro tem alcance médio de ataque (lança/espada montada).
     *
     * @return alcance de ataque em pixels
     */
    @Override
    public int getAlcanceAtaque() {
        return ALCANCE;
    }

    /**
     * Cor da aura do Cavaleiro (azul).
     *
     * @return cor da aura de alcance
     */
    @Override
    public Color getCorAlcance() {
        return new Color(65, 105, 225, 50); // Azul real semi-transparente
    }
}

