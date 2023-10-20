package com.springboot.evaluationtask.DashboardModule.enity;

import com.springboot.evaluationtask.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WatchList")
public class WatchListGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String groupName;

    @ElementCollection
    @CollectionTable(
            name = "watchlist_group_symbols",
            joinColumns = @JoinColumn(name = "watchlist_group_id")
    )
    @Column(name = "symbol_name")
    private List<String> symbols = new ArrayList<>();

    @Override
    public String toString() {
        return "WatchListGroup{" +
                "id=" + id +
                ", user=" + user +
                ", groupName='" + groupName + '\'' +
                ", symbols=" + symbols +
                '}';
    }
}
