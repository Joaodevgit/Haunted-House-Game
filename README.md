20/01/2020 23:26 Autor: João Pereira Mensagem do Commit: "Criacao de menu principal e de escolha de dificuldade"

O que foi feito neste commit:

- Criação do menu principal na classe Main
- Criação da classe MenuImages ( que irá ser responsável pela TUI (Text-base user interface)
- Criação de um menu dentro do menu principal que irá ser responsável pela alteração a dificuldade do
  jogo e que consequentemente irá retornar uma vguardar a variável (responsável por definir ,através da
  multiplicação o nº de pontos que um fantasma pode retirar ao jogador)

O que fazer para o próximo commit:

- Perceber que estruturas de dados aplicar em cada situação mencionada no enunciado
- Criar a classe responsável pela informação de cada ponto do grafo
- Resolver o método do caminho mais curto através do peso
- Ver como vamos guardar a variável responsável por alterar a dificuldade do jogo

Decisões da implementação/projeto a serem tomadas:

- A dificuldade do jogo (por defeito) é básico e depois pode ser alterada OU a dificuldade é escolhida
  aquando da iniciação do programa e depois pode ser alterada (Eu escolho a 1º opção)

--------------------------------------------------------------------------------------------------------

21/01/2020 13:36 Autor: Francisco Spínola Mensagem do Commit: "JSON Parser"

O que foi feito neste commit:

- Alteração da classe Main
- Alteração da estrutura do TUI
- Alteração de MenuImagens para TUI
- Criação do parser de JSON
- Criação da classe Map que extende UndirectedGraph e não tem funcionalidades implementadas ainda
- Criação da classe Room para implementar como aposento com as variáveis nome e ghost

--------------------------------------------------------------------------------------------------------

21/01/2020 19:00 Autor: João Pereira Mensagem do Commit:"Modificacoes na classe TUI"

O que foi feito neste commit:

Classe TUI:
- A dificuldade irá ser escolhida de inicio e futuramente poderá ser alterada na opção "4" do menu
principal (Adição do ecrã responsável por mostrar ao utilizador qual a dificuldade que deseja escolher)
- Adição do método getPlayerName() que irá ser responsável por retornar o nome do jogador
- Adição no ecrã menu principal o nome do jogador e a dificuldade atual escolhida
- Criação dos métodos getDifficulty(int difficultyNumber), mudança do nome do método getDifficultyChoice()
para getInitialDifficultyChoice()

O que fazer para o próximo commit:

- Perceber que estruturas de dados aplicar em cada situação mencionada no enunciado
- Resolver o método do caminho mais curto através do peso
- Ver como vamos guardar a variável responsável por alterar a dificuldade do jogo

Decisões da implementação/projeto a serem tomadas:
- Nada a assinalar

--------------------------------------------------------------------------------------------------------

22/01/2020 12:22 Francisco Spínola, Commit: "JSONParser completo"

O que foi feito neste commit:

- Parser de json para java já funciona
- Mudança de implementação de Grafo não direcionado para Rede direcionada
- Rede implementada com sucesso

O que fazer para o próximo commit:

- Modificar a classe Graph (quando fazem a verificaçao if (vertices[i] == 1) tem que ser if (vertices[i] != Double.POSITIVE_INFINITY)). Esta é a razao dos iteradores não funcionarem...
- Acabar classe Network

--------------------------------------------------------------------------------------------------------

22/01/2020 15:30 Francisco Spínola, Commit: "Atualização da API"

O que foi feito neste commit: 

- A classe network foi atualizada: foi feito override dos métodos iteratorBFS, iteratorDFS e addVertex, da classe Graph.
- A biblioteca da API foi reposta por esta nova atualizada

O que falta fazer:

- Acabar classe Network (métodos iteratorShortestPathWeight e shortestPathWeight)
- Guardar/Carregar informações das classificações dos jogadores

--------------------------------------------------------------------------------------------------------

25/01/2020 12:00 Francisco Spínola, Commit: "Atualização"

O que foi feito neste commit:

- Foi criada a pasta lib que contém os requisitos para a execução do programa.
- A biblioteca da API foi atualizada (método removeVertex em Network estava em falta e foram removidos os métodos toString() das coleções).
- Foi criada a interface gráfica do jogo (Classe GUI).
- Foram adicionados recursos (imagens, sons,...).
- Ficheiros implementador por Francisco Spínola foram documentados.

O que falta fazer:

- Guardar e carregar classificações de jogadores em ficheiro.
- Gerar visualmente uma tabela de classificações.
- Criar modo normal e modo simualação do jogo.
- Acabar classe Network (2 métodos).
- Gerar 2 variáveis: a primeira guarda os pontos de vida que o jogador tem e a outra guarda a posição atual do jogador (em que aposento se encontra).
- Documentar o resto do código.

--------------------------------------------------------------------------------------------------------

