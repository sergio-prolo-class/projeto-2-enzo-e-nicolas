package ifsc.joe.domain.impl;

import ifsc.joe.domain.Personagem;

/**
 * Classe que representa um Cavaleiro no jogo.
 * O Cavaleiro é um personagem montado, com maior velocidade de movimento.
 * Especializado em combate corpo a corpo.
 */
public class Cavaleiro extends Personagem {

    private static final String NOME_IMAGEM = "cavaleiro";
    private static final String NOME_IMAGEM_ATACANDO = "cavaleiro2";
    private static final int VELOCIDADE = 20;
    private static final int ATAQUE = 25;
    private static final int VIDA = 150;

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
}

