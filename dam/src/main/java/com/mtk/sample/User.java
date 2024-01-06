package com.mtk.sample;

import com.mtk.annotation.Column;
import com.mtk.annotation.Id;
import com.mtk.annotation.Record;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Record(table = "user")
public class User {

    @Id
    private String id;

    @Column(name = "username")
    private String name;
}
