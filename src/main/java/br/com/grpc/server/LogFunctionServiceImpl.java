package br.com.grpc.server;

import br.com.santander.mai.logfunction.Greeting;
import br.com.santander.mai.logfunction.JsonAvroMai;
import br.com.santander.mai.logfunction.LogFunctionServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@GRpcService
public class LogFunctionServiceImpl extends LogFunctionServiceGrpc.LogFunctionServiceImplBase {

  private static final Logger LOGGER = LoggerFactory.getLogger(LogFunctionServiceImpl.class);

  @Override
  public void insert(JsonAvroMai jsonAvroMai, StreamObserver<Greeting> responseObserver)   {
    try {
      String endpoint = "/run/MAI";
      HttpEntity<String> request = new HttpEntity<>(jsonAvroMai.getValue());

      WebClient.create().post()
              .uri(endpoint)
              .accept( MediaType.APPLICATION_JSON )
              .body( BodyInserters.fromObject(request) )
              .exchange()
              .flatMap( clientResponse -> clientResponse.bodyToMono(String.class));
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
    Greeting greeting = Greeting.newBuilder().setMessage("Log Functional send with success").build();

    responseObserver.onNext(greeting);
    responseObserver.onCompleted();
  }
}