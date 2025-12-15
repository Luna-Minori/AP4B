package Common;

import java.io.Serializable;

public class NetworkMessage implements Serializable {
    public String title;
    public String subTitle;
    public Object content;

    public NetworkMessage(String title, String subTitle, Object content) {
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
    }
}

