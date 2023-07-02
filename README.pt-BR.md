# Jogo da Forca (Em progresso)

O Jogo da Forca é um aplicativo móvel desenvolvido usando Jetpack Compose e Kotlin, proporcionando uma experiência móvel nativa. É uma recriação do clássico jogo da forca, onde os jogadores adivinham letras para descobrir uma palavra oculta. Este projeto serve como uma experiência de aprendizado pessoal, com o objetivo de construir um jogo do zero, incorporando as melhores práticas e seguindo os padrões de desenvolvimento atualizados.

Sim, é um jogo da forca, sem a forca. Disponível na Play Store logo logo.
## Estado atual do projeto:
![Captura de tela de 2023-07-01 20-42-06](https://github.com/machado001/hangman/assets/101916850/7604057c-d2ef-4d22-a3fa-4cfa7aff7613)

## O app

- Jogabilidade interativa: Adivinhe letras para revelar a palavra oculta.
- Palavras geradas aleatoriamente: Desfrute de uma variedade de palavras separadas por categoria derivadas de um banco de palavras pré-definido.
- Feedback visual: Veja o progresso da palavra e os palpites incorretos por meio de uma interface amigável.

## Pré-requisitos

Antes de executar o projeto do Jogo da Forca, certifique-se de ter o seguinte:

- Android Studio: É recomendado usar a versão mais recente do Android Studio para uma experiência de desenvolvimento ideal.
- Kotlin: Familiaridade com a linguagem citada.
- Jetpack Compose: Compreender os conceitos básicos do Jetpack Compose.

## Instalação

1. Clone o repositório:

   ```shell
   git clone https://github.com/machado001/hangman
   ```
2. Abra o projeto no Android Studio.

3. Compile e execute o aplicativo em seu emulador ou dispositivo físico.

## Estrutura do Projeto (Model-View-ViewModel)
O projeto segue uma estrutura bem organizada para garantir a manutenibilidade e escalabilidade. Aqui está uma visão geral dos principais diretórios e arquivos:

- `app/src/main`: Contém o código-fonte principal do aplicativo Jogo da Forca.
  - `java/com/example/hangmangame`: Contém o código-fonte Kotlin para o aplicativo.
    - `data`: Contém a camada de dados, lidando com os dados e lógica relacionados ao jogo (atualmente, conta apenas com a lista de palavras).
    - `ui`: Contém os componentes de interface do usuário e as telas construídas usando o Jetpack Compose, e a viewModel lidando com a lógica.
  - `res`: Contém os recursos do aplicativo, como layouts e strings.

## Documentação
Para entender a base de código do projeto e aprender mais sobre os detalhes de implementação, consulte os seguintes recursos:

- **Documentação do Jetpack Compose**: Explore a documentação oficial do Jetpack Compose para entender os conceitos fundamentais e as APIs usadas no projeto. Visite [compose.android.com](https://compose.android.com) para acessar a documentação.
- **Repositório de exemplos do Compose**: O repositório `compose-samples` fornece uma coleção de projetos de exemplo que demonstram diferentes aspectos do Jetpack Compose, sendo referencial valioso para aprimorar seu entendimento

. Encontre o repositório em [github.com/android/compose-samples](https://github.com/android/compose-samples).
- **Código-fonte do Android**: O repositório oficial de código-fonte do Android pode ser um recurso valioso para explorar o funcionamento interno da plataforma Android. Visite [cs.android.com](https://cs.android.com) para acessar o código-fonte.

## Contribuições
Fique a vontade.
