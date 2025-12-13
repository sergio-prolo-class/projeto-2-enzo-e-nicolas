package ifsc.joe.domain;

import ifsc.joe.config.Constantes;
import ifsc.joe.enums.TipoRecurso;
import ifsc.joe.interfaces.Posicionavel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Representa um recurso coletável no mapa.
 */
public class Recurso implements Posicionavel {

    private final TipoRecurso tipo;
    private final int quantidade;
    private int posX;
    private int posY;
    private Image imagem;

    public Recurso(TipoRecurso tipo, int x, int y) {
        this.tipo = tipo;
        this.posX = x;
        this.posY = y;
        this.quantidade = Constantes.Recursos.QUANTIDADE_PADRAO;
        this.imagem = carregarImagem();
    }

    private Image carregarImagem() {
        String nomeArquivo = switch (tipo) {
            case COMIDA -> "food";
            case OURO -> "gold";
            case MADEIRA -> "wood";
        };
        try {
            return new ImageIcon(Objects.requireNonNull(
                    getClass().getClassLoader().getResource("./" + nomeArquivo + ".png"))).getImage();
        } catch (Exception e) {
            // Fallback se imagem não existir: cria uma imagem colorida simples em memória
            return null; // Será tratado no draw
        }
    }

    public void desenhar(Graphics g, JPanel painel) {
        if (imagem != null) {
            g.drawImage(imagem, posX, posY, painel);
        } else {
            // Desenho fallback
            g.setColor(getCorPorTipo());
            g.fillOval(posX, posY, 20, 20);
            g.setColor(Color.BLACK);
            g.drawOval(posX, posY, 20, 20);
        }
    }

    private Color getCorPorTipo() {
        return switch (tipo) {
            case COMIDA -> Constantes.Recursos.COR_COMIDA;
            case OURO -> Constantes.Recursos.COR_OURO;
            case MADEIRA -> Constantes.Recursos.COR_MADEIRA;
        };
    }

    @Override
    public int getCentroX() {
        return posX + 10; // Aproximação do centro (20x20)
    }

    @Override
    public int getCentroY() {
        return posY + 10;
    }

    public TipoRecurso getTipo() {
        return tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }
}
