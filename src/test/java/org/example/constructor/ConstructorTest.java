package org.example.constructor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstructorTest {

    @Test
    void if_parent_class_is_created_then_child_constructor_is_called() throws Exception {
        Parent parent = new Parent();
    }
}