package com.peaksoft.accounting.db.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
@Getter
@Setter
@RequiredArgsConstructor
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_tag_id_seq")
    @SequenceGenerator(name = "tags_tag_id_seq", sequenceName = "tags_tag_id_seq", allocationSize = 1)
    private Long tag_id;
    private String nameTag;
    private String description;
    @JsonIgnore
    @ManyToMany(targetEntity = ClientEntity.class,
            mappedBy = "tags", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<ClientEntity> clients;


    public void addClient(ClientEntity client) {
        if(clients == null){
            clients = new ArrayList<>();
        }
        clients.add(client);
        client.addTags(this);
    }
}
