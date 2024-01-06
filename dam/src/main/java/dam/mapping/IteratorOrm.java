package dam.mapping;

import dam.mapping.MappingOrm;

import java.sql.ResultSet;
import java.sql.SQLException;
/*
author: Tran Duc Anh
description: Class Iterator is using design pattern Iterator
to iterate through ResultSet which is mapped to Object
through class MappingOrm
*/

public class IteratorOrm {
    private MappingOrm mapping;
    private ResultSet rs;

    public IteratorOrm(MappingOrm mapping, ResultSet rs){
        this.mapping = mapping;
        this.rs = rs;
    }

    public <T> T next() {
        return null;
    }

    // check if there is next element in ResultSet

    public boolean hasNext() throws SQLException{
        return false;
    }
}