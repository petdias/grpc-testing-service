package br.com.grpc.client;

import br.com.santander.mai.logfunction.Greeting;
import br.com.santander.mai.logfunction.JsonAvroMai;
import br.com.santander.mai.logfunction.LogFunctionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LogFunctionClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(LogFunctionClient.class);

  private LogFunctionServiceGrpc.LogFunctionServiceBlockingStub logFunctionServiceBlockingStub;

  @PostConstruct
  private void init() {
    ManagedChannel managedChannel = ManagedChannelBuilder
        .forAddress("localhost", 6565).usePlaintext().build();

    logFunctionServiceBlockingStub = LogFunctionServiceGrpc.newBlockingStub(managedChannel);
  }

  public String insert(JsonAvroMai avroMai) {

    LOGGER.info("client sending {}", avroMai.getValue());

    Greeting greeting = logFunctionServiceBlockingStub.insert(avroMai);
    LOGGER.info("client received {}", greeting);

    return greeting.getMessage();
  }
}
