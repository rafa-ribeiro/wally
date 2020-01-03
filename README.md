# Wally
Sistema para controle de investimentos

Ferramentas utilizadas: Spring, MySQL5.7, Bootstrap

Antes de rodar a aplicação, atualize o application.properties com as configurações do seu banco de dados.
```
db.name=wally
db.username=root
db.password=root
```

Acesse o servidor de banco de dados e crie o banco de dados que será utilizado na aplicação. 
```
mysql -u root -p 
create database wally;
```

## TODO

- planilha de cadastro / importação não copia para dentro da pasta do wally - recuperar produtos na lista!!!
- calculo da ação - tipo de investimento ACAO
- total do patrimonio incluir FII 
- buscar dividendos
- calcular e mostrar na tabela dividend yield para FII
- calcular e mostrar na tabela dividend yield para ACAO
- subir dados de feriados de forma automática
- buscar dados de indice de forma automatica ou melhorar cadastro de dados de mercado
- grafico da evolução da rentabilidade (com divend yield)
- grafico da evolução do patrimonio
- calculo do titulo publico
- atualizar lista de dividendos passados
- atualizar valores de mercado de CDI e IPCA (ok até junho)
- patrimonio total está mudando de valor quando muda a tela.
