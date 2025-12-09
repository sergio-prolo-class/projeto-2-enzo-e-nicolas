package ifsc.joe.ui;

import ifsc.joe.domain.Personagem;
import ifsc.joe.domain.impl.Aldeao;
import ifsc.joe.domain.impl.Arqueiro;
import ifsc.joe.domain.impl.Cavaleiro;
import ifsc.joe.enums.Direcao;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Classe responsável por gerenciar a área de jogo.
 * Utiliza polimorfismo para tratar todos os personagens de forma uniforme.
 */
public class Tela extends JPanel {

    private final Set<Personagem> personagens;
    
    // Contadores de baixas por tipo
    private int baixasAldeoes;
    private int baixasArqueiros;
    private int baixasCavaleiros;
    
    // Timer para animação de fade-out
    private Timer timerFadeOut;
    private static final float REDUCAO_OPACIDADE = 0.1f;
    private static final int INTERVALO_FADE = 50; // milissegundos

    public Tela() {
        this.setBackground(Color.white);
        this.personagens = new HashSet<>();
        this.baixasAldeoes = 0;
        this.baixasArqueiros = 0;
        this.baixasCavaleiros = 0;
    }

    /**
     * Método invocado sempre que o JPanel precisa ser redesenhado.
     *
     * @param g Graphics componente de java.awt
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Percorrendo a lista de personagens e pedindo para cada um se desenhar na tela
        // Polimorfismo: cada personagem sabe como se desenhar
        this.personagens.forEach(personagem -> personagem.desenhar(g, this));

        // Liberando o contexto gráfico
        g.dispose();
    }

    /**
     * Cria um aldeão nas coordenadas X e Y e adiciona à lista de personagens.
     *
     * @param x coordenada X
     * @param y coordenada Y
     */
    public void criarAldeao(int x, int y) {
        Aldeao aldeao = new Aldeao(x, y);
        aldeao.desenhar(super.getGraphics(), this);
        this.personagens.add(aldeao);
    }

    /**
     * Cria um arqueiro nas coordenadas X e Y e adiciona à lista de personagens.
     *
     * @param x coordenada X
     * @param y coordenada Y
     */
    public void criarArqueiro(int x, int y) {
        Arqueiro arqueiro = new Arqueiro(x, y);
        arqueiro.desenhar(super.getGraphics(), this);
        this.personagens.add(arqueiro);
    }

    /**
     * Cria um cavaleiro nas coordenadas X e Y e adiciona à lista de personagens.
     *
     * @param x coordenada X
     * @param y coordenada Y
     */
    public void criarCavaleiro(int x, int y) {
        Cavaleiro cavaleiro = new Cavaleiro(x, y);
        cavaleiro.desenhar(super.getGraphics(), this);
        this.personagens.add(cavaleiro);
    }

    /**
     * Atualiza as coordenadas X ou Y de todos os personagens.
     * Polimorfismo: cada personagem se move de acordo com sua velocidade.
     *
     * @param direcao direção para movimentar
     */
    public void movimentarPersonagens(Direcao direcao) {
        this.personagens.forEach(personagem -> 
            personagem.mover(direcao, this.getWidth(), this.getHeight())
        );

        // Depois que as coordenadas foram atualizadas é necessário repintar o JPanel
        this.repaint();
    }

    /**
     * Movimenta apenas os aldeões.
     *
     * @param direcao direção para movimentar
     */
    public void movimentarAldeoes(Direcao direcao) {
        this.personagens.stream()
            .filter(p -> p instanceof Aldeao)
            .forEach(p -> p.mover(direcao, this.getWidth(), this.getHeight()));
        this.repaint();
    }

    /**
     * Movimenta apenas os arqueiros.
     *
     * @param direcao direção para movimentar
     */
    public void movimentarArqueiros(Direcao direcao) {
        this.personagens.stream()
            .filter(p -> p instanceof Arqueiro)
            .forEach(p -> p.mover(direcao, this.getWidth(), this.getHeight()));
        this.repaint();
    }

    /**
     * Movimenta apenas os cavaleiros.
     *
     * @param direcao direção para movimentar
     */
    public void movimentarCavaleiros(Direcao direcao) {
        this.personagens.stream()
            .filter(p -> p instanceof Cavaleiro)
            .forEach(p -> p.mover(direcao, this.getWidth(), this.getHeight()));
        this.repaint();
    }

    /**
     * Altera o estado de ataque de todos os personagens.
     * Cavaleiros e arqueiros aplicam dano a todos os outros personagens.
     */
    public void atacarPersonagens() {
        // Coleta todos os atacantes (cavaleiros e arqueiros)
        List<Personagem> atacantes = this.personagens.stream()
            .filter(p -> p instanceof Cavaleiro || p instanceof Arqueiro)
            .collect(Collectors.toList());

        // Cada atacante aplica dano a todos os outros personagens (exceto a si mesmo)
        for (Personagem atacante : atacantes) {
            atacante.atacar();
            aplicarDanoAosAlvos(atacante);
        }

        removerPersonagensMortos();
        this.repaint();
    }

    /**
     * Faz todos os arqueiros atacarem todos os outros personagens.
     */
    public void atacarArqueiros() {
        List<Personagem> arqueiros = this.personagens.stream()
            .filter(p -> p instanceof Arqueiro)
            .collect(Collectors.toList());

        for (Personagem arqueiro : arqueiros) {
            arqueiro.atacar();
            aplicarDanoAosAlvos(arqueiro);
        }

        removerPersonagensMortos();
        this.repaint();
    }

    /**
     * Faz todos os cavaleiros atacarem todos os outros personagens.
     */
    public void atacarCavaleiros() {
        List<Personagem> cavaleiros = this.personagens.stream()
            .filter(p -> p instanceof Cavaleiro)
            .collect(Collectors.toList());

        for (Personagem cavaleiro : cavaleiros) {
            cavaleiro.atacar();
            aplicarDanoAosAlvos(cavaleiro);
        }

        removerPersonagensMortos();
        this.repaint();
    }

    /**
     * Aplica dano do atacante aos personagens que estão dentro do alcance.
     * Usa a distância entre os centros dos personagens para verificar o alcance.
     *
     * @param atacante personagem que está atacando
     */
    private void aplicarDanoAosAlvos(Personagem atacante) {
        int dano = atacante.getAtaque();
        int alcance = atacante.getAlcanceAtaque();
        
        this.personagens.stream()
            .filter(alvo -> alvo != atacante) // Não ataca a si mesmo
            .filter(alvo -> atacante.estaNoAlcance(alvo)) // Só ataca se estiver no alcance
            .forEach(alvo -> {
                alvo.sofrerDano(dano);
                System.out.println("[ATAQUE] " + atacante.getClass().getSimpleName() + 
                    " causou " + dano + " de dano em " + alvo.getClass().getSimpleName() +
                    " (distância: " + String.format("%.1f", atacante.calcularDistancia(alvo)) + 
                    "px, alcance: " + alcance + "px)");
            });
    }

    /**
     * Inicia o processo de remoção dos personagens mortos com efeito de fade-out.
     * Os personagens que estão morrendo terão sua opacidade reduzida gradualmente.
     */
    private void removerPersonagensMortos() {
        // Verifica se há personagens morrendo
        boolean temMorrendo = this.personagens.stream().anyMatch(Personagem::estaMorrendo);
        
        if (temMorrendo && (timerFadeOut == null || !timerFadeOut.isRunning())) {
            timerFadeOut = new Timer(INTERVALO_FADE, e -> {
                boolean continuarAnimacao = false;
                
                // Reduz a opacidade de todos os personagens morrendo
                for (Personagem p : personagens) {
                    if (p.estaMorrendo()) {
                        if (p.reduzirOpacidade(REDUCAO_OPACIDADE)) {
                            continuarAnimacao = true;
                        }
                    }
                }
                
                // Remove personagens que desapareceram completamente e atualiza contadores
                Iterator<Personagem> iterator = personagens.iterator();
                while (iterator.hasNext()) {
                    Personagem p = iterator.next();
                    if (p.desapareceuCompletamente()) {
                        contabilizarBaixa(p);
                        iterator.remove();
                        System.out.println("[BAIXA] " + p.getClass().getSimpleName() + " foi eliminado!");
                        imprimirPlacar();
                    }
                }
                
                repaint();
                
                // Para o timer quando não houver mais personagens morrendo
                if (!continuarAnimacao) {
                    ((Timer) e.getSource()).stop();
                }
            });
            timerFadeOut.start();
        }
    }

    /**
     * Contabiliza a baixa de um personagem no contador apropriado.
     *
     * @param personagem personagem que foi eliminado
     */
    private void contabilizarBaixa(Personagem personagem) {
        if (personagem instanceof Aldeao) {
            baixasAldeoes++;
        } else if (personagem instanceof Arqueiro) {
            baixasArqueiros++;
        } else if (personagem instanceof Cavaleiro) {
            baixasCavaleiros++;
        }
    }

    /**
     * Imprime o placar atual de baixas no terminal.
     */
    private void imprimirPlacar() {
        System.out.println("=== PLACAR DE BAIXAS ===");
        System.out.println("Aldeões: " + baixasAldeoes);
        System.out.println("Arqueiros: " + baixasArqueiros);
        System.out.println("Cavaleiros: " + baixasCavaleiros);
        System.out.println("Total: " + getTotalBaixas());
        System.out.println("========================");
    }

    /**
     * Retorna o total de baixas de aldeões.
     */
    public int getBaixasAldeoes() {
        return baixasAldeoes;
    }

    /**
     * Retorna o total de baixas de arqueiros.
     */
    public int getBaixasArqueiros() {
        return baixasArqueiros;
    }

    /**
     * Retorna o total de baixas de cavaleiros.
     */
    public int getBaixasCavaleiros() {
        return baixasCavaleiros;
    }

    /**
     * Retorna o total geral de baixas.
     */
    public int getTotalBaixas() {
        return baixasAldeoes + baixasArqueiros + baixasCavaleiros;
    }

    /**
     * Retorna a quantidade de personagens na tela.
     *
     * @return número total de personagens
     */
    public int getQuantidadePersonagens() {
        return this.personagens.size();
    }
}
