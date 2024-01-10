package com.mtk.sample;

import com.mtk.annotation.Column;
import com.mtk.annotation.Id;
import com.mtk.annotation.Record;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Record(table = "user")
public class User {

    @Id(name = "id")
    private Integer id;

    @Column(name = "username")
    private String name;
}
