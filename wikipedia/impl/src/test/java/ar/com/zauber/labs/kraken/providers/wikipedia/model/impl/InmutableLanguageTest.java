/**
 * Copyright (c) 2009 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.com.zauber.labs.kraken.providers.wikipedia.model.impl;

import junit.framework.Assert;

import org.junit.Test;

import ar.com.zauber.labs.kraken.providers.wikipedia.model.Language;


/**
 * Test
 * 
 * 
 * @author Juan F. Codagnone
 * @since Sep 20, 2009
 */
public class InmutableLanguageTest {

    /** test */
    @Test
    public final void testInvalidLanguage()  {
        try {
            new InmutableLanguage("asdasd");
        } catch(IllegalArgumentException e) {
            // ok
        }
    }
    
    /** test */
    @Test
    public final void testNullLanguage()  {
        try {
            new InmutableLanguage(null);
        } catch(IllegalArgumentException e) {
            // ok
        }
    }
    
    /** test */
    @Test
    public final void testLanguage()  {
        final Language l = new InmutableLanguage("es");
        Assert.assertEquals("es", l.getIsoName());
    }
    
    /** test */
    @Test
    public final void testToString()  {
        final Language l = new InmutableLanguage("es");
        Assert.assertEquals("es", l.toString());
    }
    
    /** test */
    @Test
    public final void testEquals()  {
        final Language l1 = new InmutableLanguage("es");
        final Language l2 = new InmutableLanguage("es");
        final Language l3 = new InmutableLanguage("en");
        Assert.assertEquals(l1, l2);
        Assert.assertFalse(l1.equals(null));
        Assert.assertFalse(l1.equals(l3));
        Assert.assertEquals(l1.hashCode(), l2.hashCode());
    }
    
    /** test */
    @Test
    public final void testCompareTo()  {
        final Language l1 = new InmutableLanguage("es");
        final Language l2 = new InmutableLanguage("es");
        final Language l3 = new InmutableLanguage("en");
        
        Assert.assertEquals(0, l1.compareTo(l2));
        Assert.assertTrue(l1.compareTo(l3) > 1);
        Assert.assertTrue(l3.compareTo(l1) < 0);
    }
}
