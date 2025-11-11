package rpg.mapa;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Mapa implements Cloneable {
    private Map<String, Local> locais;
    private String localAtual;

    // Construtor padrão
    public Mapa() {
        this.locais = new HashMap<>();
        this.localAtual = "entrada";
        inicializarMapa();
    }

    // Construtor de cópia
    public Mapa(Mapa outro) {
        this.locais = new HashMap<>();
        for (Map.Entry<String, Local> entry : outro.locais.entrySet()) {
            this.locais.put(entry.getKey(), entry.getValue().clone());
        }
        this.localAtual = outro.localAtual;
    }

    private void inicializarMapa() {
        // ===== Entrada da Floresta =====
        Local entrada = new Local("Entrada da Floresta", 
            "O ponto de partida da sua jornada. Árvores altas cercam uma trilha estreita.");
        entrada.adicionarEvento(new Evento("dialogo", 
            "Um viajante experiente",
            "Você encontra um viajante que adverte sobre os perigos à frente e oferece conselhos sobre pokémon do tipo planta."));
        entrada.adicionarEvento(new Evento("combate", 
            "Pequeno Pokémon planta selvagem",
            "Um pequeno pokémon planta curioso aparece para testar suas habilidades!"));
        entrada.adicionarEvento(new Evento("item", 
            "Poção abandonada",
            "Você encontra uma poção deixada por outro aventureiro."));
        
        // ===== Floresta Sombria =====
        Local floresta = new Local("Floresta Sombria", 
            "Uma floresta densa onde seu amigo foi visto pela última vez. Sons estranhos ecoam.");
        floresta.adicionarEvento(new Evento("pista", 
            "Pegadas do seu amigo",
            "Você encontra pegadas recentes! Seu amigo passou por aqui e seguiu em direção às cavernas."));
        floresta.adicionarEvento(new Evento("combate", 
            "Membro da Team Rocket",
            "Um membro da Team Rocket aparece para emboscá-lo! 'Você não vai salvar seu amigo!'"));
        floresta.adicionarEvento(new Evento("combate", 
            "Pokémon planta territorial",
            "Um pokémon planta feroz protege seu território e ataca!"));
        floresta.adicionarEvento(new Evento("item", 
            "Ervas raras",
            "Você coleta ervas medicinais que crescem apenas nesta floresta."));
        
        // ===== Caverna Misteriosa =====
        Local caverna = new Local("Caverna Misteriosa", 
            "Uma caverna escura com cristais brilhantes nas paredes. Algo se move nas sombras.");
        caverna.adicionarEvento(new Evento("dialogo", 
            "Cristais restauradores",
            "Os cristais emitem uma energia curativa. Você se sente restaurado! (+20 HP)"));
        caverna.adicionarEvento(new Evento("combate", 
            "Pokémon pedra guardião",
            "Um poderoso pokémon do tipo pedra emerge das paredes da caverna!"));
        caverna.adicionarEvento(new Evento("armadilha", 
            "Queda de pedras",
            "Pedras caem do teto! Você consegue desviar mas sofre alguns arranhões."));
        caverna.adicionarEvento(new Evento("item", 
            "Cristal misterioso",
            "Você encontra um cristal especial que brilha intensamente."));
        
        // ===== Lago Cristalino =====
        Local lago = new Local("Lago Cristalino", 
            "Um lago calmo que reflete perfeitamente a lua. Há algo brilhando no fundo.");
        lago.adicionarEvento(new Evento("combate", 
            "Pokémon água do lago",
            "Um pokémon de água salta do lago e desafia você para uma batalha!"));
        lago.adicionarEvento(new Evento("item", 
            "Tesouro submerso",
            "Você mergulha e encontra um baú com itens valiosos no fundo do lago!"));
        lago.adicionarEvento(new Evento("pista", 
            "Reflexo misterioso",
            "O reflexo da lua revela uma mensagem oculta: 'A Team Rocket está na torre'!"));
        lago.adicionarEvento(new Evento("dialogo", 
            "Pokémon gentil",
            "Um pokémon de água amigável compartilha peixes curativos com você."));
        
        // ===== Ruínas Antigas =====
        Local ruinas = new Local("Ruínas Antigas", 
            "Estruturas de pedra cobertas por musgo. Símbolos estranhos brilham fracamente.");
        ruinas.adicionarEvento(new Evento("pista", 
            "Inscrições antigas",
            "Você decifra inscrições que revelam: 'O líder da Team Rocket está na fortaleza ao norte'!"));
        ruinas.adicionarEvento(new Evento("combate", 
            "Pokémon psíquico guardião",
            "Um antigo pokémon psíquico desperta para proteger as ruínas!"));
        ruinas.adicionarEvento(new Evento("item", 
            "Tesouro antigo",
            "Você encontra relíquias valiosas guardadas nas ruínas."));
        ruinas.adicionarEvento(new Evento("armadilha", 
            "Armadilha de pressão",
            "Você pisa em uma placa de pressão e flechas são disparadas!"));
        
        // ===== Pântano Venenoso =====
        Local pantano = new Local("Pântano Venenoso", 
            "Um pântano fedorento com águas turvas. Criaturas perigosas habitam aqui.");
        pantano.adicionarEvento(new Evento("combate", 
            "Pokémon água venenoso",
            "Um pokémon de água/planta venenoso emerge das águas pútridas!"));
        pantano.adicionarEvento(new Evento("armadilha", 
            "Lama traiçoeira",
            "Você afunda na lama! Consegue sair mas perde energia."));
        pantano.adicionarEvento(new Evento("combate", 
            "Membro da Team Rocket perdido",
            "Um agente da Team Rocket perdido no pântano ataca em desespero!"));
        pantano.adicionarEvento(new Evento("item", 
            "Plantas medicinais raras",
            "Apesar do ambiente hostil, você encontra plantas curativas raras."));
        
        // ===== Campo de Flores =====
        Local campo = new Local("Campo de Flores", 
            "Um campo colorido cheio de flores medicinais. O ar é puro e refrescante.");
        campo.adicionarEvento(new Evento("item", 
            "Ervas medicinais",
            "Você coleta diversas ervas curativas do campo florido."));
        campo.adicionarEvento(new Evento("dialogo", 
            "Pokémon planta amigável",
            "Um pokémon do tipo planta compartilha néctar restaurador com você."));
        campo.adicionarEvento(new Evento("descanso", 
            "Local de descanso",
            "Você descansa entre as flores. A paz do local restaura sua energia. (+30 HP)"));
        campo.adicionarEvento(new Evento("combate", 
            "Pokémon planta protetor",
            "Um pokémon planta forte protege o campo e testa sua força!"));
        
        // ===== Torre Abandonada =====
        Local torre = new Local("Torre Abandonada", 
            "Uma torre alta e antiga. Do topo, você pode ver toda a região.");
        torre.adicionarEvento(new Evento("pista", 
            "Visão do topo",
            "Do topo da torre você avista a fortaleza da Team Rocket! Seu amigo está lá!"));
        torre.adicionarEvento(new Evento("combate", 
            "Pokémon elétrico da torre",
            "Um pokémon elétrico que vive no topo da torre te desafia!"));
        torre.adicionarEvento(new Evento("item", 
            "Livro antigo",
            "Você encontra um livro com informações sobre pokémons lendários."));
        torre.adicionarEvento(new Evento("combate", 
            "Pokémon psíquico levitando",
            "Um pokémon psíquico paira no ar, bloqueando sua passagem!"));
        
        // ===== Ponte de Pedra =====
        Local ponte = new Local("Ponte de Pedra", 
            "Uma ponte antiga sobre um abismo profundo. Ventos fortes fazem ela balançar.");
        ponte.adicionarEvento(new Evento("armadilha", 
            "Ventania forte",
            "Uma rajada de vento quase te derruba da ponte! Você se segura mas se machuca."));
        ponte.adicionarEvento(new Evento("combate", 
            "Agente da Team Rocket bloqueando",
            "Um agente da Team Rocket bloqueia a ponte: 'Você não vai passar!'"));
        ponte.adicionarEvento(new Evento("item", 
            "Item escondido",
            "Você encontra um item precioso escondido sob a ponte."));
        ponte.adicionarEvento(new Evento("dialogo", 
            "Viajante assustado",
            "Um viajante assustado avisa sobre a presença do líder da Team Rocket na fortaleza."));
        
        // ===== Fortaleza do Chefe =====
        Local fortaleza = new Local("Fortaleza do Chefe", 
            "Uma fortaleza sombria onde seu amigo está sendo mantido prisioneiro.");
        fortaleza.adicionarEvento(new Evento("chefe", 
            "Giovanni - Líder da Team Rocket",
            "Giovanni, o líder da Team Rocket, aparece! 'Você nunca salvará seu amigo! Prepare-se para a derrota!'"));

        // Estabelecer conexões
        entrada.adicionarConexao("floresta");
        entrada.adicionarConexao("lago");
        
        floresta.adicionarConexao("entrada");
        floresta.adicionarConexao("caverna");
        floresta.adicionarConexao("ruinas");
        
        caverna.adicionarConexao("floresta");
        caverna.adicionarConexao("pantano");
        
        lago.adicionarConexao("entrada");
        lago.adicionarConexao("campo");
        lago.adicionarConexao("ponte");
        
        ruinas.adicionarConexao("floresta");
        ruinas.adicionarConexao("torre");
        
        pantano.adicionarConexao("caverna");
        pantano.adicionarConexao("torre");
        pantano.adicionarConexao("fortaleza");
        
        campo.adicionarConexao("lago");
        campo.adicionarConexao("ponte");
        
        torre.adicionarConexao("ruinas");
        torre.adicionarConexao("pantano");
        torre.adicionarConexao("fortaleza");
        
        ponte.adicionarConexao("lago");
        ponte.adicionarConexao("campo");
        ponte.adicionarConexao("fortaleza");
        
        // Fortaleza conecta com vários locais (acesso final)
        fortaleza.adicionarConexao("pantano");
        fortaleza.adicionarConexao("torre");
        fortaleza.adicionarConexao("ponte");

        // Adicionar ao mapa
        locais.put("entrada", entrada);
        locais.put("floresta", floresta);
        locais.put("caverna", caverna);
        locais.put("lago", lago);
        locais.put("ruinas", ruinas);
        locais.put("pantano", pantano);
        locais.put("campo", campo);
        locais.put("torre", torre);
        locais.put("ponte", ponte);
        locais.put("fortaleza", fortaleza);
    }

    // Getters
    public Local getLocalAtual() {
        return locais.get(localAtual);
    }

    public String getNomeLocalAtual() {
        return localAtual;
    }

    public Local getLocal(String nome) {
        return locais.get(nome.toLowerCase());
    }

    // Movimento
    public boolean moverPara(String nomeLocal) {
        nomeLocal = nomeLocal.toLowerCase();
        Local atual = locais.get(localAtual);
        
        if (atual.getConexoes().contains(nomeLocal) && locais.containsKey(nomeLocal)) {
            localAtual = nomeLocal;
            Local novoLocal = locais.get(nomeLocal);
            novoLocal.setVisitado(true);
            return true;
        }
        return false;
    }

    // Listar locais conectados ao atual
    public List<Local> getLocaisConectados() {
        Local atual = locais.get(localAtual);
        List<Local> conectados = new ArrayList<>();
        
        for (String nome : atual.getConexoes()) {
            Local local = locais.get(nome);
            if (local != null) {
                conectados.add(local);
            }
        }
        return conectados;
    }

    // Verificar se pode acessar a fortaleza
    public boolean podeAcessarChefe() {
        int pistasEncontradas = 0;
        for (Local local : locais.values()) {
            for (Evento evento : local.getEventos()) {
                if (evento.getTipo().equals("pista") && evento.jaOcorreu()) {
                    pistasEncontradas++;
                }
            }
        }
        return pistasEncontradas >= 3; // Precisa de 3 pistas
    }

    // Mostrar mapa
    public void mostrarMapa() {
        System.out.println("\n===== MAPA DA REGIÃO =====");
        System.out.println(">> Local atual: " + getLocalAtual().getNome());
        System.out.println("\nTodos os locais:");
        
        for (Local local : locais.values()) {
            String marcador = local.getNome().equals(getLocalAtual().getNome()) ? ">> " : "   ";
            System.out.println(marcador + local.toString());
        }
        
        System.out.println("\nLocais conectados (onde você pode ir):");
        List<Local> conectados = getLocaisConectados();
        for (int i = 0; i < conectados.size(); i++) {
            System.out.println((i + 1) + ". " + conectados.get(i).toString());
        }
        
        if (podeAcessarChefe()) {
            System.out.println("\n[DESBLOQUEADO] A Fortaleza do Chefe foi desbloqueada! Você pode enfrentar o chefe final!");
        } else {
            int pistasEncontradas = 0;
            for (Local local : locais.values()) {
                for (Evento evento : local.getEventos()) {
                    if (evento.getTipo().equals("pista") && evento.jaOcorreu()) {
                        pistasEncontradas++;
                    }
                }
            }
            System.out.println("\n[PISTAS] Pistas encontradas: " + pistasEncontradas + "/3 (precisa de 3 para acessar o chefe)");
        }
    }

    @Override
    public Mapa clone() {
        return new Mapa(this);
    }

    @Override
    public String toString() {
        return "Mapa com " + locais.size() + " locais. Local atual: " + getLocalAtual().getNome();
    }
}