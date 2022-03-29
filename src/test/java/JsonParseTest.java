
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;


public class JsonParseTest {

    @Test
    void jsonTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonMapper jsonMapper = mapper.readValue(Paths.get("src/test/resources/files/response.json").toFile(), JsonMapper.class);
        assertThat(jsonMapper.code).isEqualTo("28200");
        assertThat(jsonMapper.metadata).isEqualTo("test");
    }
}

