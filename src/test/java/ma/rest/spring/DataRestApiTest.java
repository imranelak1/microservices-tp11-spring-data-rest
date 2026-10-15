package ma.rest.spring;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DataRestApiTest {
  @Autowired TestRestTemplate http;

  @Test
  void paginationTriEtIds() {
    var page = http.getForObject("/api/comptes?page=0&size=2&sort=solde,desc", Map.class);
    var rows = (List<Map>) ((Map) page.get("_embedded")).get("comptes");
    assertEquals(2, rows.size());
    assertNotNull(rows.get(0).get("id"));
    assertTrue(
        ((Number) rows.get(0).get("solde")).doubleValue()
            >= ((Number) rows.get(1).get("solde")).doubleValue());
    assertEquals(3, ((Number) ((Map) page.get("page")).get("totalElements")).intValue());
  }

  @Test
  void projectionsSoldeEtMobile() {
    var solde = http.getForObject("/api/comptes/1?projection=solde", Map.class);
    assertNotNull(solde.get("solde"));
    assertNull(solde.get("type"));
    assertNull(solde.get("dateCreation"));
    var mobile = http.getForObject("/api/comptes/1?projection=mobile", Map.class);
    assertEquals("EPARGNE", mobile.get("type"));
    assertNull(mobile.get("dateCreation"));
  }

  @Test
  void rechercheParType() {
    String result = http.getForObject("/api/comptes/search/byType?t=EPARGNE", String.class);
    assertTrue(result.contains("EPARGNE"));
    assertFalse(result.contains("COURANT"));
  }

  @Test
  void liensClientCompteEtProjection() {
    String accounts = http.getForObject("/api/clients/1/comptes", String.class);
    assertTrue(accounts.contains("5000"));
    var client = http.getForObject("/api/comptes/1/client?projection=clientDetails", Map.class);
    assertEquals("Amal", client.get("nom"));
    assertEquals("amal@example.com", client.get("email"));
    assertNull(client.get("comptes"));
  }

  @Test
  void crudAutomatique() {
    Map<String, Object> data =
        new HashMap<>(
            Map.of(
                "solde",
                1000,
                "dateCreation",
                "2024-11-01",
                "type",
                "COURANT",
                "client",
                "/api/clients/1"));
    var created = http.postForEntity("/api/comptes", data, Map.class);
    assertEquals(201, created.getStatusCode().value());
    String path = created.getHeaders().getLocation().getPath();
    data.put("solde", 1300);
    var updated = http.exchange(path, HttpMethod.PUT, new HttpEntity<>(data), Map.class);
    assertEquals(200, updated.getStatusCode().value());
    assertEquals(1300, ((Number) updated.getBody().get("solde")).intValue());
    assertEquals(
        200,
        http.exchange(path, HttpMethod.DELETE, HttpEntity.EMPTY, String.class)
            .getStatusCode()
            .value());
    assertEquals(404, http.getForEntity(path, String.class).getStatusCode().value());
  }
}
