import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonMapper {
    public String code;
    public String metadata;

}
