package ifsc.joe.domain;

import ifsc.joe.enums.Direcao;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * Classe abstrata que representa a base de todos os personagens do jogo.
 * Implementa o padrão de herança e polimorfismo.
 */
public abstract class Personagem {

    protected int posX;
    protected int posY;
    protected boolean atacando;
    protected Image icone;
    protected int vida;
    protected int ataque;
    protected float opacidade;
    protected boolean morrendo;

    /**
     * Construtor base para todos os personagens
     *
     * @param x coordenada X inicial
     * @param y coordenada Y inicial
     */
    public Personagem(int x, int y) {
        this.posX = x;
        this.posY = y;
        this.atacando = false;
        this.icone = carregarImagem(getNomeImagem());
        this.vida = getVidaInicial();
        this.ataque = getAtaqueBase();
        this.opacidade = 1.0f;
        this.morrendo = false;
    }

    /**
     * Retorna a vida inicial do personagem.
     * Pode ser sobrescrito para personagens com mais ou menos vida.
     *
     * @return vida inicial do personagem
     */
    public int getVidaInicial() {
        return 100;
    }

    /**
     * Retorna o valor base de ataque do personagem.
     * Pode ser sobrescrito para personagens com mais ou menos ataque.
     *
     * @return valor de ataque base
     */
    public int getAtaqueBase() {
        return 0;
    }

    /**
     * Retorna o nome base da imagem do personagem (sem extensão).
     * Cada subclasse deve implementar este método.
     *
     * @return nome da imagem do personagem
     */
    public abstract String getNomeImagem();

    /**
     * Retorna o nome base da imagem quando o personagem está atacando.
     * Pode ser sobrescrito pelas subclasses para customizar a animação de ataque.
     *
     * @return nome da imagem de ataque do personagem
     */
    public String getNomeImagemAtacando() {
        return getNomeImagem();
    }

    /**
     * Desenha o personagem no JPanel utilizando as coordenadas X e Y.
     * Aplica transparência quando o personagem está morrendo.
     *
     * @param g objeto Graphics do JPanel
     * @param painel JPanel onde o personagem será desenhado
     */
    public void desenhar(Graphics g, JPanel painel) {
        String nomeImagem = atacando ? getNomeImagemAtacando() : getNomeImagem();
        this.icone = carregarImagem(nomeImagem);
        
        // Aplica efeito de transparência
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacidade));
        g2d.drawImage(this.icone, this.posX, this.posY, painel);
        g2d.dispose();
    }

    /**
     * Atualiza as coordenadas X e Y do personagem baseado na direção
     *
     * @param direcao direção do movimento
     * @param maxLargura largura máxima da área de movimento
     * @param maxAltura altura máxima da área de movimento
     */
    public void mover(Direcao direcao, int maxLargura, int maxAltura) {
        int velocidade = getVelocidade();
        
        switch (direcao) {
            case CIMA     -> this.posY -= velocidade;
            case BAIXO    -> this.posY += velocidade;
            case ESQUERDA -> this.posX -= velocidade;
            case DIREITA  -> this.posX += velocidade;
        }

        // Não permite que a imagem seja desenhada fora dos limites
        this.posX = Math.min(Math.max(0, this.posX), maxLargura - this.icone.getWidth(null));
        this.posY = Math.min(Math.max(0, this.posY), maxAltura - this.icone.getHeight(null));
    }

    /**
     * Retorna a velocidade de movimento do personagem.
     * Pode ser sobrescrito para personagens mais rápidos ou lentos.
     *
     * @return velocidade em pixels por movimento
     */
    public int getVelocidade() {
        return 10;
    }

    /**
     * Alterna o estado de ataque do personagem
     */
    public void atacar() {
        this.atacando = !this.atacando;
    }

    /**
     * Verifica se o personagem está atacando
     *
     * @return true se está atacando, false caso contrário
     */
    public boolean isAtacando() {
        return atacando;
    }

    /**
     * Retorna a coordenada X atual do personagem
     *
     * @return posição X
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Retorna a coordenada Y atual do personagem
     *
     * @return posição Y
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Retorna a vida atual do personagem
     *
     * @return vida atual
     */
    public int getVida() {
        return vida;
    }

    /**
     * Retorna o valor de ataque do personagem
     *
     * @return valor de ataque
     */
    public int getAtaque() {
        return ataque;
    }

    /**
     * Verifica se o personagem está vivo
     *
     * @return true se vida > 0, false caso contrário
     */
    public boolean estaVivo() {
        return vida > 0;
    }

    /**
     * Aplica dano ao personagem, reduzindo sua vida.
     * A vida não pode ficar negativa. Se a vida chegar a zero, inicia o processo de morte.
     *
     * @param dano quantidade de dano a ser aplicado
     */
    public void sofrerDano(int dano) {
        this.vida = Math.max(0, this.vida - dano);
        if (this.vida <= 0) {
            this.morrendo = true;
        }
    }

    /**
     * Verifica se o personagem está no processo de morrer (fade-out)
     *
     * @return true se está morrendo, false caso contrário
     */
    public boolean estaMorrendo() {
        return morrendo;
    }

    /**
     * Retorna a opacidade atual do personagem (para efeito visual)
     *
     * @return valor entre 0.0 e 1.0
     */
    public float getOpacidade() {
        return opacidade;
    }

    /**
     * Reduz a opacidade do personagem para efeito de fade-out.
     *
     * @param reducao valor a reduzir da opacidade
     * @return true se ainda tem opacidade, false se já desapareceu completamente
     */
    public boolean reduzirOpacidade(float reducao) {
        this.opacidade = Math.max(0, this.opacidade - reducao);
        return this.opacidade > 0;
    }

    /**
     * Verifica se o personagem completou o efeito de desaparecimento
     *
     * @return true se o personagem já desapareceu completamente
     */
    public boolean desapareceuCompletamente() {
        return morrendo && opacidade <= 0;
    }

    /**
     * Método auxiliar para carregar uma imagem do disco
     *
     * @param imagem nome da imagem (sem extensão)
     * @return objeto Image carregado
     */
    protected Image carregarImagem(String imagem) {
        return new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("./" + imagem + ".png")
        )).getImage();
    }
}

