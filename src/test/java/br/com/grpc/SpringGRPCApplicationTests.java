package br.com.grpc;

import br.com.grpc.client.LogFunctionClient;
import br.com.santander.mai.logfunction.JsonAvroMai;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringGRPCApplicationTests {

  @Autowired
  private LogFunctionClient logFunctionClient;

  @Test
  public void testSayHello() {
    JsonAvroMai jsonAvroMai = JsonAvroMai.newBuilder().setValue("{aaa:'hehehe'}").build();

    logFunctionClient.insert(jsonAvroMai);
  }
}
