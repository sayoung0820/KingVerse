package com.example.kingverse.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("character_entity")
public class CharacterEntity {
    @Id
    @Column("character_id")
    private Integer characterId;

    private String name;
    private String description;

    @Column("first_appearance")
    private Integer firstAppearance; // FK to book.book_id

    @Column("image_url")
    private String imageUrl;

    public CharacterEntity() {}

    public CharacterEntity(Integer characterId, String name, String description, Integer firstAppearance, String imageUrl) {
        this.characterId = characterId;
        this.name = name;
        this.description = description;
        this.firstAppearance = firstAppearance;
        this.imageUrl = imageUrl;
    }

    public Integer getCharacterId() { return characterId; }
    public void setCharacterId(Integer characterId) { this.characterId = characterId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getFirstAppearance() { return firstAppearance; }
    public void setFirstAppearance(Integer firstAppearance) { this.firstAppearance = firstAppearance; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    @Override
    public String toString() {
        return "CharacterEntity{" +
                "characterId=" + characterId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", firstAppearance=" + firstAppearance +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

