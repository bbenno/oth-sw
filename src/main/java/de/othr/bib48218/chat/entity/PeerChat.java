package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringJoiner;

@Entity
@Getter
@NoArgsConstructor
public class PeerChat extends Chat {
    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" \uD83E\udC58 ");
        joiner.setEmptyValue(super.toString());
        getMemberships().stream().map(ChatMembership::getUser).map(User::toString).forEach(joiner::add);
        return joiner.toString();
    }
}
