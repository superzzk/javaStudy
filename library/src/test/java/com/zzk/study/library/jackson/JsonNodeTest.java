package com.zzk.study.library.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

/**
 * @author zhangzhongkun02
 * @date 2023/11/16 17:19
 */
class JsonNodeTest {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void traversing() throws JsonProcessingException {
        JsonNode root = mapper.readTree(jsonStr);

        // Get id
        long id = root.path("id").asLong();
        System.out.println("id : " + id);

        // Get Name
        JsonNode nameNode = root.path("name");
        if (!nameNode.isMissingNode()) {        // if "name" node is exist
            System.out.println("firstName : " + nameNode.path("first").asText());
            System.out.println("middleName : " + nameNode.path("middle").asText());
            System.out.println("lastName : " + nameNode.path("last").asText());
        }

        // Get Contact
        JsonNode contactNode = root.path("contact");
        if (contactNode.isArray()) {

            System.out.println("Is this node an Array? " + contactNode.isArray());

            for (JsonNode node : contactNode) {
                String type = node.path("type").asText();
                String ref = node.path("ref").asText();
                System.out.println("type : " + type);
                System.out.println("ref : " + ref);

            }
        }
    }

    @Test
    void crud() throws JsonProcessingException {
        JsonNode root = mapper.readTree(jsonStr);

        System.out.println("Before Update " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root));

        // 1. Update id to 1000
        ((ObjectNode) root).put("id", 1000L);

        // 2. If middle name is empty , update to M
        ObjectNode nameNode = (ObjectNode) root.path("name");
        if ("".equals(nameNode.path("middle").asText())) {
            nameNode.put("middle", "M");
        }

        // 3. Create a new field in nameNode
        nameNode.put("nickname", "mkyong");

        // 4. Remove last field in nameNode
        nameNode.remove("last");

        // 5. Create a new ObjectNode and add to root
        ObjectNode positionNode = mapper.createObjectNode();
        positionNode.put("name", "Developer");
        positionNode.put("years", 10);
        ((ObjectNode) root).set("position", positionNode);

        // 6. Create a new ArrayNode and add to root
        ArrayNode gamesNode = mapper.createArrayNode();

        ObjectNode game1 = mapper.createObjectNode().objectNode();
        game1.put("name", "Fall Out 4");
        game1.put("price", 49.9);

        ObjectNode game2 = mapper.createObjectNode().objectNode();
        game2.put("name", "Dark Soul 3");
        game2.put("price", 59.9);

        gamesNode.add(game1);
        gamesNode.add(game2);
        ((ObjectNode) root).set("games", gamesNode);

        // 7. Append a new Node to ArrayNode
        ObjectNode email = mapper.createObjectNode();
        email.put("type", "email");
        email.put("ref", "abc@mkyong.com");

        JsonNode contactNode = root.path("contact");
        ((ArrayNode) contactNode).add(email);

        String resultUpdate = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);

        System.out.println("After Update " + resultUpdate);
    }

    @Test
    void missing_node() throws JsonProcessingException {
        JsonNode root = mapper.readTree(jsonStr);
        // not exist
        System.out.println("is missing node:" + root.path("not_exist").isMissingNode());
        long notExist = root.path("not_exist").asLong();
        System.out.println("notExist : " + notExist);
    }

    private static String jsonStr =
            """
                    {
                      "id": 1,
                      "name": {
                        "first": "Yong",
                        "last": "Mook Kim"
                      },
                      "contact": [
                        {
                          "type": "phone/home",
                          "ref": "111-111-1234"
                        },
                        {
                          "type": "phone/work",
                          "ref": "222-222-2222"
                        }
                      ]
                    }
                    """;
}
