package com.zzk.study.library.jackson;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ObjectMapperTest {

	@Test
	public void testWriteToFile() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Car car = new Car("yellow", "renault");
		objectMapper.writeValue(new File("target/car.json"), car);
	}

	@Test
	public void writeToString() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Car car = new Car("yellow", "renault");
		String carAsString = objectMapper.writeValueAsString(car);
	}

	@Test
	public void readObjectFromString() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
		Car car = objectMapper.readValue(json, Car.class);
	}

	@Test
	public void readJsonNodeFromString() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = "{ \"color\" : \"Black\", \"type\" : \"FIAT\" }";
		JsonNode jsonNode = objectMapper.readTree(json);
		String color = jsonNode.get("color").asText();
		Assert.assertEquals(color,"Black");
	}

	@Test
	public void readListFromJsonArrayString() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonCarArray =
				"[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
		List<Car> listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>(){});
		Assert.assertEquals(listCar.size(),2);
	}

	@Test
	public void readMapFromJsonString() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
		Map<String, Object> map
				= objectMapper.readValue(json, new TypeReference<Map<String,Object>>(){});
		Assert.assertEquals(map.size(),2);
	}

	@Test(expected = JsonProcessingException.class)
	public void readObjFromJsonString() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json
				= "{ \"color\" : \"Black\", \"type\" : \"Fiat\", \"year\" : \"1970\" }";
		Car car = objectMapper.readValue(json, Car.class);
	}
	@Test
	public void readObjFromJsonStringWithoutException() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json
				= "{ \"color\" : \"Black\", \"type\" : \"Fiat\", \"year\" : \"1970\" }";
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Car car = objectMapper.readValue(json, Car.class);

		JsonNode jsonNodeRoot = objectMapper.readTree(json);
		JsonNode jsonNodeYear = jsonNodeRoot.get("year");
		String year = jsonNodeYear.asText();
	}

	@Test
	public void customSerializer() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module =
				new SimpleModule("CustomCarSerializer", new Version(1, 0, 0, null, null, null));
		module.addSerializer(Car.class, new CustomCarSerializer());
		mapper.registerModule(module);
		Car car = new Car("yellow", "renault");
		String carJson = mapper.writeValueAsString(car);
		System.out.println(carJson);
	}

	@Test
	public void customDeserializer() throws IOException {
		String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module =
				new SimpleModule("CustomCarDeserializer", new Version(1, 0, 0, null, null, null));
		module.addDeserializer(Car.class, new CustomCarDeserializer());
		mapper.registerModule(module);
		Car car = mapper.readValue(json, Car.class);
	}

	@Test
	public void dateFormat() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
		objectMapper.setDateFormat(df);
		Car car = new Car("yellow", "renault");
		Request request = new Request(car, new Date());
		String carAsString = objectMapper.writeValueAsString(request);
		System.out.println(carAsString);
		// output: {"car":{"color":"yellow","type":"renault"},"datePurchased":"2016-07-03 11:43 AM CEST"}
	}

	@Test
	public void collections() throws IOException {
		String jsonCarArray =
				"[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
		Car[] cars = objectMapper.readValue(jsonCarArray, Car[].class);
		// print cars
		Assert.assertEquals(cars.length, 2);
		List<Car> listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>(){});
		Assert.assertEquals(listCar.size(), 2);
	}

	public static class Car {

		public String color;
		public String type;

		public Car(String color, String type) {
			this.color = color;
			this.type = type;
		}

		public Car(){}
	}

	public static class CustomCarSerializer extends StdSerializer<Car> {

		public CustomCarSerializer() {
			this(null);
		}

		public CustomCarSerializer(Class<Car> t) {
			super(t);
		}

		@Override
		public void serialize(
				Car car, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
			jsonGenerator.writeStartObject();
			jsonGenerator.writeStringField("car_brand", car.type);
			jsonGenerator.writeEndObject();
		}
	}

	public static class CustomCarDeserializer extends StdDeserializer<Car> {

		public CustomCarDeserializer() {
			this(null);
		}

		public CustomCarDeserializer(Class<?> vc) {
			super(vc);
		}

		@Override
		public Car deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
			Car car = new Car();
			ObjectCodec codec = parser.getCodec();
			JsonNode node = codec.readTree(parser);

			// try catch block
			JsonNode colorNode = node.get("color");
			String color = colorNode.asText();
			car.color = color;
			return car;
		}
	}

	public static class Request
	{
		public Car car;
		public Date datePurchased;

		// standard getters setters

		public Request(Car car, Date datePurchased) {
			this.car = car;
			this.datePurchased = datePurchased;
		}
	}

}
