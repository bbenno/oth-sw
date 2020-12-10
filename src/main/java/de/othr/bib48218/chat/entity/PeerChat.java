package de.othr.bib48218.chat.entity;

import lombok.Getter;
import javax.persistence.Entity;

@Entity
@Getter
public class PeerChat extends Chat {
  public PeerChat() {
    super();
  }
}
