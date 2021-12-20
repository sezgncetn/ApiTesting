package apiTest.apiActions;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Random;

import org.json.simple.JSONObject;
import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Actions {

	private JSONObject requestParams;

	public Actions() {

		this.requestParams = new JSONObject();
		requestParams.put("key", apiKey); // Cast
		requestParams.put("token", token);
		RestAssured.baseURI = "https://api.trello.com/1/";
	}

	static String apiKey = "65e73c433a2bd141fd6936bd679d47f9";
	static String token = "5875331c25564ecd3e0262e2186a6b8d0b5ccb6b6e48ac844ee50412ed74ddb4";

	public String createBoard(String boardName) {

		requestParams.put("name", boardName);
		Response response = given().contentType(ContentType.JSON).when().body(requestParams.toJSONString())
				.post("/boards").then().extract().response();
		Assert.assertEquals(200, response.statusCode());
		return response.jsonPath().getString("id");
	}

	public void deleteBoard(String boardId) {

		Response response = given().contentType(ContentType.JSON).when().body(requestParams.toJSONString())
				.delete("/boards/" + boardId).then().extract().response();
		Assert.assertEquals(200, response.statusCode());
	}

	public String createList(String listName, String boardId) { 

		requestParams.put("name", listName);
		requestParams.put("idBoard", boardId);
		Response response = given().contentType(ContentType.JSON).when().body(requestParams.toJSONString())
				.post("/lists").then().extract().response();
		Assert.assertEquals(200, response.statusCode());
		return response.jsonPath().getString("id");
	}

	public void createCard(String cardName, String listId) {

		requestParams.put("name", cardName);
		requestParams.put("idList", listId);
		Response response = given().contentType(ContentType.JSON).when().body(requestParams.toJSONString())
				.post("/cards").then().extract().response();
		Assert.assertEquals(200, response.statusCode());

	}

	public String getRandomCardId(String listId) {

		Response response = given().contentType(ContentType.JSON).when().body(requestParams.toJSONString())
				.get("/lists/" + listId + "/cards").then().extract().response();
		Assert.assertEquals(200, response.statusCode());
		List<String> jsonResponse = response.jsonPath().getList("id");
		Random rn = new Random();
		int index = rn.nextInt(jsonResponse.size());
		return jsonResponse.get(index);
	}

	public void moveCard(String cardId, String listId) {

		requestParams.put("idList", listId);
		Response response = given().contentType(ContentType.JSON).when().body(requestParams.toJSONString())
				.put("/cards/" + cardId).then().extract().response();

		Assert.assertEquals(200, response.statusCode());
	}

	public void deleteCard(String cardId) {

		Response response = given().contentType(ContentType.JSON).when().body(requestParams.toJSONString())
				.delete("/cards/" + cardId).then().extract().response();

		Assert.assertEquals(200, response.statusCode());
	}

	public void deleteCards(String listId) {

		Response response = given().contentType(ContentType.JSON).when().body(requestParams.toJSONString())
				.get("/lists/" + listId + "/cards").then().extract().response();
		Assert.assertEquals(200, response.statusCode());
		List<String> jsonResponse = response.jsonPath().getList("id");
		for (int i = 0; i < jsonResponse.size(); i++) {

			deleteCard(jsonResponse.get(i));
		}

	}
}
