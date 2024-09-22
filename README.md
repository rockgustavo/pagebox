# Pagebox

## Projeto de diretórios para controle de arquivos

### Back-End - Java Spring Boot

### Docker - Criar a imagem do projeto

```
docker run -p 8080:8080 -d pagebox
```

### Docker - Rodar um container com a imagem do projeto

```
docker run --name pageboxcontainer -p 8080:8080 -d pagebox
```

### Documentação do projeto após rodar o container

```
http://localhost:8080/swagger-ui/index.html#/
```
