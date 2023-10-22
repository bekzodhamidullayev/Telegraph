package uz.telegraph.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "telegraph")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TelegraphEntity extends BaseEntity {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private String author;

    private String title;

    private String description;

    @Column(unique = true)
    private String link;
}
