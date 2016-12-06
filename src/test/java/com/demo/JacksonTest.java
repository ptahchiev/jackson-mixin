/*
 * nemesis Platform - NExt-generation Multichannel E-commerce SYStem
 *
 * Copyright (c) 2010 - 2016 nemesis
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of nemesis
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with nemesis.
 */

package com.demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * @author Petar Tahchiev
 * @since 1.5
 */
public class JacksonTest {

    @Test
    public void test2() throws IOException {

        Module module = new SimpleModule("TestModule") {

            @Override
            public void setupModule(Module.SetupContext context) {
                context.addAbstractTypeResolver(new SimpleAbstractTypeResolver().addMapping(AbstractArgsConstructor.class, SingleArgConstructor.class));
            }
        };

        String source = "{\"n\" : 10}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
        objectMapper.addMixIn(SingleArgConstructor.class, MixIn.class);

        SingleArgConstructor ctx = objectMapper.readValue(source, SingleArgConstructor.class);
        assertNotNull(ctx);

    }

    abstract class MixIn {
        @JsonCreator
        MixIn(@JsonProperty("n") int n) {
        }
    }

}
