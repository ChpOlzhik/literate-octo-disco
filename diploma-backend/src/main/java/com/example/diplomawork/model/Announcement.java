package com.example.diplomawork.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "announcements")
@Builder
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "filename")
    private String filename;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private LocalDate date;

}
