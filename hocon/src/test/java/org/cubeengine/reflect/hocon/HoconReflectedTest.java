/*
 * The MIT License
 * Copyright © 2013 Cube Island
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.cubeengine.reflect.hocon;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import org.cubeengine.reflect.ReflectedTestFile;
import org.cubeengine.reflect.ReflectedTest2;
import org.cubeengine.reflect.Reflector;
import org.cubeengine.reflect.codec.hocon.HoconCodec;
import org.cubeengine.reflect.exception.DuplicatedPathException;
import org.junit.Before;
import org.junit.Test;

import static org.cubeengine.reflect.ReflectedFieldShadowing.ReflectedFieldShadowing2;
import static org.cubeengine.reflect.util.AssertionUtils.assertEqualsDeep;
import static org.junit.Assert.assertEquals;

public class HoconReflectedTest
{
    private ReflectedTestFile test1;
    private ReflectedTest2 test2;
    private File file;

    private Reflector factory;
    private HoconCodec codec;

    @Before
    public void setUp() throws Exception
    {
        this.file = new File("../testReflected.conf");
        this.factory = new Reflector();
        test1 = ReflectedTestFile.getDefaultReflectedTest(factory);
        this.test2 = factory.create(ReflectedTest2.class);
        codec = factory.getCodecManager().getCodec(HoconCodec.class);
    }

    @Test
    public void test() throws Exception
    {
        FileWriter writer = new FileWriter(file);
        codec.saveReflected(test1, writer);
        writer.close();
        final ReflectedTestFile reflected = factory.create(ReflectedTestFile.class);
        FileReader reader = new FileReader(file);
        codec.loadReflected(reflected, reader);
        reader.close();
        file.delete();
        assertEqualsDeep(codec.getConverterManager(), test1, reflected);
    }

    @Test
    public void test2() throws Exception
    {
        FileWriter writer = new FileWriter(file);
        codec.saveReflected(test2, writer);
        writer.close();
        final ReflectedTest2 reflected = factory.create(ReflectedTest2.class);
        FileReader reader = new FileReader(file);
        codec.loadReflected(reflected, reader);
        reader.close();
        file.delete();
        assertEqualsDeep(codec.getConverterManager(), test2, reflected);
    }

    @Test(expected = DuplicatedPathException.class)
    public void test3() throws Exception
    {
        factory.create(ReflectedFieldShadowing2.class);
    }
}
