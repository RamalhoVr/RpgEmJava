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
        // Entrada da Floresta
        Local entrada = new Local("Entrada da Floresta", 
            "O ponto de partida da sua jornada. Árvores altas cercam uma trilha estreita.", "vazio");
        
        // Floresta Sombria
        Local floresta = new Local("Floresta Sombria", 
            "Uma floresta densa onde seu amigo foi visto pela última vez. Sons estranhos ecoam.", "pista");
        
        // Caverna Misteriosa
        Local caverna = new Local("Caverna Misteriosa", 
            "Uma caverna escura com cristais brilhantes nas paredes. Algo se move nas sombras.", "combate");
        
        // Lago Cristalino
        Local lago = new Local("Lago Cristalino", 
            "Um lago calmo que reflete perfeitamente a lua. Há algo brilhando no fundo.", "item");
        
        // Ruínas Antigas
        Local ruinas = new Local("Ruínas Antigas", 
            "Estruturas de pedra cobertas por musgo. Símbolos estranhos brilham fracamente.", "pista");
        
        // Pântano Venenoso
        Local pantano = new Local("Pântano Venenoso", 
            "Um pântano fedorento com águas turvas. Criaturas perigosas habitam aqui.", "combate");
        
        // Campo de Flores
        Local campo = new Local("Campo de Flores", 
            "Um campo colorido cheio de flores medicinais. O ar é puro e refrescante.", "item");
        
        // Torre Abandonada
        Local torre = new Local("Torre Abandonada", 
            "Uma torre alta e antiga. Do topo, você pode ver toda a região.", "pista");
        
        // Ponte de Pedra
        Local ponte = new Local("Ponte de Pedra", 
            "Uma ponte antiga sobre um abismo profundo. Ventos fortes fazem ela balançar.", "armadilha");
        
        // Fortaleza do Chefe
        Local fortaleza = new Local("Fortaleza do Chefe", 
            "Uma fortaleza sombria onde seu amigo está sendo mantido prisioneiro.", "chefe");

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
            if (local.getTipoEvento().equals("pista") && !local.temPista()) {
                pistasEncontradas++;
            }
        }
        return pistasEncontradas >= 3; // Precisa de 3 pistas
    }

    // Mostrar mapa
    public void mostrarMapa() {
        System.out.println("\n🗺️ ===== MAPA DA REGIÃO =====");
        System.out.println("📍 Local atual: " + getLocalAtual().getNome());
        System.out.println("\n📋 Todos os locais:");
        
        for (Local local : locais.values()) {
            String marcador = local.getNome().equals(getLocalAtual().getNome()) ? "👉 " : "   ";
            System.out.println(marcador + local.toString());
        }
        
        System.out.println("\n🚪 Locais conectados (onde você pode ir):");
        List<Local> conectados = getLocaisConectados();
        for (int i = 0; i < conectados.size(); i++) {
            System.out.println((i + 1) + ". " + conectados.get(i).toString());
        }
        
        if (podeAcessarChefe()) {
            System.out.println("\n🏰 A Fortaleza do Chefe foi desbloqueada! Você pode enfrentar o chefe final!");
        } else {
            int pistasEncontradas = 0;
            for (Local local : locais.values()) {
                if (local.getTipoEvento().equals("pista") && !local.temPista()) {
                    pistasEncontradas++;
                }
            }
            System.out.println("\n🔍 Pistas encontradas: " + pistasEncontradas + "/3 (precisa de 3 para acessar o chefe)");
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