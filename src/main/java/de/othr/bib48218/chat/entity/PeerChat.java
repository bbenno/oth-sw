package de.othr.bib48218.chat.entity;

import java.util.StringJoiner;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PeerChat extends Chat {

    public boolean isEnabled() {
        return getMemberships().stream().allMatch(m -> m.getUser().isEnabled());
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" \uD83E\udC58 ");
        joiner.setEmptyValue(super.toString());
        getMemberships().stream().map(ChatMembership::getUser).map(User::toString)
            .forEach(joiner::add);
        return joiner.toString();
    }
}
