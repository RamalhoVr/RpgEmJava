# 🎮 Como Executar o RPG Pokémon

## ❌ Problema: "javac não é reconhecido"

Esse erro significa que o Java JDK não está instalado ou não está no PATH do Windows.

## ✅ Soluções

### **Opção 1: Executar direto pelo VS Code (MAIS FÁCIL)**

Se você já tem as extensões Java instaladas, pode executar diretamente pelo VS Code:

1. **Abra o arquivo** `src/rpg/Main.java`
2. **Clique com o botão direito** dentro do arquivo
3. **Selecione** "Run Java" ou pressione `F5`
4. O jogo vai iniciar no terminal integrado do VS Code!


### **Opção 2: Usar o Terminal Integrado do VS Code**

1. No VS Code, abra o terminal integrado: `Ctrl + '` (aspas)
2. O VS Code geralmente detecta o Java automaticamente
3. Execute:
   ```powershell
   cd src
   javac rpg\*.java rpg\personagem\*.java rpg\personagem\pokemon\*.java rpg\poderes\*.java rpg\item\*.java rpg\inventario\*.java rpg\origem\*.java rpg\util\*.java
   java rpg.Main
   ```

## 🚀 Método Mais Rápido (Recomendado)

**Use o botão "Run" do VS Code:**

1. Abra `src/rpg/Main.java`
2. Procure o ícone de **play** (▶️) ao lado da linha `public class Main`
3. Clique no ícone **▶️ Run** ou **🐞 Debug**
4. O jogo inicia automaticamente!

## 🎯 Verificar se o Java está instalado

Para verificar se o Java está instalado corretamente:

```powershell
java -version
javac -version
```

Se aparecer a versão (ex: `java version "17.0.8"`), está tudo certo!

## 📝 Notas Importantes

- **VS Code já faz a compilação automaticamente** se você tiver as extensões Java
- **Não é necessário compilar manualmente** se usar o botão "Run"
- O VS Code cria uma pasta `bin/` automaticamente com os arquivos compilados

## ❓ Ainda com problemas?

Se ainda assim não funcionar:

1. Verifique se as extensões Java estão ativadas:
   - `Ctrl + Shift + X`
   - Procure por "Java"
   - Certifique-se de que estão instaladas:
     - Language Support for Java (Red Hat)
     - Debugger for Java
     - Test Runner for Java

2. Reinicie o VS Code completamente

3. Tente executar pelo menu:
   - Terminal → Run Task → Selecione a tarefa Java

---

**Boa sorte e divirta-se jogando! 🎮✨**
