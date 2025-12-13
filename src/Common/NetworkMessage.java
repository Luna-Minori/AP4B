package Common;

import java.io.Serializable;

public class NetworkMessage implements Serializable {
    public String title;
    public Object content;

    public NetworkMessage(String title, Object content) {
        this.title = title;
        this.content = content;
    }
}

