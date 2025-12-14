
# Projeto 2 - Jogo de Batalha

## 📋 Descrição

Jogo de batalha desenvolvido em Java com interface gráfica Swing, onde diferentes tipos de personagens podem ser criados, movimentados e atacar uns aos outros. O projeto demonstra conceitos de Programação Orientada a Objetos como herança, polimorfismo e encapsulamento.

## 🎮 Funcionalidades Implementadas

### 1. Sistema de Ataque Básico

O sistema de combate permite que personagens ataquem outros personagens em campo.

#### Atributos de Combate

Cada personagem possui atributos específicos de combate:

| Personagem | Vida | Ataque | Velocidade |
|------------|------|--------|------------|
| **Aldeão** | 100 | 0 | 10 |
| **Arqueiro** | 80 | 20 | 15 |
| **Cavaleiro (montado)** | 150 | 25 | 20 |
| **Cavaleiro (desmontado)** | 150 | 25 | 10 |

#### Funcionamento

- Ao clicar em **Atacar**, os personagens selecionados (Cavaleiro e Arqueiro) atacam todos os outros personagens em campo
- O **Aldeão não pode atacar** - o botão de ataque fica desabilitado quando ele está selecionado
- O dano é aplicado através do método `sofrerDano(int dano)` que atualiza o atributo `vida`
- A vida não pode ficar negativa (mínimo = 0)

#### Métodos Principais

```java
// Classe Personagem
public int getVida()              // Retorna vida atual
public int getAtaque()            // Retorna valor de ataque
public boolean estaVivo()         // Verifica se vida > 0
public void sofrerDano(int dano)  // Aplica dano ao personagem
```

---

### 2. Efeito Visual de Morte (Fade-Out)

Quando um personagem morre, ele não desaparece instantaneamente. Em vez disso, um efeito de desvanecimento gradual é aplicado.

#### Funcionamento

1. Quando a vida chega a 0, o personagem entra no estado `morrendo`
2. Um timer reduz a opacidade gradualmente (10% a cada 50ms)
3. O personagem é desenhado com transparência crescente usando `AlphaComposite`
4. Quando a opacidade chega a 0, o personagem é removido do campo

#### Atributos e Métodos

```java
protected float opacidade;        // Valor entre 0.0 e 1.0
protected boolean morrendo;       // Flag de estado de morte

public boolean estaMorrendo()             // Verifica se está morrendo
public float getOpacidade()               // Retorna opacidade atual
public boolean reduzirOpacidade(float r)  // Reduz opacidade gradualmente
public boolean desapareceuCompletamente() // Verifica se pode ser removido
```

---

### 3. Contador de Baixas

Sistema de contagem de personagens eliminados, com exibição na interface gráfica e no terminal.

#### Placar na Interface

Um painel "Baixas" foi adicionado à interface lateral, exibindo:
- Aldeões eliminados
- Arqueiros eliminados
- Cavaleiros eliminados
- Total de baixas

#### Mensagens no Terminal

Quando um personagem é eliminado, são exibidas mensagens no terminal:

```
[BAIXA] Aldeao foi eliminado!
=== PLACAR DE BAIXAS ===
Aldeões: 1
Arqueiros: 0
Cavaleiros: 0
Total: 1
========================
```

#### Métodos de Consulta

```java
// Classe Tela
public int getBaixasAldeoes()     // Total de aldeões eliminados
public int getBaixasArqueiros()   // Total de arqueiros eliminados
public int getBaixasCavaleiros()  // Total de cavaleiros eliminados
public int getTotalBaixas()       // Total geral de baixas
```

---

### 4. Alcance Variável de Ataque

Cada tipo de personagem possui um alcance de ataque diferente. O ataque só causa dano se o alvo estiver dentro do alcance.

#### Alcances por Tipo

| Personagem | Alcance | Cor da Aura |
|------------|---------|-------------|
| **Aldeão** | 50px | Marrom (terra) |
| **Arqueiro** | 150px | Verde |
| **Cavaleiro** | 75px | Azul |

#### Cálculo de Distância

A distância é calculada entre os **centros** dos personagens usando a fórmula euclidiana:

```java
public double calcularDistancia(Personagem outro) {
    int dx = this.getCentroX() - outro.getCentroX();
    int dy = this.getCentroY() - outro.getCentroY();
    return Math.sqrt(dx * dx + dy * dy);
}
```

#### Validação de Alcance

O ataque só causa dano se: `distância <= alcanceAtaque`

```java
public boolean estaNoAlcance(Personagem outro) {
    return calcularDistancia(outro) <= getAlcanceAtaque();
}
```

#### Indicação Visual (Aura) - Sempre Visível

A aura de alcance é exibida **permanentemente** para todos os personagens que podem atacar:
- O **raio** do círculo corresponde ao alcance de ataque
- A **cor** é específica para cada tipo de personagem
- O círculo possui preenchimento semi-transparente e borda mais visível
- **Aldeões não exibem aura** pois não podem atacar (ataque = 0)

| Personagem | Aura Visível |
|------------|--------------|
| **Aldeão** | ❌ Não |
| **Arqueiro** | ✅ Sempre (verde) |
| **Cavaleiro** | ✅ Sempre (azul) |

#### Mensagens de Ataque no Terminal

```
[ATAQUE] Arqueiro causou 20 de dano em Aldeao (distância: 87.5px, alcance: 150px)
```

---

### 5. Controle de Montaria (Cavaleiro)

O Cavaleiro possui a capacidade de alternar entre dois estados: **montado** e **desmontado**.

#### Estados do Cavaleiro

| Estado | Imagem Normal | Imagem Atacando | Velocidade |
|--------|---------------|-----------------|------------|
| **Montado** | `cavaleiro.png` | `cavaleiro2.png` | 20 |
| **Desmontado** | `guerreiro.png` | `guerreiro2.png` | 10 |

#### Funcionamento

- O Cavaleiro **começa montado** por padrão
- Ao clicar no botão **"Montar"**, todos os cavaleiros alternam seu estado
- Quando **montado**: é mais rápido e usa a sprite do cavaleiro
- Quando **desmontado**: é mais lento e usa a sprite do guerreiro
- A imagem muda corretamente ao atacar em ambos os estados

#### Botão Montar/Desmontar

O botão "Montar" só fica habilitado quando:
- Radio button **"Cavaleiro"** está selecionado
- Radio button **"Todos"** está selecionado

Quando desabilitado, exibe tooltip: *"Selecione Cavaleiro ou Todos"*

#### Métodos da Classe Cavaleiro

```java
public void alternarMontado()  // Alterna entre montado/desmontado
public boolean isMontado()     // Verifica se está montado

@Override
public int getVelocidade() {
    return montado ? VELOCIDADE_MONTADO : VELOCIDADE_DESMONTADO;
}

@Override
public String getNomeImagem() {
    return montado ? "cavaleiro" : "guerreiro";
}

@Override
public String getNomeImagemAtacando() {
    return montado ? "cavaleiro2" : "guerreiro2";
}
```

#### Método na Classe Tela

```java
public void alternarMontariaCavaleiros()  // Alterna montaria de todos os cavaleiros
```

---

### 6. Atalhos de Teclado

O jogo suporta controle completo via teclado, permitindo uma experiência de jogo mais fluida e rápida.

#### Tabela de Atalhos

| Tecla | Ação | Observação |
|-------|------|------------|
| **W** ou **↑** | Mover para cima | Move personagens do tipo selecionado |
| **S** ou **↓** | Mover para baixo | Move personagens do tipo selecionado |
| **A** ou **←** | Mover para esquerda | Move personagens do tipo selecionado |
| **D** ou **→** | Mover para direita | Move personagens do tipo selecionado |
| **1** | Criar Aldeão | Também funciona no numpad |
| **2** | Criar Arqueiro | Também funciona no numpad |
| **3** | Criar Cavaleiro | Também funciona no numpad |
| **Espaço** | Atacar | Não funciona se Aldeão estiver selecionado |
| **Tab** | Alternar filtro de tipo | Ciclo: Todos → Aldeão → Arqueiro → Cavaleiro |
| **M** | Montar/Desmontar | Funciona independente do tipo selecionado |
| **C** | Coletar Recursos | Comando para Aldeões coletarem recursos próximos |

#### Implementação Técnica

Os atalhos foram implementados usando `KeyEventDispatcher`, que intercepta eventos de teclado globalmente no `KeyboardFocusManager`. Esta abordagem foi escolhida porque:

1. **Funciona independente do foco**: Não importa qual componente está selecionado
2. **Intercepta antes dos componentes**: Evita conflitos com comportamentos padrão do Swing
3. **Controle total**: Permite consumir eventos para que não afetem outros componentes

```java
KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
    if (e.getID() != KeyEvent.KEY_PRESSED) {
        return false; // Ignora KEY_RELEASED e KEY_TYPED
    }

    switch (e.getKeyCode()) {
        case KeyEvent.VK_W:
        case KeyEvent.VK_UP:
            movimentarPorTipoSelecionado(Direcao.CIMA);
            return true; // Consome o evento
        // ... outros casos
    }
    return false; // Deixa passar teclas não mapeadas
});
```

#### Vantagens sobre KeyListener

| Aspecto | KeyListener | KeyEventDispatcher |
|---------|-------------|-------------------|
| Foco necessário | Sim | Não |
| Conflito com Tab | Sim | Não |
| Conflito com Espaço em botões | Sim | Não |
| Escopo | Componente | Global |

#### Desabilitação de Foco em Botões

Para evitar conflitos com o comportamento padrão dos botões (espaço ativa botão com foco), todos os botões de ação têm `setFocusable(false)`:

```java
private void desabilitarFocoBotoes() {
    atacarButton.setFocusable(false);
    montarButton.setFocusable(false);
    buttonCima.setFocusable(false);
    // ... outros botões
}
```

---

### 7. Sistema de Coleta de Recursos

O jogo possui um sistema de economia baseado na coleta de três tipos de recursos: **Comida**, **Ouro** e **Madeira**.

#### Funcionamento

- Apenas o **Aldeão** possui a capacidade de coletar recursos (implementa interface `Coletador`)
- Ao pressionar a tecla **C** ou clicar no botão **"Coletar"**, todos os aldeões buscam recursos próximos
- A quantidade coletada é somada ao estoque global do jogador exibido no painel lateral
- Recursos são representados por cores:
  - **Comida**: Rosa
  - **Ouro**: Amarelo/Dourado
  - **Madeira**: Marrom

#### Interfaces e Classes

```java
// Interface Coletador
public interface Coletador {
    void coletar(Recurso recurso);
}

// Em Aldeao.java
public class Aldeao extends Personagem implements Coletador { ... }
```

---

### 8. Sistema de Áudio

O projeto conta com efeitos sonoros para melhorar a imersão do jogo (implementado via `GerenciadorAudio`).

- **Sons Implementados**:
  - Ataque (espada/flecha)
  - Dano recebido
  - Morte de personagem
  - Coleta de recursos
  
---

## 🏗️ Arquitetura do Projeto

### Estrutura de Classes

ifsc.joe/
├── App.java                    # Classe principal
├── config/
│   └── Constantes.java         # Configurações globais
├── domain/
│   ├── Personagem.java         # Classe abstrata base
│   ├── Recurso.java            # Entidade de recurso
│   └── impl/
│       ├── Aldeao.java         # Implementação do Aldeão
│       ├── Arqueiro.java       # Implementação do Arqueiro
│       └── Cavaleiro.java      # Implementação do Cavaleiro
├── enums/
│   ├── Direcao.java            # Enum de direções
│   └── TipoRecurso.java        # Enum de tipos (OURO, COMIDA, MADEIRA)
├── interfaces/
│   ├── Atacante.java           # Interface para combate
│   └── Coletador.java          # Interface para coleta
├── ui/
│   ├── JanelaJogo.java         # JFrame principal
│   ├── PainelControles.java    # Painel de controles lateral
│   ├── PainelControles.form    # Layout do painel
│   └── Tela.java               # Área de jogo (JPanel)
└── utils/
    └── GerenciadorAudio.java   # Sistema de som
```

### Hierarquia de Personagens

```
Personagem (abstract)
    ├── Aldeao
    ├── Arqueiro
    └── Cavaleiro
```

### Atributos da Classe Personagem

| Atributo | Tipo | Descrição |
|----------|------|-----------|
| `posX` | int | Coordenada X |
| `posY` | int | Coordenada Y |
| `vida` | int | Pontos de vida |
| `ataque` | int | Poder de ataque |
| `atacando` | boolean | Estado de ataque |
| `morrendo` | boolean | Estado de morte |
| `opacidade` | float | Transparência (0.0 a 1.0) |
| `icone` | Image | Imagem do personagem |

---

## 🎯 Como Jogar

### Controles por Mouse

1. **Criar Personagens**: Clique nos botões com ícones (Aldeão, Arqueiro, Cavaleiro)

2. **Selecionar Tipo**: Use os radio buttons para selecionar qual tipo controlar

3. **Movimentar**: Clique nas setas direcionais

4. **Atacar**: Clique em "Atacar"

5. **Montar/Desmontar**: Clique em "Montar"

### Controles por Teclado (Recomendado) ⌨️

| Ação | Teclas |
|------|--------|
| Criar personagens | **1** (Aldeão), **2** (Arqueiro), **3** (Cavaleiro) |
| Mover | **WASD** ou **Setas** |
| Atacar | **Espaço** |
| Alternar tipo | **Tab** |
| Montar/Desmontar | **M** |
| Coletar | **C** |

### Dicas de Jogo

- A **aura de alcance** é sempre visível para personagens combatentes
- Apenas alvos **dentro do alcance** receberão dano
- O **Aldeão não pode atacar** (Espaço é ignorado quando selecionado)
- **M** funciona para montar/desmontar independente do tipo selecionado
- Personagens com vida = 0 **desaparecem gradualmente**
- O **placar de baixas** é atualizado automaticamente

---

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Swing** (Interface Gráfica)
- **Gradle** (Build Tool)
- **IntelliJ IDEA Form Designer** (Layout)

---

## ▶️ Como Executar

```bash
# Clonar o repositório
git clone <url-do-repositorio>

# Entrar no diretório
cd projeto-2-enzo-e-nicolas

# Executar com Gradle
./gradlew run
```

---

## 👥 Autores

- Enzo
- Nicolas

---

## 📚 Conceitos de POO Aplicados

- **Herança**: Classes `Aldeao`, `Arqueiro` e `Cavaleiro` herdam de `Personagem`
- **Polimorfismo**: Cada personagem implementa seus próprios valores para métodos como `getVelocidade()`, `getAtaque()`, `getAlcanceAtaque()`
- **Encapsulamento**: Atributos protegidos com getters/setters
- **Abstração**: Classe `Personagem` define o contrato comum para todos os personagens
