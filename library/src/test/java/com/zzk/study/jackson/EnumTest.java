package com.zzk.study.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class EnumTest {

	@Test
	public void defaultEnum() throws JsonProcessingException {
		String s = new ObjectMapper().writeValueAsString(Distance.MILE);
		assertEquals(s, "\"MILE\"");
	}

	@Test
	public void formatEnum() throws JsonProcessingException {
		String s = new ObjectMapper().writeValueAsString(DistanceWithFormat.MILE);
		assertEquals(s, "{\"unit\":\"miles\",\"meters\":1609.34}");
	}

	@Test
	public void jsonValueEnum() throws JsonProcessingException {
		String s = new ObjectMapper().writeValueAsString(DistanceWithJsonValue.MILE);
		assertEquals(s, "1609.34");
	}

	@Test
	public void serializerEnum() throws JsonProcessingException {
		String s = new ObjectMapper().writeValueAsString(DistanceWithSerializer.MILE);
		System.out.println(s);
	}

	// By default, Jackson will use the Enum name to deserialize from JSON.
	@Test
	public void deserializeEnum() throws IOException {
		String json = "{\"distance\":\"KILOMETER\"}";
		City city = new ObjectMapper().readValue(json, City.class);
		assertEquals(Distance.KILOMETER, city.getDistance());
	}

	@Test
	public void deserializeEnumWithJsonValue() throws IOException {
		String json = "{\"distance\":\"0.0254\"}";
		CityWithJsonValue city = new ObjectMapper().readValue(json, CityWithJsonValue.class);
		assertEquals(DistanceWithJsonValue.INCH, city.getDistance());
	}

	@Test
	public void deserializeEnumWithJsonProperty() throws IOException {
		String json = "{\"distance\": \"distance-in-km\"}";
		CityWithJsonProperty city = new ObjectMapper().readValue(json, CityWithJsonProperty.class);
		assertEquals(DistanceWithJsonProperty.INCH, city.getDistance());
	}

	@Test
	public void deserializeEnumWithJsonCreator() throws IOException {
		String json = "{\n" +
				"    \"distance\": {\n" +
				"        \"unit\":\"miles\", \n" +
				"        \"meters\":1609.34\n" +
				"    }\n" +
				"}";
		CityWithJsonCreator city = new ObjectMapper().readValue(json, CityWithJsonCreator.class);
		assertEquals(DistanceWithJsonCreator.MILE, city.getDistance());
	}

	@Test
	public void deserializeEnumWithDeserializer() throws IOException {
		String json = "{\n" +
				"    \"distance\": {\n" +
				"        \"unit\":\"miles\", \n" +
				"        \"meters\":1609.34\n" +
				"    }\n" +
				"}";
		CityWithDeserializer city = new ObjectMapper().readValue(json, CityWithDeserializer.class);
		assertEquals(DistanceWithDeserializer.MILE, city.getDistance());
	}

	public enum Distance {
		KILOMETER("km", 1000),
		MILE("miles", 1609.34),
		METER("meters", 1),
		INCH("inches", 0.0254),
		CENTIMETER("cm", 0.01),
		MILLIMETER("mm", 0.001);

		private String unit;
		private final double meters;

		private Distance(String unit, double meters) {
			this.unit = unit;
			this.meters = meters;
		}

		// standard getters and setters
	}

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum DistanceWithFormat {
		KILOMETER("km", 1000),
		MILE("miles", 1609.34),
		METER("meters", 1),
		INCH("inches", 0.0254),
		CENTIMETER("cm", 0.01),
		MILLIMETER("mm", 0.001);

		private String unit;
		private final double meters;

		private DistanceWithFormat(String unit, double meters) {
			this.unit = unit;
			this.meters = meters;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public double getMeters() {
			return meters;
		}
	}

	public enum DistanceWithJsonValue {
		KILOMETER("km", 1000),
		MILE("miles", 1609.34),
		METER("meters", 1),
		INCH("inches", 0.0254),
		CENTIMETER("cm", 0.01),
		MILLIMETER("mm", 0.001);

		private String unit;
		private final double meters;

		private DistanceWithJsonValue(String unit, double meters) {
			this.unit = unit;
			this.meters = meters;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}
		@JsonValue
		public double getMeters() {
			return meters;
		}
	}

	@JsonSerialize(using = DistanceSerializer.class)
	public enum DistanceWithSerializer {
		KILOMETER("km", 1000),
		MILE("miles", 1609.34),
		METER("meters", 1),
		INCH("inches", 0.0254),
		CENTIMETER("cm", 0.01),
		MILLIMETER("mm", 0.001);

		private String unit;
		private final double meters;

		private DistanceWithSerializer(String unit, double meters) {
			this.unit = unit;
			this.meters = meters;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public double getMeters() {
			return meters;
		}
	}

	public static class DistanceSerializer extends StdSerializer<DistanceWithSerializer> {

		public DistanceSerializer() {
			super(DistanceWithSerializer.class);
		}

		public DistanceSerializer(Class t) {
			super(t);
		}

		@Override
		public void serialize(DistanceWithSerializer distance, JsonGenerator generator, SerializerProvider provider)
				throws IOException, JsonProcessingException {
			generator.writeStartObject();
			generator.writeFieldName("name");
			generator.writeString(distance.name());
			generator.writeFieldName("unit");
			generator.writeString(distance.getUnit());
			generator.writeFieldName("meters");
			generator.writeNumber(distance.getMeters());
			generator.writeEndObject();
		}
	}

	public static class City {

		private Distance distance;

		public City() {}

		public Distance getDistance() {
			return distance;
		}

		public void setDistance(Distance distance) {
			this.distance = distance;
		}
	}

	public static class CityWithJsonValue {

		private DistanceWithJsonValue distance;

		public CityWithJsonValue() {}

		public DistanceWithJsonValue getDistance() {
			return distance;
		}

		public void setDistance(DistanceWithJsonValue distance) {
			this.distance = distance;
		}
	}

	public static class CityWithJsonProperty {

		private DistanceWithJsonProperty distance;

		public CityWithJsonProperty() {}

		public DistanceWithJsonProperty getDistance() {
			return distance;
		}

		public void setDistance(DistanceWithJsonProperty distance) {
			this.distance = distance;
		}
	}

	public static enum DistanceWithJsonProperty {
		@JsonProperty("distance-in-km")
		KILOMETER("km", 1000),
		@JsonProperty("distance-in-miles")
		MILE("miles", 1609.34),
		@JsonProperty("distance-in-meters")
		METER("meters", 1),
		@JsonProperty("distance-in-inches")
		INCH("inches", 0.0254),
		@JsonProperty("distance-in-cm")
		CENTIMETER("cm", 0.01),
		@JsonProperty("distance-in-mm")
		MILLIMETER("mm", 0.001);

		private String unit;
		private final double meters;

		private DistanceWithJsonProperty(String unit, double meters) {
			this.unit = unit;
			this.meters = meters;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public double getMeters() {
			return meters;
		}
	}

	public static class CityWithJsonCreator {

		private DistanceWithJsonCreator distance;

		public CityWithJsonCreator() {}

		public DistanceWithJsonCreator getDistance() {
			return distance;
		}

		public void setDistance(DistanceWithJsonCreator distance) {
			this.distance = distance;
		}
	}

	public enum DistanceWithJsonCreator {

		KILOMETER("km", 1000),
		MILE("miles", 1609.34),
		METER("meters", 1),
		INCH("inches", 0.0254),
		CENTIMETER("cm", 0.01),
		MILLIMETER("mm", 0.001);

		private String unit;
		private final double meters;

		private DistanceWithJsonCreator(String unit, double meters) {
			this.unit = unit;
			this.meters = meters;
		}

		@JsonCreator
		public static DistanceWithJsonCreator forValues(@JsonProperty("unit") String unit,
		                                 @JsonProperty("meters") double meters) {
			for (DistanceWithJsonCreator distance : DistanceWithJsonCreator.values()) {
				if (
						distance.unit.equals(unit) && Double.compare(distance.meters, meters) == 0) {
					return distance;
				}
			}

			return null;
		}
	}

	public static class CityWithDeserializer {

		private DistanceWithDeserializer distance;

		public CityWithDeserializer() {}

		public DistanceWithDeserializer getDistance() {
			return distance;
		}

		public void setDistance(DistanceWithDeserializer distance) {
			this.distance = distance;
		}
	}

	@JsonDeserialize(using = CustomEnumDeserializer.class)
	public enum DistanceWithDeserializer {
		KILOMETER("km", 1000),
		MILE("miles", 1609.34),
		METER("meters", 1),
		INCH("inches", 0.0254),
		CENTIMETER("cm", 0.01),
		MILLIMETER("mm", 0.001);

		private String unit;
		private final double meters;

		private DistanceWithDeserializer(String unit, double meters) {
			this.unit = unit;
			this.meters = meters;
		}

		public String getUnit() {
			return unit;
		}

		public double getMeters() {
			return meters;
		}
	}

	public static class CustomEnumDeserializer extends StdDeserializer<DistanceWithDeserializer> {

		protected CustomEnumDeserializer() {
			this(null);
		}
		protected CustomEnumDeserializer(Class<?> vc) {
			super(vc);
		}

		@Override
		public DistanceWithDeserializer deserialize(JsonParser jsonParser, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			JsonNode node = jsonParser.getCodec().readTree(jsonParser);

			String unit = node.get("unit").asText();
			double meters = node.get("meters").asDouble();

			for (DistanceWithDeserializer distance : DistanceWithDeserializer.values()) {

				if (distance.getUnit().equals(unit) && Double.compare(
						distance.getMeters(), meters) == 0) {
					return distance;
				}
			}

			return null;
		}
	}
}
