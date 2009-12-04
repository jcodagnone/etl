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

import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;


/**
 * Tests {@link LangUtilsTest}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Sep 20, 2009
 */
public class LangUtilsTest {

    /** test */
    @Test
    public final void testFields() {
        Assert.assertEquals("en", LangUtils.EN.getIsoName());
        Assert.assertEquals("es", LangUtils.ES.getIsoName());
        assertNotNull(LangUtils.getLang("es"));
    }
    
    /** test */
    @Test
    public final void testGetInvalidLang() {
        try {
            LangUtils.getLang("gooooooooooooo");
        } catch(final NoSuchEntityException e) {
            Assert.assertEquals("gooooooooooooo", e.getEntity());
        }
    }
}
