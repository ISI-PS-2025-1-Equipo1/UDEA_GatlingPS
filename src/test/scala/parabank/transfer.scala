package parabank

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import parabank.Data._

class transfer extends Simulation{

  // 1 Http Conf
  val httpConf = http.baseUrl(url)
    .acceptHeader("application/json")
    //Verificar de forma general para todas las solicitudes
    .check(status.is(200))
  // 2 Scenario Definition
  val scn = scenario("Escalabilidad en Transacciones").
    exec(http("Transferencias de fondos solicitadas")
      .post("/transfer")  
      .queryParam("fromAccountId", fromAccountId)
      .queryParam("toAccountId", toAccountId)
      .queryParam("amount", amount)
    )
  // 3 Load Scenario
    setUp(
    scn.inject(rampUsersPerSec(5).to(15).during(30))
  ).protocols(httpConf);

}
