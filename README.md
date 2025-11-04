# RPG Pokémon - Resgate do Amigo

## Descrição do Jogo

Um RPG em Java baseado em texto onde você escolhe ser um Humano (Guerreiro ou Arqueiro) ou um Pokémon, e parte em uma jornada para resgatar seu amigo que foi capturado pela Equipe Rocket.

## História

Seu melhor amigo (um Pokémon) foi capturado pela Equipe Rocket! Você precisa:
- Explorar o mundo
- Enfrentar membros da Equipe Rocket
- Lutar contra Pokémon selvagens
- Encontrar pistas sobre o paradeiro do seu amigo
- Derrotar o líder Giovanni em uma batalha final
- Resgatar seu amigo!

## Características do Jogo

### Sistema de Personagens
- **Humanos**: Guerreiro (mais defesa e HP) ou Arqueiro (mais ataque)
- **Pokémon jogáveis**: 18 Pokémon disponíveis em 6 tipos diferentes
  - Água: Squirtle, Vaporeon, Gyarados
  - Fogo: Charmander, Flareon, Arcanine
  - Planta: Bulbasaur, Leafeon, Venusaur
  - Pedra: Onix, Geodude, Rhydon
  - Elétrico: Pikachu, Jolteon, Electabuzz
  - Psíquico: Abra, Espeon, Alakazam

### Sistema de Combate
- Baseado em **rolagem de dados** (1d6)
- Ataque total = Ataque base + dado
- Dano = (Ataque total - Defesa) × Multiplicador de tipo
- **Vantagens e fraquezas** entre tipos:
  - Água > Fogo
  - Fogo > Planta
  - Planta > Água
  - Elétrico > Água
  - Psíquico > Pedra
  - Pedra > Fogo
  - Humanos têm vantagem contra Pedra e Planta
  - Humanos são fracos contra Fogo, Elétrico e Psíquico

### Sistema de Inventário
- Itens com **quantidades** (múltiplas unidades do mesmo item)
- Poções de cura
- Sistema de **saque**: ao derrotar inimigos, você recebe parte de seus itens
- Itens são ordenados por nome (usando `compareTo`)

### Exploração e Eventos Aleatórios
- **Encontros com Equipe Rocket** (40%): Membros humanos da organização criminosa
- **Pokémon Selvagens** (30%): Encontros com Pokémon de tipos variados
- **Pistas** (15%): Informações sobre seu amigo capturado
- **Itens** (10%): Encontrar poções e outros itens
- **Armadilhas** (5%): Perder vida em armadilhas

### Progressão
- Encontre **5 pistas** para desbloquear a batalha final
- Derrote **Giovanni**, o líder da Equipe Rocket
- Resgate seu amigo!

## Como Compilar e Executar

### Requisitos
- Java JDK 8 ou superior

### Compilação

No terminal (PowerShell/CMD), navegue até a pasta `src`:

```powershell
cd src
```

Compile todos os arquivos:

```powershell
javac rpg\*.java rpg\personagem\*.java rpg\personagem\pokemon\*.java rpg\poderes\*.java rpg\item\*.java rpg\inventario\*.java rpg\origem\*.java rpg\util\*.java
```

### Execução

Execute o jogo:

```powershell
java rpg.Main
```

## Como Jogar

### Menu Inicial
1. Escolha seu personagem (Humano ou Pokémon)
2. Personalize o nome se desejar
3. Seu amigo (um Pokémon aleatório) será escolhido

### Durante o Jogo
As opções disponíveis são:

1. **Explorar**: Procurar por eventos (encontros, itens, pistas)
2. **Ver Status**: Verificar vida, atributos, inventário e poderes
3. **Usar Item**: Consumir poções para recuperar vida
4. **Sair**: Encerrar o jogo

### Durante Combates
- **Atacar**: Realizar um ataque com rolagem de dado
- **Fugir**: Tentar escapar (50% de chance)

### Dicas
- Use poções entre batalhas para manter a vida alta
- Pokémon têm poderes especiais baseados em seus tipos
- Preste atenção às vantagens de tipo no combate
- Ao derrotar inimigos, você recebe itens deles

## Estrutura do Código

```
src/
└── rpg/
    ├── Main.java              # Ponto de entrada, menu de seleção
    ├── Jogo.java              # Loop principal, exploração e combate
    ├── personagem/
    │   ├── Personagem.java    # Classe abstrata base
    │   ├── Guerreiro.java     # Humano com foco em defesa
    │   ├── Arqueiro.java      # Humano com foco em ataque
    │   ├── Inimigo.java       # NPCs hostis
    │   └── pokemon/
    │       ├── PokemonAgua.java
    │       ├── PokemonFogo.java
    │       ├── PokemonPlanta.java
    │       ├── PokemonPedra.java
    │       ├── PokemonEletrico.java
    │       ├── PokemonPsiquico.java
    │       └── PokemonPresets.java  # Pokémon pré-configurados
    ├── inventario/
    │   └── Inventario.java    # Gerenciamento de itens
    ├── item/
    │   └── Item.java          # Classe de itens com quantidades
    ├── poderes/
    │   ├── Poder.java         # Poderes/habilidades especiais
    │   └── PoderPresets.java  # Poderes pré-configurados por tipo
    ├── origem/
    │   ├── Origem.java        # Interface de origem
    │   ├── OrigemHumano.java  # Origem humana
    │   └── OrigemPokemon.java # Origem Pokémon
    └── util/
        └── TipoUtil.java      # Cálculo de vantagens/fraquezas
```

## Conceitos de Orientação a Objetos Utilizados

- **Herança**: Personagem → Guerreiro/Arqueiro/Inimigo/Pokémons
- **Polimorfismo**: Método `atacar()` com diferentes implementações
- **Encapsulamento**: Atributos protected/private com getters
- **Interfaces**: `Origem`, `Comparable`, `Cloneable`
- **Classes abstratas**: `Personagem`
- **Construtores**: Padrão, parametrizados e de cópia
- **Clonagem**: Inventário e itens com clone profundo
- **Comparação**: `equals()`, `compareTo()` para ordenação

## Autores

Projeto desenvolvido como trabalho acadêmico de Programação Orientada a Objetos.

---

**Boa sorte na jornada para resgatar seu amigo!** 🎮✨
