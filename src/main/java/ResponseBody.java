import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponseBody {
    @JsonProperty("code")
    public Integer code;
    @JsonProperty("pos")
    public Integer pos;
    @JsonProperty("row")
    public Integer row;
    @JsonProperty("col")
    public Integer col;
    @JsonProperty("len")
    public Integer len;
    @JsonProperty("word")
    public String word;
    @JsonProperty("s")
    public List<String> s = null;
}
