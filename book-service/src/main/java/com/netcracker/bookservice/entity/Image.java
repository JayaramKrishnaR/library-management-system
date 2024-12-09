package com.netcracker.bookservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid") @Column(length = 36)
    private UUID imageId;
    private byte[] imageData;

}
