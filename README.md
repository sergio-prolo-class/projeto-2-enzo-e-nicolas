[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/XD-ACkNn)

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

## 🏗️ Arquitetura do Projeto

### Estrutura de Classes

```
ifsc.joe/
├── App.java                    # Classe principal
├── domain/
│   ├── Personagem.java         # Classe abstrata base
│   └── impl/
│       ├── Aldeao.java         # Implementação do Aldeão
│       ├── Arqueiro.java       # Implementação do Arqueiro
│       └── Cavaleiro.java      # Implementação do Cavaleiro
├── enums/
│   └── Direcao.java            # Enum de direções (CIMA, BAIXO, etc.)
└── ui/
    ├── JanelaJogo.java         # JFrame principal
    ├── PainelControles.java    # Painel de controles lateral
    ├── PainelControles.form    # Layout do painel (IntelliJ Form)
    └── Tela.java               # Área de jogo (JPanel)
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

1. **Criar Personagens**: Clique nos botões com ícones (Aldeão, Arqueiro, Cavaleiro) para criar personagens em posições aleatórias

2. **Selecionar Tipo**: Use os radio buttons para selecionar qual tipo de personagem controlar:
   - Todos
   - Aldeão
   - Arqueiro
   - Cavaleiro

3. **Movimentar**: Use as setas direcionais para mover os personagens selecionados

4. **Atacar**: Clique em "Atacar" para que os personagens selecionados ataquem
   - A aura de alcance é sempre visível para personagens combatentes
   - Apenas alvos dentro do alcance receberão dano

5. **Montar/Desmontar**: Com "Cavaleiro" ou "Todos" selecionado, clique em "Montar" para:
   - Alternar cavaleiros entre montado (rápido) e desmontado (lento)
   - A aparência muda: cavaleiro ↔ guerreiro

6. **Observar**: 
   - Personagens com vida = 0 desaparecem gradualmente
   - O placar de baixas é atualizado automaticamente
   - As auras de alcance ajudam a visualizar o campo de batalha

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
