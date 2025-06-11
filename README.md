# Librear

---

## Plataforma de Treinamento de Professores para o Ensino de Alunos Surdocegos

Nosso projeto é uma plataforma de ensino focada em capacitar professores para garantir a acessibilidade de alunos surdocegos ao sistema educacional.

A plataforma oferece cursos e materiais didáticos específicos para que os professores possam se especializar no ensino de alunos surdocegos.

---

### Tecnologias Utilizadas:

- **Frontend**: Vue.js
- **Backend**: Laravel
- **Mobile**: Kotlin (Android)

---

### Acesso e Execução

A aplicação web pode ser acessada em: [https://librear.vercel.app](https://librear.vercel.app)

---

### Endpoints da API (Consulta e Autenticação)

A seguir, estão os endpoints da API que podem ser utilizados para consulta de dados e autenticação de usuários.

#### **Autenticação**

- **`POST /login`**: Realiza o login do usuário. Retorna um token de autenticação que deve ser incluído nos cabeçalhos das requisições protegidas.
  - **Exemplo de uso:** Autenticar um usuário para acessar recursos protegidos.

#### **Usuários**

- **`GET /user/me`**: Retorna os dados do usuário autenticado.
  - **Requer Autenticação**: Sim
  - **Exemplo de uso:** Exibir o perfil do usuário logado.
- **`GET /user`**: Lista todos os usuários cadastrados.
  - **Exemplo de uso:** Visualizar a lista completa de usuários.

#### **Cursos**

- **`GET /cursos`**: Lista todos os cursos disponíveis na plataforma.
  - **Exemplo de uso:** Exibir o catálogo de cursos para os usuários.
- **`GET /cursos/show/{curso}`**: Retorna os detalhes de um curso específico, identificado pelo ID (`{curso}`).
  - **Exemplo de uso:** Visualizar o conteúdo e informações de um curso.
- **`GET /cursos/search/{search}`**: Realiza uma busca por cursos com base em um termo de pesquisa (`{search}`).
  - **Exemplo de uso:** Encontrar cursos por título, descrição, etc.
- **`POST /cursos/subscribe/{cursos}`**: Permite que um usuário autenticado se inscreva em um curso, identificado pelo ID (`{cursos}`).
  - **Requer Autenticação**: Sim
  - **Exemplo de uso:** Usuário se inscrevendo em um curso de interesse.
- **`GET /cursos/meus_cursos`**: Retorna os cursos nos quais o usuário autenticado está inscrito.
  - **Requer Autenticação**: Sim
  - **Exemplo de uso:** Usuário visualizando sua lista de cursos inscritos.

#### **Aulas**

- **`GET /aulas`**: Lista todas as aulas disponíveis.
  - **Exemplo de uso:** Visualizar o banco de aulas.
- **`GET /aulas/show/{aulas}`**: Retorna os detalhes de uma aula específica, identificada pelo ID (`{aulas}`).
  - **Requer Autenticação**: Sim
  - **Exemplo de uso:** Acessar o conteúdo de uma aula específica.
- **`GET /aulas/search/{search}`**: Realiza uma busca por aulas com base em um termo de pesquisa (`{search}`).
  - **Exemplo de uso:** Encontrar aulas por título, tema, etc.
- **`PATCH /aulas/{aulas}/visto`**: Marca uma aula como vista pelo usuário autenticado.
  - **Requer Autenticação**: Sim
  - **Exemplo de uso:** Usuário marcando uma aula como concluída.

#### **Leituras**

- **`GET /leituras`**: Lista todas as leituras disponíveis.
  - **Exemplo de uso:** Visualizar o acervo de materiais de leitura.
- **`GET /leituras/{leitura}`**: Retorna os detalhes de uma leitura específica, identificada pelo ID (`{leitura}`).
  - **Requer Autenticação**: Sim
  - **Exemplo de uso:** Acessar o conteúdo de um material de leitura.
- **`GET /leituras/search/{search}`**: Realiza uma busca por leituras com base em um termo de pesquisa (`{search}`).
  - **Exemplo de uso:** Encontrar leituras por título, autor, etc.
- **`PATCH /leituras/{leitura}/visto`**: Marca uma leitura como vista pelo usuário autenticado.
  - **Requer Autenticação**: Sim
  - **Exemplo de uso:** Usuário marcando um material de leitura como concluído.

---

### Como Realizar o Build da Aplicação

Para configurar e rodar a aplicação backend localmente, siga os passos abaixo:

1.  **Clone o Repositório:**
    ```bash
    git clone [URL_DO_SEU_REPOSITORIO]
    cd [NOME_DO_SEU_REPOSITORIO] # Substitua pelo nome da pasta do projeto
    ```
2.  **Instale as Dependências do Composer:**
    ```bash
    composer install
    ```
3.  **Configure o Arquivo `.env`:**
    - Crie uma cópia do arquivo de exemplo:
      ```bash
      cp .env.example .env
      ```
    - Abra o arquivo `.env` e configure suas variáveis de ambiente, especialmente as relacionadas ao banco de dados (`DB_CONNECTION`, `DB_HOST`, `DB_PORT`, `DB_DATABASE`, `DB_USERNAME`, `DB_PASSWORD`).
4.  **Gere a Chave da Aplicação:**
    ```bash
    php artisan key:generate
    ```
5.  **Gere a Chave JWT (JSON Web Token):**
    ```bash
    php artisan jwt:secret
    ```
    Este comando irá gerar uma chave secreta e adicioná-la ao seu arquivo `.env`, essencial para a segurança das suas autenticações.
6.  **Execute as Migrações do Banco de Dados:**
    ```bash
    php artisan migrate
    ```
7.  **Opcional: Seed de Dados (para dados de teste):**
    ```bash
    php artisan db:seed
    ```
8.  **Inicie o Servidor de Desenvolvimento:**
    ```bash
    php artisan serve
    ```
    A API estará acessível em `http://127.0.0.1:8000` (ou outra porta, se especificado).

---

### Tour Visual da Aplicação

Para facilitar a compreensão do projeto, preparamos algumas imagens:

- **Capturas de Tela (Screenshots)**:

  ***

  #### **Página de Login**

  ![Screenshot da Página de Login](docs/login_page.png)

  - _(Adicionar imagem da página de login)_

  ***

  #### **Listagem de Cursos**

  ![Screenshot da Listagem de Cursos](docs/courses_list.png)

  - _(Adicionar uma imagem da tela que exibe os cursos disponíveis.)_

  ***

  #### **Detalhes de um Curso**

  ![Screenshot dos Detalhes de um Curso](docs/course_details.png)

  - _(Adicionar uma imagem da tela de detalhes de um curso específico.)_

  ***

  #### **Página Meus Cursos**

  ![Screenshot da Página Meus Cursos](docs/my_courses.png)

  - _(Adicionar uma imagem da tela que exibe os cursos nos quais o usuário está inscrito.)_

---
