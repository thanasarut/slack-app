package bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Contact {
    private String nickname;
    private String extension;

    public Contact(String nickname, String extension) {
        this.nickname = nickname;
        this.extension = extension;
    }
}
