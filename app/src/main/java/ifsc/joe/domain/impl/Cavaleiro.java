package ifsc.joe.domain.impl;

import ifsc.joe.domain.Personagem;

import java.awt.*;

/**
 * Classe que representa um Cavaleiro no jogo.
 * O Cavaleiro pode alternar entre montado e desmontado.
 * Quando montado, é mais rápido. Quando desmontado, é um guerreiro.
 * Especializado em combate corpo a corpo com alcance médio (75px).
 */
public class Cavaleiro extends Personagem {

    private static final String NOME_IMAGEM_MONTADO = "cavaleiro";
    private static final String NOME_IMAGEM_MONTADO_ATACANDO = "cavaleiro2";
    private static final String NOME_IMAGEM_DESMONTADO = "guerreiro";
    private static final String NOME_IMAGEM_DESMONTADO_ATACANDO = "guerreiro2";
    private static final int VELOCIDADE_MONTADO = 20;
    private static final int VELOCIDADE_DESMONTADO = 10;
    private static final int ATAQUE = 25;
    private static final int VIDA = 150;
    private static final int ALCANCE = 75;

    private boolean montado;

    public Cavaleiro(int x, int y) {
        super(x, y);
        this.montado = true; // Começa montado por padrão
    }

    @Override
    public String getNomeImagem() {
        return montado ? NOME_IMAGEM_MONTADO : NOME_IMAGEM_DESMONTADO;
    }

    @Override
    public String getNomeImagemAtacando() {
        // Retorna a imagem de ataque correspondente ao estado de montaria
        return montado ? NOME_IMAGEM_MONTADO_ATACANDO : NOME_IMAGEM_DESMONTADO_ATACANDO;
    }

    /**
     * Alterna o estado de montaria do cavaleiro.
     * Quando montado, fica mais rápido. Quando desmontado, é um guerreiro.
     */
    public void alternarMontado() {
        this.montado = !this.montado;
    }

    /**
     * Verifica se o cavaleiro está montado.
     *
     * @return true se montado, false se desmontado
     */
    public boolean isMontado() {
        return montado;
    }

    /**
     * A velocidade do Cavaleiro depende se está montado ou não.
     * Montado é mais rápido, desmontado tem velocidade normal.
     *
     * @return velocidade em pixels por movimento
     */
    @Override
    public int getVelocidade() {
        return montado ? VELOCIDADE_MONTADO : VELOCIDADE_DESMONTADO;
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

