Aplicativo para o administrador gerenciar os usuarios e rank do jogo.
Conectado a API no render, com o link https://jogo2025.onrender.com, utilize os metodos para teste: https://jogo2025.onrender.com/usuarios/top10, https://jogo2025.onrender.com/usuarios.
O banco está hospedado no supabase e entra em inatividade após 1 semana sem conexão.
Após algum tempo sem requisições a API do render entra em uma fila de baixa prioridade e é preciso esperar para que a requisição funcione, podendo causar um erro ao iniciar o codigo na primeira vez.
