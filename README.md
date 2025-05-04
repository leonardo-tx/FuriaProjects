# Furia Projects
Nesse repositório se encontra os desafios propostos para o Estágio da Fúria

## Chat de Counter-Strike da Fúria
No projeto FuriaCSChat, foi feito um projeto simples de chat 
online em tempo real utilizando SpringBoot / Java para o back-end
e NextJS / React / Typescript para o front-end.

O banco de dados utilizado é o H2 que é em memória, ele foi utilizado
de modo a simplificar a execução do projeto.

Para executar ele, é necessário executar tanto o servidor web, quanto a API.

### Executando a API
No diretório raiz do repositório:
```bash
cd FuriaCSChat
cd back-end
mvn clean install
mvn spring-boot:run
```

### Executando o Servidor Web
No diretório raiz do repositório:
```bash
cd FuriaCSChat
cd front-end
yarn install
yarn run build
yarn run start
```

### No navegador
Após executar o Servidor Web e a API, só é apenas necessário se conectar
ao servidor web no navegador, pelo endereço http://localhost:3000