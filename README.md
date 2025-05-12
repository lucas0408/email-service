# Microsserviço de Email

Este microsserviço é responsável pelo envio de emails aos usuários do sistema, consumindo mensagens de uma fila RabbitMQ.

## 📋 Descrição

Este projeto implementa um microsserviço dedicado ao envio de emails, seguindo o princípio de responsabilidade única. O serviço monitora uma fila RabbitMQ nomeada "email", processa as mensagens recebidas e envia os emails correspondentes aos destinatários.

## 🛠️ Tecnologias Utilizadas

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring AMQP](https://spring.io/projects/spring-amqp) (para integração com RabbitMQ)
- [RabbitMQ](https://www.rabbitmq.com/)
- [Java Mail Sender](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/mail/javamail/JavaMailSender.html)

## 📦 Pré-requisitos

- JDK 17 ou superior
- Maven
- PostgreSQL
- RabbitMQ
- Conta de email (configurado para Gmail por padrão)

## ⚙️ Configuração

### Variáveis de Ambiente

O sistema utiliza variáveis de ambiente para configuração. Abaixo estão as principais variáveis que precisam ser definidas:

```properties
# Configuração do servidor
server.port=${serve_port}

# Configuração do banco de dados
spring.datasource.url=${db_url}
spring.datasource.username=postgres
spring.datasource.password=${db_password}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true

# Configuração do RabbitMQ
spring.rabbitmq.addresses=${rabbit_address}
broker.queue.email.name=${queue}

# Configuração do servidor de email
spring.mail.host=smtp.gmail.com
spring.mail.port=${email_port}
spring.mail.username=${email_user}
spring.mail.password=${email_password}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

Você precisará definir as seguintes variáveis de ambiente:
- `serve_port`: Porta em que o serviço será executado
- `db_url`: URL de conexão com o banco de dados PostgreSQL
- `db_password`: Senha do banco de dados
- `rabbit_address`: Endereço do servidor RabbitMQ
- `queue`: Nome da fila para processamento de emails
- `email_port`: Porta SMTP (geralmente 587 para Gmail)
- `email_user`: Endereço de email usado para enviar mensagens
- `email_password`: Senha do email ou token de aplicativo

## 🚀 Instalação e Execução

1. Clone o repositório:
   ```bash
   git clone https://github.com/your-username/email-microservice.git
   cd email-microservice
   ```

2. Configure as variáveis de ambiente necessárias:
   - Crie um arquivo `.env` na raiz do projeto ou configure as variáveis no seu ambiente

3. Compile o projeto:
   ```bash
   mvn clean install
   ```

4. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

Alternativamente, você pode executar usando Docker:

```bash
# Construir a imagem
docker build -t email-microservice .

# Executar o container
docker run -p 8080:${serve_port} --env-file .env email-microservice
```

## 🔧 Uso

O microsserviço aguarda mensagens na fila "email" do RabbitMQ. Cada mensagem deve conter:

```json
{
  "to": "destinatario@exemplo.com",
  "subject": "Assunto do Email",
  "body": "Conteúdo do email em texto ou HTML",
  "isHtml": true
}
```

## 📊 Monitoramento

O serviço expõe endpoints de saúde e métricas através do Spring Actuator:

- Saúde: `/actuator/health`
- Métricas: `/actuator/metrics`
- Informações: `/actuator/info`

## ⚠️ Tratamento de Erros

O serviço implementa políticas de retry para lidar com falhas temporárias no envio de emails ou indisponibilidade do servidor SMTP. Após um número configurável de tentativas, as mensagens são encaminhadas para uma fila de dead-letter para análise posterior.

## 📝 Logs

Os logs do serviço são configurados para mostrar informações relevantes sobre o processamento das mensagens e envio de emails, facilitando a depuração em caso de problemas.

## 🧪 Testes

Execute os testes unitários e de integração com:

```bash
mvn test
```

## 📄 Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

## ✒️ Autor

- **Seu Nome** - [Lucas Gabriel](https://github.com/lucas0408)

## 🔗 Links Úteis

- [Documentação do Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Documentação do RabbitMQ](https://www.rabbitmq.com/documentation.html)
