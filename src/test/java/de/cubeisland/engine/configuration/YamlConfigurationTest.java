/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Anselm Brehme
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.cubeisland.engine.configuration;

import junit.framework.TestCase;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class YamlConfigurationTest extends TestCase
{
    private TestConfig config;
    private TestConfig loadConfig;
    private Path path;

    @Override
    public void setUp() throws Exception
    {
        this.path = new File("../testconfig.yml").toPath();
        config = Configuration.create(TestConfig.class);
    }

    public void testConfiguration() throws Exception
    {
        config.save(path);
        loadConfig = Configuration.load(TestConfig.class, path);
        Files.delete(path);
        assertEquals(config.getCodec().convertSection(config).toString(), config.getCodec().convertSection(loadConfig).toString());
    }
}