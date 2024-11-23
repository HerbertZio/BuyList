# **BuyList**

**BuyList** é um aplicativo Android que permite aos usuários criar, visualizar, editar e excluir listas de compras ou tarefas. Ele é uma solução prática e offline, ideal para quem busca simplicidade e funcionalidade no gerenciamento de listas.

---

## **Funcionalidades**
- 📋 **Criar listas de compras ou tarefas** com um título e itens associados.
- 📝 **Editar listas existentes**, adicionando ou removendo itens.
- ❌ **Excluir listas** que não são mais necessárias.
- 🔍 **Visualizar todas as listas salvas** na tela principal.
- 💾 **Armazenamento local** usando o banco de dados Room, garantindo acesso offline.

---

## **Tecnologias Utilizadas**
- **Linguagem:** Kotlin
- **IDE:** Android Studio
- **Banco de Dados:** Room (persistência local)
- **Compatibilidade:** Dispositivos Android com SDK mínimo 21 (Lollipop).

---

## **Como Usar**
1. **Tela Principal:** Visualize todas as listas salvas. Clique em uma lista para editá-la ou excluí-la.
2. **Criar Nova Lista:** Pressione o botão "Nova Lista" para adicionar uma nova lista com itens.
3. **Editar Lista:** Adicione ou remova itens em uma lista existente e salve as alterações.
4. **Excluir Lista:** Exclua listas diretamente da tela de visualização.

---

## **Estrutura do Projeto**
- **MainActivity:** Tela inicial com opções para criar novas listas ou visualizar listas salvas.
- **CriarListaActivity:** Tela para criar uma nova lista com itens.
- **ListasSalvasActivity:** Tela que exibe todas as listas salvas.
- **DetalhesListaActivity:** Tela para editar ou excluir uma lista salva.
- **Banco de Dados (Room):** Configurado com a entidade `Lista` e o DAO `ListaDao` para operações CRUD.

---

## **Requisitos do Sistema**
- **Dispositivo Android:** SDK mínimo 21 (Lollipop).
- **Espaço de Armazenamento:** ~10 MB para instalação inicial.

---


## Licença
Este código é **proprietário**. Todos os direitos são reservados ao autor. Nenhuma parte deste código pode ser usada, modificada, distribuída ou copiada sem a permissão explícita do autor.


---

## **Contato**
- **Desenvolvedor:** Herbert Zio
- **GitHub:** [Herbert Zio](https://github.com/HerbertZio)

---